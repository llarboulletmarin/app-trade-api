package fr.apptrade.backend.api.v1.user.service;

import fr.apptrade.backend.api.v1.currency.model.Currency;
import fr.apptrade.backend.api.v1.user.model.response.TransactionResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ITransactionService {

    /**
     * Récupération des transactions d'un utilisateur
     *
     * @param email : email de l'utilisateur
     * @return : transactions
     */
    List<TransactionResponse> getTransactions(String email);

    /**
     * Récupération des transactions d'un utilisateur pour une devise
     *
     * @param email : email de l'utilisateur
     * @return : transactions
     */
    List<TransactionResponse> getTransactionsByCode(String email, String code);

    /**
     * Permet d'enregistrer une nouvelle transaction pour un utilisateur (achat)
     *
     * @param email  : email de l'utilisateur
     * @param code   : devise
     * @param amount : montant de la transaction (en devise, ex: 0.5 BTC)
     * @param price  : prix de la devise (en euros, au moment de l'achat
     * @return : transaction
     */
    TransactionResponse addBuyTransaction(String email, Currency code, BigDecimal amount, BigDecimal price) throws Exception;
}
