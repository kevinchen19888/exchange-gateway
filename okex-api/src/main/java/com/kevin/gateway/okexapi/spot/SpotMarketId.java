package com.kevin.gateway.okexapi.spot;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.core.InstrumentId;
import com.kevin.gateway.core.InstrumentType;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;


@Data(staticConstructor = "of")
public final class SpotMarketId implements InstrumentId {
    @Override
    public InstrumentType getType() {
        return InstrumentType.SPOT;
    }

    private final CoinPair coinPair;

    @Override
    public String getSymbol() {
        return coinPair.getSymbol();
    }

    @JsonCreator
    public static SpotMarketId fromSymbol(String symbol) {
        return of(CoinPair.of(symbol));
    }
}
