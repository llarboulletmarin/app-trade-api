package fr.apptrade.backend.api.v1.user.repository;

import fr.apptrade.backend.api.v1.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
