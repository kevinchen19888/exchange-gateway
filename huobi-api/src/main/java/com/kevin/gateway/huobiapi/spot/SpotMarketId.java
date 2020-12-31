package com.kevin.gateway.huobiapi.spot;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.core.InstrumentId;
import com.kevin.gateway.core.InstrumentType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data(staticConstructor = "of")
public final class SpotMarketId implements InstrumentId {
    @Override
    public InstrumentType getType() {
        return InstrumentType.SPOT;
    }

    private final CoinPair coinPair;

    @JsonValue
    @Override
    public String getSymbol() {
        return (coinPair.getBaseCoin().getSymbol() + coinPair.getQuoteCoin().getSymbol()).toLowerCase();
    }

    @JsonCreator
    public static SpotMarketId fromSymbol(String symbol) {
        //todo:暂定
        String[] buyers = {"btc", "eth", "usdt", "husd", "ht","trx"};
        for (String buyer : buyers) {
            if (symbol.endsWith(buyer)) {
                String substring = symbol.substring(0, symbol.length() - buyer.length());
                return of(CoinPair.of(substring.toUpperCase()
                        , buyer.toUpperCase()));
            }
        }
        log.warn("未识别的buyer:{}", symbol);
        return null;
    }

}
