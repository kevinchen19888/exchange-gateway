package com.kevin.gateway.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 币对
 * <p>
 * 币对格式为: 本位币-报价币
 */
@Data(staticConstructor = "of")
public final class CoinPair implements Symbolic {
    private final Coin baseCoin;
    private final Coin quoteCoin;

    // 正常格式："BTC-USDT"
    private final static Pattern coinPairSymbolFormat = Pattern.compile("^([A-Z]+)-([A-Z]+)$");

    // 非正常格式：但是不多的几个场合，OKEX 中还有这种格式的币种，"btc_usdt" ,"btc-usdt"
    private final static Pattern coinPairWeirdSymbolFormat = Pattern.compile("^([a-z]+)[-_]([a-z]+)$");


    /**
     * 通过两个币种符号构造币对对象
     *
     * @param baseCoinSymbol  本位币符号
     * @param quoteCoinSymbol 报价币符号
     * @return 币对对象
     */
    public static CoinPair of(String baseCoinSymbol, String quoteCoinSymbol) {
        return CoinPair.of(Coin.of(baseCoinSymbol), Coin.of(quoteCoinSymbol));
    }

    /**
     * 通过币对符号构造币对对象
     *
     * @param symbol 币对符号，格式为"本位币-报价币"，其中币种为全部大写字母
     * @return 币对
     */
    @JsonCreator
    public static CoinPair of(String symbol) {
        Matcher matcher = coinPairSymbolFormat.matcher(symbol);
        if (!matcher.matches()) {
            matcher = coinPairWeirdSymbolFormat.matcher(symbol);    // 补丁：处理非正常的格式
            if (!matcher.matches()) {
                throw new IllegalArgumentException("非法币对格式, 正确格式为 币种-币种, 实际传入 " + symbol);
            }
        }
        MatchResult matchResult = matcher.toMatchResult();
        String baseCoinSymbol = matchResult.group(1);
        String quoteCoinSymbol = matchResult.group(2);
        return of(baseCoinSymbol.toUpperCase(), quoteCoinSymbol.toUpperCase()); // 补丁：处理非正常格式
    }

    @Override
    public String getSymbol() {
        return baseCoin.getSymbol() + "-" + quoteCoin.getSymbol();
    }

    /**
     * 判断币对（本位币或者报价币）中是否包含指定的币种
     *
     * @param coin 币种
     * @return true 包含
     */
    public boolean contains(Coin coin) {
        return this.getBaseCoin().equals(coin) || this.getQuoteCoin().equals(coin);
    }
}
