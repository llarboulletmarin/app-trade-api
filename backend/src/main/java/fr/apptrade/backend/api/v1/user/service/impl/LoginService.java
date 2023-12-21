package fr.apptrade.backend.api.v1.user.service.impl;

import fr.apptrade.backend.api.v1.user.model.response.UserResponse;
import fr.apptrade.backend.api.v1.user.service.ILoginService;
import fr.apptrade.backend.api.v1.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginService {

    private final IUserService userService;

    @Autowired
    public LoginService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserResponse login(String email) throws UsernameNotFoundException {
        return new UserResponse(this.userService.getUserByEmail(email));
    }

}
