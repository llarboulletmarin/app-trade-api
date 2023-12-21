package fr.apptrade.backend.api.v1.user.service.impl;

import fr.apptrade.backend.api.v1.user.model.User;
import fr.apptrade.backend.api.v1.user.model.response.TransactionResponse;
import fr.apptrade.backend.api.v1.user.repository.ITransactionRepository;
import fr.apptrade.backend.api.v1.user.service.ITransactionService;
import fr.apptrade.backend.api.v1.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService implements ITransactionService {

    private final ITransactionRepository transactionRepository;

    private final IUserService userService;

    @Autowired
    public TransactionService(ITransactionRepository transactionRepository,
                              IUserService userService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }

    @Override
    public List<TransactionResponse> getTransactions(String email) {
        User user = this.userService.getUserByEmail(email);
        return this.transactionRepository.findAllByFkidUser(user.getId())
                .stream()
                .map(TransactionResponse::new)
                .toList();
    }

    @Override
    public List<TransactionResponse> getTransactionsByCode(String email, String code) {
        User user = this.userService.getUserByEmail(email);
        return this.transactionRepository.findAllByFkidUserAndCurrencyCode(user.getId(), code)
                .stream()
                .map(TransactionResponse::new)
                .toList();
    }

}
