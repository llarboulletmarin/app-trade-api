package fr.apptrade.backend.api.v1.currency.service;

import fr.apptrade.backend.api.v1.currency.model.candle.CandleResponseList;
import fr.apptrade.backend.api.v1.currency.model.response.CurrencyResponse;

import java.util.List;

public interface ICurrencyService {

    /**
     * Retourne toutes les devises
     *
     * @return la liste des devises
     */
    List<CurrencyResponse> getCurrencies();

    /**
     * Retourne une devise par son code
     * @param code : code de la devise
     * @return la devise
     */
    List<CurrencyResponse> getCurrencyByCode(String code);

    /**
     * Retourne l'historique de prix d'une devise sur une période
     *
     * @param code : code de la devise
     * @param days : nombre de jours à remonter
     * @return l'historique de la devise
     */
    CandleResponseList getCurrencyHistoryByCode(String code, Integer days) throws Exception;

}
