package fr.apptrade.backend.api.v1.currency.service;

import fr.apptrade.backend.api.v1.currency.model.Currency;
import fr.apptrade.backend.api.v1.currency.model.candle.CandleResponseList;
import fr.apptrade.backend.api.v1.currency.model.response.CurrencyResponse;
import fr.apptrade.backend.api.v1.user.model.request.TransactionRequest;
import fr.apptrade.backend.api.v1.user.model.response.TransactionResponse;

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
     *
     * @param code : code de la devise
     * @return une devise
     */
    CurrencyResponse getCurrencyByCode(String code);

    /**
     * Retourne lune devise par son code
     *
     * @param code : code de la devise
     * @return la devise
     */
    Currency findCurrencyByCode(String code);

    /**
     * Retourne l'historique de prix d'une devise sur une période
     *
     * @param code : code de la devise
     * @param days : nombre de jours à remonter
     * @return l'historique de la devise
     */
    CandleResponseList getCurrencyHistoryByCode(String code, Integer days) throws Exception;


    /**
     * Endpoint d'achat d'une devise
     *
     * @param email        : email de l'utilisateur connecté
     * @param currencyCode : code de la devise
     * @param buyRequest   : montant à acheter (en euros)
     * @return la transaction
     * @throws Exception : exception
     */
    TransactionResponse buyCurrency(String email, String currencyCode, TransactionRequest buyRequest) throws Exception;

    /**
     * Endpoint de vente d'une devise
     *
     * @param email        : email de l'utilisateur connecté
     * @param currencyCode : code de la devise
     * @param sellRequest   : montant à vendre (en euros)
     * @return la transaction
     * @throws Exception : exception
     */
    TransactionResponse sellCurrency(String email, String currencyCode, TransactionRequest sellRequest) throws Exception;
}
