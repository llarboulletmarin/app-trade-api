package fr.apptrade.backend.api.v1.user.service;

import fr.apptrade.backend.api.v1.user.model.request.TransactionCardRequest;
import fr.apptrade.backend.api.v1.user.model.response.TransactionCardResponse;

import java.util.List;

public interface ITransactionCardService {

    /**
     * Dépôt d'argent sur le compte de l'utilisateur
     *
     * @param email          : email de l'utilisateur
     * @param depositRequest : montant à déposer et la carte de crédit
     * @return : transaction de carte de crédit
     */
    TransactionCardResponse deposit(String email, TransactionCardRequest depositRequest);

    /**
     * Retrait d'argent du compte de l'utilisateur
     *
     * @param email           : email de l'utilisateur
     * @param withdrawRequest : montant à retirer et la carte de crédit
     * @return : transaction de carte de crédit
     */
    TransactionCardResponse withdraw(String email, TransactionCardRequest withdrawRequest);

    /**
     * Récupération des transactions de carte de crédit d'un utilisateur
     *
     * @param email : email de l'utilisateur
     * @return : transactions de carte de crédit
     */
    List<TransactionCardResponse> getTransactionsCard(String email);

    /**
     * Récupération des transactions de carte de crédit d'un utilisateur pour une carte de crédit
     *
     * @param email      : email de l'utilisateur
     * @param cardNumber : numéro de la carte de crédit
     * @return : transactions de carte de crédit
     */
    List<TransactionCardResponse> getTransactionsCardByCardNumber(String email, String cardNumber);

}
