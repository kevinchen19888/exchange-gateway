package com.alchemy.gateway.core.common;

import com.alchemy.gateway.core.utils.CoinUtils;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Data
public final class CoinPair {
    @NonNull
    private String buyCoin; // 买方币种
    @NonNull
    private String sellCoin; // 卖方币种

    private static final Pattern COIN_PAIR_SYMBOL_PATTERN = Pattern.compile("^([A-Z]+?)/([A-Z]+?)$");

    public CoinPair(@NonNull String sellCoin, @NonNull String buyCoin) {
        CoinUtils.checkValidCoin(buyCoin);
        CoinUtils.checkValidCoin(sellCoin);

        this.buyCoin = buyCoin;
        this.sellCoin = sellCoin;
    }

    /**
     * 创建币对（静态方法）
     *
     * @param sellCoin 卖方币种
     * @param buyCoin  买方币种
     * @return 币对
     */
    public static CoinPair of(@NonNull String sellCoin, @NonNull String buyCoin) {
        return new CoinPair(sellCoin, buyCoin);
    }

    /**
     * 从币对标识字符串构造币对对象
     *
     * @param symbol 币对标识字符串（例如：BTC/USDT, BTC/ETH……等）
     * @return 币对对象
     */
    public static CoinPair fromSymbol(@NonNull String symbol) {
        Matcher matcher = COIN_PAIR_SYMBOL_PATTERN.matcher(symbol);
        if (matcher.matches()) {
            String sellCoin = matcher.group(1);
            String buyCoin = matcher.group(2);
            return new CoinPair(sellCoin, buyCoin);
        } else {
            throw new IllegalArgumentException("币对字符串的格式非法（正确格式是：买方币种/卖方币种）:" + symbol);
        }
    }

    /**
     * 生成 币对标识字符串
     *
     * @return 币对标识字符串（例如：BTC/USDT, BTC/ETH……等）
     */
    public String toSymbol() {
        return this.sellCoin + '/' + this.buyCoin;
    }
}
