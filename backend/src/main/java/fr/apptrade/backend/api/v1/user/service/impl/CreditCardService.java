package fr.apptrade.backend.api.v1.user.service.impl;

import fr.apptrade.backend.api.v1.user.model.CreditCard;
import fr.apptrade.backend.api.v1.user.model.User;
import fr.apptrade.backend.api.v1.user.repository.ICreditCardRepository;
import fr.apptrade.backend.api.v1.user.service.ICreditCardService;
import fr.apptrade.backend.api.v1.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CreditCardService implements ICreditCardService {

    private final ICreditCardRepository creditCardRepository;

    private final IUserService userService;

    @Autowired
    public CreditCardService(ICreditCardRepository creditCardRepository,
                             IUserService userService) {
        this.creditCardRepository = creditCardRepository;
        this.userService = userService;
    }

    @Override
    public CreditCard addCreditCard(CreditCard creditCard, String email) {
        User user = this.userService.getUserByEmail(email);
        creditCard.setFkidUser(user.getId());
        return this.creditCardRepository.save(creditCard);
    }

    @Override
    public void deleteCreditCard(int id, String email) throws Exception {
        User user = this.userService.getUserByEmail(email);

        CreditCard creditCard = this.creditCardRepository.findById(id).orElseThrow(() -> new Exception("Credit card not found"));
        if (!Objects.equals(creditCard.getFkidUser(), user.getId())) {
            throw new Exception("Credit card not found");
        }

        this.creditCardRepository.delete(creditCard);
    }

}
