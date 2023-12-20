package fr.apptrade.backend.api.v1.user.repository;

import fr.apptrade.backend.api.v1.user.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByFkidUser(Integer fkidUser);

    List<Transaction> findAllByFkidUserAndCurrencyCode(Integer fkidUser, String code);
}
