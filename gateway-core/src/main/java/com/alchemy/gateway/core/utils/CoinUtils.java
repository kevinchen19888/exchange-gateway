package com.alchemy.gateway.core.utils;

import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 币种工具
 */
public class CoinUtils {
    private static final Pattern COIN_PATTERN = Pattern.compile("^[A-Z]+$");

    private static final List<String> WELLKNOWN_COINS = Arrays.asList(
            // TODO：使用非小号的数据
            // TODO: 使用资源中配置
            "BTC", "ETH", "USDT", "XRP", "BCH", "BSV", "ADA", "LTC", "BNB", "EOS",
            "LINK", "XLM", "XTZ", "OKB", "TRX", "XMR", "VET", "HT", "NEO", "ETC"
    );

    /**
     * 判断指定的币种是否是一个知名币种
     *
     * @param coin 币种标识
     * @return true 代表指定币种是一个知名币种
     */
    public static boolean isWellKnownCoin(@NonNull String coin) {
        for (String wellKnownCoin : WELLKNOWN_COINS) {
            if (wellKnownCoin.equals(coin)) {
                return true;
            }
        }
        return false;
    }

    public static void checkWellKnownCoin(@NonNull String coin) {
        if (!isWellKnownCoin(coin)) {
            throw new IllegalArgumentException("不是知名币种类型: " + coin);
        }
    }

    /**
     * 判断指定币种是否合法的币种
     *
     * @param coin 币种标识
     * @return true 代表指定币种是法的币种
     */
    public static boolean isValidCoin(@NonNull String coin) {
        return COIN_PATTERN.matcher(coin).matches();
    }

    /**
     * 检查指定币种，如果不合法，则抛出参数异常
     *
     * @param coin 币种标识
     * @throws IllegalArgumentException 非法币种
     */
    public static void checkValidCoin(@NonNull String coin) {
        if (!isValidCoin(coin)) {
            throw new IllegalArgumentException("非法币种类型: " + coin);
        }
    }
}
