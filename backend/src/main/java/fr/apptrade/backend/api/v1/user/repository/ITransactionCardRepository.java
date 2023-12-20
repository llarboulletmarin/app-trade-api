package fr.apptrade.backend.api.v1.user.repository;

import fr.apptrade.backend.api.v1.user.model.TransactionCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITransactionCardRepository extends JpaRepository<TransactionCard, Integer> {

    List<TransactionCard> findAllByFkidUser(Integer fkidUser);

    List<TransactionCard> findAllByFkidUserAndCreditCardCardNumber(Integer fkidUser, String cardNumber);

}
