package fr.apptrade.backend.api.v1.currency.service.impl;

import fr.apptrade.backend.api.v1.currency.model.Currency;
import fr.apptrade.backend.api.v1.currency.repository.ICurrencyRepository;
import fr.apptrade.backend.api.v1.currency.service.ICurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements ICurrencyService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);
    private final ICurrencyRepository currencyRepository;

    @Autowired
    public CurrencyServiceImpl(ICurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Currency> getCurrency() {
        logger.debug("getCurrency()");
        return this.currencyRepository.findAll();
    }

    @Override
    public List<Currency> getCurrencyByCode(String code) {
        logger.debug("getCurrencyByCode({})", code);
        return this.currencyRepository.findByCode(code);
    }

}
