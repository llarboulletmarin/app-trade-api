package fr.apptrade.backend.api.v1.user.model.response;

import fr.apptrade.backend.api.v1.user.model.TransactionCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCardResponse {

    private BigDecimal amount;
    private Date transactionDate;
    private CreditCardResponse creditCard;

    public TransactionCardResponse(TransactionCard transactionCard) {
        this.amount = transactionCard.getAmount();
        this.transactionDate = transactionCard.getTransactionDate();
        this.creditCard = new CreditCardResponse(transactionCard.getCreditCard());
    }
}
