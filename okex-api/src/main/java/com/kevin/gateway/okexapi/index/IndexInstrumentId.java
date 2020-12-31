package com.kevin.gateway.okexapi.index;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.core.InstrumentId;
import com.kevin.gateway.core.InstrumentType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

/**
 * 永续合约市场标识符
 */

@Data(staticConstructor = "of")
public class IndexInstrumentId implements InstrumentId {
    private final CoinPair coinPair;

    @JsonCreator
    public static IndexInstrumentId fromSymbol(String symbol) {
        return of(CoinPair.of(symbol));
    }

    @Override
    public InstrumentType getType() {
        return InstrumentType.SWAP;
    }

    @Override
    @JsonValue
    public String getSymbol() {
        return coinPair.getSymbol();
    }
}

