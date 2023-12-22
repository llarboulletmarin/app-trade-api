package fr.apptrade.backend.api.v1.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCardRequest {

    private Integer cardId;
    private BigDecimal amount;

}
