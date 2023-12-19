package fr.apptrade.backend.api.service;

import fr.apptrade.backend.api.model.Currency;

import java.util.List;

public interface ICurrencyService {

    List<Currency> getCurrency();

    List<Currency> getCurrencyByCode(String code);

}
