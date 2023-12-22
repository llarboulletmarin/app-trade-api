package fr.apptrade.backend.api.v1.user.service;

import fr.apptrade.backend.api.v1.user.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;

public interface IUserService {

    /**
     * Récupération d'un utilisateur par son email
     *
     * @param email : email
     * @return : utilisateur
     */
    User getUserByEmail(String email) throws UsernameNotFoundException;

    /**
     * Permet de déposer de l'argent sur le compte de l'utilisateur
     *
     * @param email  : email
     * @param amount : montant à déposer
     * @return : true si le montant a été déposé
     */
    boolean deposit(String email, BigDecimal amount);


    /**
     * Permet de retirer de l'argent du compte de l'utilisateur
     *
     * @param email  : email
     * @param amount : montant à retirer
     * @return : true si le montant a été retiré
     */
    boolean withdraw(String email, BigDecimal amount);

    /**
     * Permet de vérifier si un utilisateur existe
     *
     * @param email : email
     * @return : true si l'utilisateur existe
     */
    boolean userExists(String email);

    /**
     * Sauvegarde d'un utilisateur
     *
     * @param user : utilisateur
     * @return : true si l'utilisateur a été sauvegardé
     */
    boolean saveUser(User user);
}
