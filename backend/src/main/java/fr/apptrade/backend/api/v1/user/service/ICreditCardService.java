package fr.apptrade.backend.api.v1.user.service;

import fr.apptrade.backend.api.v1.user.model.CreditCard;

public interface ICreditCardService {

    /**
     * Ajout d'une carte de crédit à un utilisateur
     *
     * @param creditCard : carte de crédit
     * @param email      : email de l'utilisateur
     * @return : carte de crédit
     */
    CreditCard addCreditCard(CreditCard creditCard, String email);

    /**
     * Suppression d'une carte de crédit
     *
     * @param id    : id de la carte de crédit
     * @param email : email de l'utilisateur
     * @throws Exception : erreur
     */
    void deleteCreditCard(int id, String email) throws Exception;

}
