package fr.apptrade.backend.api.v1.currency.model.candle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Candle {
    private Timestamp timestamp;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal volume;

    public Candle(List<Object> objects) {
        this.timestamp = Timestamp.from(Instant.ofEpochSecond(Long.parseLong(objects.get(0).toString())));
        this.open = new BigDecimal(objects.get(1).toString());
        this.high = new BigDecimal(objects.get(2).toString());
        this.low = new BigDecimal(objects.get(3).toString());
        this.close = new BigDecimal(objects.get(4).toString());
        this.volume = new BigDecimal(objects.get(5).toString());
    }
}
