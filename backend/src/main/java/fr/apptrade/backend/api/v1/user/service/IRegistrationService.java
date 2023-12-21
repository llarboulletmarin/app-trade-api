package fr.apptrade.backend.api.v1.user.service;

import fr.apptrade.backend.api.v1.user.model.User;

public interface IRegistrationService {

    /**
     * Inscription d'un utilisateur
     *
     * @param user : utilisateur
     * @throws Exception : erreur
     */
    void register(User user) throws Exception;

}
