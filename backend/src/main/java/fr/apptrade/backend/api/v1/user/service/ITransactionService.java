package fr.apptrade.backend.api.v1.user.service;

import fr.apptrade.backend.api.v1.currency.model.Currency;
import fr.apptrade.backend.api.v1.user.model.request.TransactionRequest;
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
     * @param email      : email de l'utilisateur
     * @param code       : devise
     * @param buyRequest : montant de la transaction (en euros, ex: 100€)
     * @param price      : prix de la devise (en euros, au moment de l'achat)
     * @return : transaction
     */
    TransactionResponse addBuyTransaction(String email, Currency code, TransactionRequest buyRequest, BigDecimal price) throws Exception;

    /**
     * Permet d'enregistrer une nouvelle transaction pour un utilisateur (vente)
     *
     * @param email       : email de l'utilisateur
     * @param code        : devise
     * @param sellRequest : montant de la transaction (en euros, ex: 100€)
     * @param price       : prix de la devise (en euros, au moment de l'achat)
     * @return : transaction
     */
    TransactionResponse addSellTransaction(String email, Currency code, TransactionRequest sellRequest, BigDecimal price) throws Exception;
}
