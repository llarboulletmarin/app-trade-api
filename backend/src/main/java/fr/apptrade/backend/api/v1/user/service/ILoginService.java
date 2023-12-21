package fr.apptrade.backend.api.v1.user.service;

import fr.apptrade.backend.api.v1.user.model.response.UserResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ILoginService {

    /**
     * Permet de se connecter et renvoie les informations de l'utilisateur
     *
     * @param email : email
     * @return : utilisateur
     */
    UserResponse login(String email) throws UsernameNotFoundException;

}
