package fr.apptrade.backend.api.v1.currency.service.impl;

import fr.apptrade.backend.api.v1.currency.model.Currency;
import fr.apptrade.backend.api.v1.currency.model.candle.Candle;
import fr.apptrade.backend.api.v1.currency.model.candle.CandleResponse;
import fr.apptrade.backend.api.v1.currency.model.candle.CandleResponseList;
import fr.apptrade.backend.api.v1.currency.model.response.CoinbasePriceResponse;
import fr.apptrade.backend.api.v1.currency.model.response.CurrencyResponse;
import fr.apptrade.backend.api.v1.currency.repository.ICurrencyRepository;
import fr.apptrade.backend.api.v1.currency.service.ICurrencyService;
import fr.apptrade.backend.api.v1.user.model.request.TransactionRequest;
import fr.apptrade.backend.api.v1.user.model.response.TransactionResponse;
import fr.apptrade.backend.api.v1.user.service.impl.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CurrencyService implements ICurrencyService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyService.class);
    private final ICurrencyRepository currencyRepository;

    private final RestTemplate restTemplate;

    private final TransactionService transactionService;

    @Autowired
    public CurrencyService(ICurrencyRepository currencyRepository,
                           TransactionService transactionService) {
        this.currencyRepository = currencyRepository;
        this.restTemplate = new RestTemplate();
        this.transactionService = transactionService;
    }

    @Override
    public List<CurrencyResponse> getCurrencies() {
        List<Currency> currencies = this.currencyRepository.findAll();
        List<CompletableFuture<CurrencyResponse>> futures = currencies.stream()
                .map(currency -> CompletableFuture.supplyAsync(() -> {
                    CurrencyResponse response = new CurrencyResponse(currency);
                    response.setPrice(getPriceFromCoinbase(currency.getCode()));
                    return response;
                }))
                .toList();
        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    @Override
    public CurrencyResponse getCurrencyByCode(String code) {
        logger.debug("getCurrencyByCode({})", code);
        return this.currencyRepository.findByCode(code)
                .map(currency -> {
                    CurrencyResponse response = new CurrencyResponse(currency);
                    response.setPrice(getPriceFromCoinbase(currency.getCode()));
                    return response;
                })
                .orElseThrow(() -> new IllegalArgumentException("Currency not found"));
    }

    @Override
    public Currency findCurrencyByCode(String code) {
        return this.currencyRepository.findByCode(code).orElseThrow(() -> new IllegalArgumentException("Currency not found"));
    }

    @Override
    public CandleResponseList getCurrencyHistoryByCode(String code, Integer days) throws Exception {
        long currentTime = System.currentTimeMillis() / 1000;
        String startTimestamp = String.valueOf(currentTime - days * 24 * 60 * 60);

        try {
            // récupère les données brutes de Coinbase
            ResponseEntity<List<List<Object>>> response = restTemplate.exchange(
                    "https://api.pro.coinbase.com/products/" + code + "-EUR/candles?start=" + startTimestamp + "&end=" + currentTime + "&granularity=3600",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );
            List<List<Object>> rawData = response.getBody();
            if (rawData == null) {
                return null;
            }

            // transforme les données brutes en liste de CandleResponse
            List<CandleResponse> candleResponseList = rawData.stream()
                    .map(Candle::new)
                    .map(CandleResponse::new)
                    .toList();

            return new CandleResponseList(candleResponseList);

        } catch (Exception e) {
            throw new Exception("Currency with code " + code + " not found");
        }
    }

    @Override
    public TransactionResponse buyCurrency(String email, String currencyCode, TransactionRequest buyRequest) throws Exception {
        // récupère la devise et le prix
        Currency currency = this.findCurrencyByCode(currencyCode);
        BigDecimal price = getPriceFromCoinbase(currencyCode);

        return this.transactionService.addBuyTransaction(email, currency, buyRequest, price);
    }

    @Override
    public TransactionResponse sellCurrency(String email, String currencyCode, TransactionRequest sellRequest)
            throws Exception {
        // récupère la devise et le prix
        Currency currency = this.findCurrencyByCode(currencyCode);
        BigDecimal price = getPriceFromCoinbase(currencyCode);

        return this.transactionService.addSellTransaction(email, currency, sellRequest, price);
    }

    /**
     * Récupère le prix d'une devise sur Coinbase
     *
     * @param currencyCode code de la devise
     * @return le prix de la devise
     */
    private BigDecimal getPriceFromCoinbase(String currencyCode) {
        try {
            String url = "https://api.coinbase.com/v2/prices/" + currencyCode + "-EUR/spot";
            ResponseEntity<CoinbasePriceResponse> response = restTemplate.getForEntity(url, CoinbasePriceResponse.class);
            return new BigDecimal(Objects.requireNonNull(response.getBody()).getData().getAmount());
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération du prix pour la devise " + currencyCode, e);
            return BigDecimal.ZERO;
        }
    }

}
