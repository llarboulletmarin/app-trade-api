package fr.apptrade.backend.api.v1.currency.model.response;

import fr.apptrade.backend.api.v1.currency.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyResponse {
    private String name;
    private String code;
    private BigDecimal price;

    public CurrencyResponse(Currency currency) {
        this.name = currency.getName();
        this.code = currency.getCode();
    }
}
