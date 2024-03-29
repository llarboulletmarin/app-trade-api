package fr.apptrade.backend.api.v1.currency.model.candle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandleResponseList {
    private List<CandleResponse> prices;
}
