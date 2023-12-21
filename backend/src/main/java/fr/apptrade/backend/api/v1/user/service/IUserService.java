package fr.apptrade.backend.api.v1.user.service;

import fr.apptrade.backend.api.v1.user.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserService {

    /**
     * Récupération d'un utilisateur par son email
     *
     * @param email : email
     * @return : utilisateur
     */
    User getUserByEmail(String email) throws UsernameNotFoundException;

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
