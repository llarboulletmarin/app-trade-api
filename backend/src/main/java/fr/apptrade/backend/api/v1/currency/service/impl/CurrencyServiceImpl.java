package fr.apptrade.backend.api.v1.currency.service.impl;

import fr.apptrade.backend.api.v1.currency.model.Currency;
import fr.apptrade.backend.api.v1.currency.model.candle.Candle;
import fr.apptrade.backend.api.v1.currency.model.candle.CandleResponse;
import fr.apptrade.backend.api.v1.currency.model.candle.CandleResponseList;
import fr.apptrade.backend.api.v1.currency.repository.ICurrencyRepository;
import fr.apptrade.backend.api.v1.currency.service.ICurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

    @Override
    public List<Currency> getCurrencies() {
        logger.debug("getCurrency()");
        return this.currencyRepository.findAll();
    }

    @Override
    public List<Currency> getCurrencyByCode(String code) {
        logger.debug("getCurrencyByCode({})", code);
        return this.currencyRepository.findByCode(code);
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
