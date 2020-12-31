package com.kevin.gateway.okexapi.margin;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.core.InstrumentId;
import com.kevin.gateway.core.InstrumentType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

@Data(staticConstructor = "of")
public final class MarginMarketId implements InstrumentId {
    @Override
    public InstrumentType getType() {
        return InstrumentType.MARGIN;
    }

    private final CoinPair coinPair;

    @Override
    @JsonValue
    public String getSymbol() {
        return coinPair.getSymbol();
    }

    @JsonCreator
    public static MarginMarketId fromSymbol(String symbol) {
        return of(CoinPair.of(symbol));
    }
}
