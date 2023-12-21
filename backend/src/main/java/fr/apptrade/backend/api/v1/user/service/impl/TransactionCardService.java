package fr.apptrade.backend.api.v1.user.service.impl;

import fr.apptrade.backend.api.v1.user.model.User;
import fr.apptrade.backend.api.v1.user.model.response.TransactionCardResponse;
import fr.apptrade.backend.api.v1.user.repository.ITransactionCardRepository;
import fr.apptrade.backend.api.v1.user.service.ITransactionCardService;
import fr.apptrade.backend.api.v1.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionCardService implements ITransactionCardService {

    private final ITransactionCardRepository transactionCardRepository;

    private final IUserService userService;

    @Autowired
    public TransactionCardService(ITransactionCardRepository transactionCardRepository,
                                  IUserService userService) {
        this.transactionCardRepository = transactionCardRepository;
        this.userService = userService;
    }

    @Override
    public List<TransactionCardResponse> getTransactionsCard(String email) {
        User user = this.userService.getUserByEmail(email);
        return this.transactionCardRepository.findAllByFkidUser(user.getId())
                .stream()
                .map(TransactionCardResponse::new)
                .toList();
    }

    @Override
    public List<TransactionCardResponse> getTransactionsCardByCardNumber(String email, String cardNumber) {
        User user = this.userService.getUserByEmail(email);
        return this.transactionCardRepository.findAllByFkidUserAndCreditCardCardNumber(user.getId(), cardNumber)
                .stream()
                .map(TransactionCardResponse::new)
                .toList();
    }

}
