package fr.apptrade.backend.api.v1.user.service;

import fr.apptrade.backend.api.v1.user.model.User;
import fr.apptrade.backend.api.v1.user.model.response.UserResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserService {

    void register(User user) throws Exception;

    UserResponse getUserByEmail(String email) throws UsernameNotFoundException;

}
