package fr.apptrade.backend.api.v1.user.service;

import fr.apptrade.backend.api.v1.user.model.response.TransactionCardResponse;

import java.util.List;

public interface ITransactionCardService {

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
