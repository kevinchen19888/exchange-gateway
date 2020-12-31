package com.kevin.gateway.core;

import lombok.Data;

import java.util.regex.Pattern;

/**
 * 币种
 */
@Data(staticConstructor = "of")
public final class Coin implements Symbolic {
    private final String symbol;

    // 币种符号必须全部是大写字母或数字，比如：USDT, 1ST
    private final static Pattern coinSymbolPattern = Pattern.compile("^[0-9A-Z]+$");


    private Coin(String symbol) {
        if (!coinSymbolPattern.matcher(symbol).matches()) {
            throw new IllegalArgumentException("币种格式错误, 必须全部是大写字母或数字, 实际传入:" + symbol);
        }
        this.symbol = symbol;
    }

}
