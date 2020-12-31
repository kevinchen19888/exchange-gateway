package com.kevin.gateway.huobiapi.spot;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.Symbolic;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.util.regex.Pattern;

@Data(staticConstructor = "of")
public final class SpotCoin implements Symbolic {
    // 币种符号必须全部是小写字母或数字，比如：usdt, lst(火币的小写)
    private final static Pattern coinSymbol = Pattern.compile("^[0-9a-zA-Z]+$");
    private final Coin coin;

    @JsonCreator
    public static SpotCoin fromSymbol(String symbol) {
        if (!coinSymbol.matcher(symbol).matches()) {
            throw new IllegalArgumentException("币种格式错误, 必须全部是字母或数字, 实际传入:" + symbol);
        }
        return of(Coin.of(symbol.toUpperCase()));
    }

    @JsonValue
    @Override
    public String getSymbol() {
        return coin.getSymbol().toLowerCase();
    }
}
