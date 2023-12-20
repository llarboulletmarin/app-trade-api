package fr.apptrade.backend.api.v1.currency.model.candle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandleResponse {
    private Timestamp timestamp;
    private BigDecimal price;

    public CandleResponse(Candle candle) {
        this.timestamp = candle.getTimestamp();
        this.price = candle.getClose();
    }
}
