package fr.apptrade.backend.api.v1.currency.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinbasePriceResponse {
    private Data data;

    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data {
        private String base;
        private String currency;
        private String amount;
    }

}
