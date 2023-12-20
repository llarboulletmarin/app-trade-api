package fr.apptrade.backend.api.v1.currency.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.apptrade.backend.api.v1.currency.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrencyResponse {
    private String name;
    private String code;
    private BigDecimal price;

    public CurrencyResponse(Currency currency) {
        this.name = currency.getName();
        this.code = currency.getCode();
    }
}
