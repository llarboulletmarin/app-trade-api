package fr.apptrade.backend.api.v1.user.service.impl;

import fr.apptrade.backend.api.v1.currency.model.Currency;
import fr.apptrade.backend.api.v1.user.model.Transaction;
import fr.apptrade.backend.api.v1.user.model.User;
import fr.apptrade.backend.api.v1.user.model.request.TransactionRequest;
import fr.apptrade.backend.api.v1.user.model.response.TransactionResponse;
import fr.apptrade.backend.api.v1.user.repository.ITransactionRepository;
import fr.apptrade.backend.api.v1.user.service.ITransactionService;
import fr.apptrade.backend.api.v1.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Override
    public TransactionResponse addBuyTransaction(String email, Currency currency, TransactionRequest buyRequest, BigDecimal price) throws Exception {
        User user = this.userService.getUserByEmail(email);

        if (user.getBalance().compareTo(buyRequest.getAmount()) < 0) {
            throw new Exception("Not enough money");
        }

        // calcule la quantité de devise à acheter (ex: 100€ / 0.5€ = 200 unités)
        BigDecimal quantity = buyRequest.getAmount().divide(price, 8, RoundingMode.HALF_UP);

        Transaction transaction = new Transaction();
        transaction.setFkidUser(user.getId());
        transaction.setCurrency(currency);
        transaction.setAmount(quantity);
        transaction.setValue(price);

        this.userService.withdraw(email, quantity.multiply(price));
        return new TransactionResponse(this.transactionRepository.save(transaction));
    }

    @Override
    public TransactionResponse addSellTransaction(String email, Currency currency, TransactionRequest sellRequest, BigDecimal price)
            throws Exception {
        User user = this.userService.getUserByEmail(email);

        // calcule la quantité de devise à vendre (ex: 100€ / 0.5€ = 200 unités) et la négative (-200)
        BigDecimal quantity = sellRequest.getAmount().divide(price, 8, RoundingMode.HALF_UP);

        // récupère le montant de crypto monnaie que l'utilisateur possède
        BigDecimal amountOwned = this.getTransactionsByCode(email, currency.getCode())
                .stream()
                .map(TransactionResponse::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (amountOwned.compareTo(quantity) < 0) {
            throw new Exception("Not enough crypto currency");
        }

        Transaction transaction = new Transaction();
        transaction.setFkidUser(user.getId());
        transaction.setCurrency(currency);
        transaction.setAmount(quantity.negate());
        transaction.setValue(price);

        this.userService.deposit(email, quantity.multiply(price));
        return new TransactionResponse(this.transactionRepository.save(transaction));
    }

}
