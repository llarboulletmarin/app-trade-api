package fr.apptrade.backend.api.v1.currency.service.impl;

import fr.apptrade.backend.api.v1.currency.model.candle.Candle;
import fr.apptrade.backend.api.v1.currency.model.candle.CandleResponse;
import fr.apptrade.backend.api.v1.currency.model.candle.CandleResponseList;
import fr.apptrade.backend.api.v1.currency.model.response.CoinbasePriceResponse;
import fr.apptrade.backend.api.v1.currency.model.response.CurrencyResponse;
import fr.apptrade.backend.api.v1.currency.repository.ICurrencyRepository;
import fr.apptrade.backend.api.v1.currency.service.ICurrencyService;
import lombok.Data;
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
import java.util.Map;
import java.util.Objects;

@Service
public class CurrencyServiceImpl implements ICurrencyService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);
    private final ICurrencyRepository currencyRepository;

    private final RestTemplate restTemplate;

    @Autowired
    public CurrencyServiceImpl(ICurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
        this.restTemplate = new RestTemplate();
    }

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


    @Override
    public List<CurrencyResponse> getCurrencies() {
        logger.debug("getCurrency()");
        return this.currencyRepository.findAll()
                .stream()
                .map(currency -> {
                    CurrencyResponse response = new CurrencyResponse(currency);
                    response.setPrice(getPriceFromCoinbase(currency.getCode()));
                    return response;
                })
                .toList();
    }

    @Override
    public List<CurrencyResponse> getCurrencyByCode(String code) {
        logger.debug("getCurrencyByCode({})", code);
        return this.currencyRepository.findByCode(code)
                .stream()
                .map(currency -> {
                    CurrencyResponse response = new CurrencyResponse(currency);
                    response.setPrice(getPriceFromCoinbase(currency.getCode()));
                    return response;
                })
                .toList();
    }

    @Override
    public CandleResponseList getCurrencyHistoryByCode(String code, Integer days) throws Exception {
        long currentTime = System.currentTimeMillis() / 1000;
        String startTimestamp = String.valueOf(currentTime - days * 24 * 60 * 60);

        try {

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

            List<CandleResponse> candleResponseList = rawData.stream()
                    .map(Candle::new)
                    .map(CandleResponse::new)
                    .toList();

            return new CandleResponseList(candleResponseList);

        } catch (Exception e) {
            throw new Exception("Currency with code " + code + " not found");
        }
    }


}
