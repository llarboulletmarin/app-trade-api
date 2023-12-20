package fr.apptrade.backend.api.v1.user.repository;

import fr.apptrade.backend.api.v1.user.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICreditCardRepository extends JpaRepository<CreditCard, Integer> {
}
