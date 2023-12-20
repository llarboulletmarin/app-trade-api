package fr.apptrade.backend.api.v1.user.model.response;

import fr.apptrade.backend.api.v1.currency.model.response.CurrencyResponse;
import fr.apptrade.backend.api.v1.user.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private CurrencyResponse currency;
    private BigDecimal amount;
    private BigDecimal value;
    private Date transactionDate;

    public TransactionResponse(Transaction transaction) {
        this.currency = new CurrencyResponse(transaction.getCurrency());
        this.amount = transaction.getAmount();
        this.value = transaction.getValue();
        this.transactionDate = transaction.getTransactionDate();
    }
}
