package fr.apptrade.backend.api.v1.user.service.impl;

import fr.apptrade.backend.api.v1.config.WebSecurityConfig;
import fr.apptrade.backend.api.v1.user.model.CreditCard;
import fr.apptrade.backend.api.v1.user.model.Role;
import fr.apptrade.backend.api.v1.user.model.User;
import fr.apptrade.backend.api.v1.user.service.ICreditCardService;
import fr.apptrade.backend.api.v1.user.service.IRegistrationService;
import fr.apptrade.backend.api.v1.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class RegistrationService implements IRegistrationService {

    private final IUserService userService;

    private final ICreditCardService creditCardService;

    private final WebSecurityConfig webSecurityConfig;

    @Autowired
    public RegistrationService(IUserService userService,
                               WebSecurityConfig webSecurityConfig,
                               ICreditCardService creditCardService) {
        this.userService = userService;
        this.webSecurityConfig = webSecurityConfig;
        this.creditCardService = creditCardService;
    }

    @Override
    public void register(User user) throws Exception {
        String password = user.getPassword();
        user.setPassword(webSecurityConfig.passwordEncoder().encode(password));
        user.setRole(new Role(2, "user"));

        if (this.userService.userExists(user.getEmail()))
            throw new Exception(String.format("Email already exists: %s", user.getEmail()));

        if (!this.userService.saveUser(user))
            throw new Exception(String.format("Error while saving user: %s", user.getEmail()));

        CreditCard creditCard = new CreditCard(0, "John Doe", "1234567890123456", "123", Date.valueOf("2021-12-31"), user.getId());
        this.creditCardService.addCreditCard(creditCard, user.getEmail());
    }

}
