package fr.apptrade.backend.api.v1.user.service.impl;

import fr.apptrade.backend.api.v1.user.model.User;
import fr.apptrade.backend.api.v1.user.repository.IUserRepository;
import fr.apptrade.backend.api.v1.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByEmail(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
    }

    @Override
    public boolean deposit(String email, BigDecimal amount) {
        User user = this.getUserByEmail(email);
        user.setBalance(user.getBalance().add(amount));
        this.userRepository.save(user);
        return true;
    }

    @Override
    public boolean withdraw(String email, BigDecimal amount) {
        User user = this.getUserByEmail(email);
        user.setBalance(user.getBalance().subtract(amount));
        this.userRepository.save(user);
        return true;
    }

    @Override
    public boolean userExists(String email) {
        return this.userRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean saveUser(User user) {
        this.userRepository.save(user);
        return true;
    }

}
