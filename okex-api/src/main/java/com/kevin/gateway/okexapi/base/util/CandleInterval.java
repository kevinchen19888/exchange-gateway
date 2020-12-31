package com.kevin.gateway.okexapi.base.util;

import org.springframework.lang.NonNull;

import java.time.Duration;

/**
 * 标准 K线周期
 */
public enum CandleInterval {
    MINUTES_1(Duration.ofMinutes(1).getSeconds(), "1min"),
    MINUTES_3(Duration.ofMinutes(3).getSeconds(), "3min"),
    MINUTES_5(Duration.ofMinutes(5).getSeconds(), "5min"),
    MINUTES_15(Duration.ofMinutes(15).getSeconds(), "15min"),
    MINUTES_30(Duration.ofMinutes(30).getSeconds(), "30min"),
    MINUTES_60(Duration.ofMinutes(60).getSeconds(), "1hour"),
    HOUR_2(Duration.ofHours(2).getSeconds(), "2hour"),
    HOUR_4(Duration.ofHours(4).getSeconds(), "4hour"),
    HOUR_6(Duration.ofHours(6).getSeconds(), "6hour"),
    HOUR_12(Duration.ofHours(12).getSeconds(), "12hour"),
    DAY_1(Duration.ofDays(1).getSeconds(), "1day"),
    DAY_7(Duration.ofDays(7).getSeconds(), "7day"),
    MONTH_1(Duration.ofDays(31).getSeconds(), "1mon"),
    MONTH_3(Duration.ofDays(93).getSeconds(), "3mon"),
    MONTH_6(Duration.ofDays(186).getSeconds(), "6mon"),
    YEAR_1(Duration.ofDays(365).getSeconds(), "1year");

    private final long seconds;
    private final String symbol;

    CandleInterval(long seconds, String symbol) {
        this.seconds = seconds;
        this.symbol = symbol;
    }

    public long getSeconds() {
        return seconds;
    }

    public String getSymbol() {
        return symbol;
    }

    public static CandleInterval fromSymbol(@NonNull String symbol) {
        for (CandleInterval candleInterval : CandleInterval.values()) {
            if (candleInterval.getSymbol().equals(symbol))
                return candleInterval;
        }
        throw new IllegalArgumentException("非法参数: " + symbol);
    }
}
