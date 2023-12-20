package fr.apptrade.backend.api.v1.user.service.impl;

import fr.apptrade.backend.api.v1.config.WebSecurityConfig;
import fr.apptrade.backend.api.v1.user.model.CreditCard;
import fr.apptrade.backend.api.v1.user.model.Role;
import fr.apptrade.backend.api.v1.user.model.User;
import fr.apptrade.backend.api.v1.user.model.response.UserResponse;
import fr.apptrade.backend.api.v1.user.repository.ICreditCardRepository;
import fr.apptrade.backend.api.v1.user.repository.IUserRepository;
import fr.apptrade.backend.api.v1.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final ICreditCardRepository creditCardRepository;
    private final WebSecurityConfig webSecurityConfig;

    @Autowired
    public UserService(IUserRepository userRepository,
                       ICreditCardRepository creditCardRepository,
                       WebSecurityConfig webSecurityConfig) {
        this.userRepository = userRepository;
        this.creditCardRepository = creditCardRepository;
        this.webSecurityConfig = webSecurityConfig;
    }

    @Override
    public void register(User user) throws Exception {
        String password = user.getPassword();
        user.setPassword(webSecurityConfig.passwordEncoder().encode(password));
        user.setRole(new Role(2, "user"));

        if (this.userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Email already exist");
        }

        this.userRepository.save(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
        return new UserResponse(user);
    }

    @Override
    public CreditCard addCreditCard(CreditCard creditCard, String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
        creditCard.setFkidUser(user.getId());

        return this.creditCardRepository.save(creditCard);
    }

    @Override
    public void deleteCreditCard(int id, String email) throws Exception {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
        CreditCard creditCard = this.creditCardRepository.findById(id).orElseThrow(() -> new Exception("Credit card not found"));
        if (!Objects.equals(creditCard.getFkidUser(), user.getId())) {
            throw new Exception("Credit card not found");
        }

        this.creditCardRepository.delete(creditCard);
    }

}
