package fr.apptrade.backend.api.v1.currency.service;

import fr.apptrade.backend.api.v1.currency.model.Currency;

import java.util.List;

public interface ICurrencyService {

    List<Currency> getCurrency();

    List<Currency> getCurrencyByCode(String code);

}
