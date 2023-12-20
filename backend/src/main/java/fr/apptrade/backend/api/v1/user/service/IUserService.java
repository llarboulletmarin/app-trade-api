package fr.apptrade.backend.api.v1.user.service;

import fr.apptrade.backend.api.v1.user.model.CreditCard;
import fr.apptrade.backend.api.v1.user.model.User;
import fr.apptrade.backend.api.v1.user.model.response.UserResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserService {

    /**
     * Inscription d'un utilisateur
     *
     * @param user : utilisateur
     * @throws Exception : erreur
     */
    void register(User user) throws Exception;

    /**
     * Récupération d'un utilisateur par son email
     * @param email : email
     * @return : utilisateur
     */
    UserResponse getUserByEmail(String email) throws UsernameNotFoundException;

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
