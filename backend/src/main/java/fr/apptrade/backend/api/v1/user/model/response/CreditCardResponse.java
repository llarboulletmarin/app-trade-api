package fr.apptrade.backend.api.v1.user.model.response;

import fr.apptrade.backend.api.v1.user.model.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardResponse {

    private int id;
    private String cardHolder;
    private String cardNumber;
    private String cardCvc;
    private Date cardExpirationDate;

    public CreditCardResponse(CreditCard card) {
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.cardNumber = card.getCardNumber().replaceAll("\\d(?=(?:\\D*\\d){4})", "X");
        this.cardCvc = "XXX";
        this.cardExpirationDate = card.getCardExpirationDate();
    }
}
