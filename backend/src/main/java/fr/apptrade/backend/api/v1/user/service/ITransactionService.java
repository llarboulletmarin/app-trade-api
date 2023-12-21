package fr.apptrade.backend.api.v1.user.service;

import fr.apptrade.backend.api.v1.user.model.response.TransactionResponse;

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

}
