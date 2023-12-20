package fr.apptrade.backend.api.v1.currency.model.response;

import fr.apptrade.backend.api.v1.currency.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyResponse {
    private String name;
    private String code;

    public CurrencyResponse(Currency currency) {
        this.name = currency.getName();
        this.code = currency.getCode();
    }
}
