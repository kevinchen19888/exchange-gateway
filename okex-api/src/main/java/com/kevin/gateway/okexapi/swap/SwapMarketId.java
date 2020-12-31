package com.kevin.gateway.okexapi.swap;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.core.InstrumentId;
import com.kevin.gateway.core.InstrumentType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 永续合约市场标识符
 */

@Data(staticConstructor = "of")
public class SwapMarketId implements InstrumentId {

    private final static Pattern FORMAT = Pattern.compile("^([A-Z]+)-([A-Z]+)-([SWAP]+)$");
    private final CoinPair coinPair;
    private final String time;

    private boolean isEmulated;

    @JsonCreator
    public static SwapMarketId fromSymbol(String symbol) {
        Matcher matcher = FORMAT.matcher(symbol);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("非法币对格式, 正确格式为 币种-币种-SWAP, 实际传入 " + symbol);
        }
        MatchResult matchResult = matcher.toMatchResult();
        String baseCoinSymbol = matchResult.group(1);
        String quoteCoinSymbol = matchResult.group(2);
        String time = matchResult.group(3);
        CoinPair pair = CoinPair.of(baseCoinSymbol, quoteCoinSymbol);
        return of(pair, time);

    }

    @Override
    public InstrumentType getType() {
        return InstrumentType.SWAP;
    }

    @Override
    @JsonValue
    public String getSymbol() {
        return String.join("-", coinPair.getSymbol(), time);
    }
}

