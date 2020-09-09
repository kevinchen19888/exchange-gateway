package com.alchemy.gateway.core.common;

import org.springframework.lang.NonNull;

import java.util.concurrent.TimeUnit;

/**
 * 标准 K线周期
 */
public enum CandleInterval {
    MINUTES_1(TimeUnit.MINUTES.toMillis(1), "1m", "1m"),
    MINUTES_1MIN(TimeUnit.MINUTES.toMillis(1), "1min", "1m"),
    MINUTES_3(TimeUnit.MINUTES.toMillis(3), "3m", "3m"),
    MINUTES_5(TimeUnit.MINUTES.toMillis(5), "5m", "5m"),
    MINUTES_5MIN(TimeUnit.MINUTES.toMillis(5), "5min", "5m"),
    MINUTES_15(TimeUnit.MINUTES.toMillis(15), "15m", "15m"),
    MINUTES_15MIN(TimeUnit.MINUTES.toMillis(15), "15min", "15m"),
    MINUTES_30(TimeUnit.MINUTES.toMillis(30), "30m", "30m"),
    MINUTES_30MIN(TimeUnit.MINUTES.toMillis(30), "30min", "30m"),
    MINUTES_60(TimeUnit.MINUTES.toMillis(60), "60min", "1h"),
    HOUR_1(TimeUnit.HOURS.toMillis(1), "1h", "1h"),
    HOUR_2(TimeUnit.HOURS.toMillis(2), "2h", "2h"),
    HOUR_3(TimeUnit.HOURS.toMillis(3), "3h", "3h"),
    HOUR_4(TimeUnit.HOURS.toMillis(4), "4h", "4h"),
    HOUR_4HOUR(TimeUnit.HOURS.toMillis(4), "4hour", "4h"),
    HOUR_6(TimeUnit.HOURS.toMillis(6), "6h", "6h"),
    HOUR_8(TimeUnit.HOURS.toMillis(8), "8h", "8h"),
    HOUR_12(TimeUnit.HOURS.toMillis(12), "12h", "12h"),
    DAY_1(TimeUnit.DAYS.toMillis(1), "1d", "1d"),
    DAY_1_D(TimeUnit.DAYS.toMillis(1), "1D", "1d"),
    DAY_1DAY(TimeUnit.DAYS.toMillis(1), "1day", "1d"),
    DAY_3(TimeUnit.DAYS.toMillis(3), "3d", "3d"),
    DAY_7(TimeUnit.DAYS.toMillis(7), "7d", "7d"),
    DAY_7_D(TimeUnit.DAYS.toMillis(7), "7D", "7d"),
    WEEK_1(TimeUnit.DAYS.toMillis(7), "1w", "7d"),
    WEEK_1_1WEEK(TimeUnit.DAYS.toMillis(7), "1week", "7d"),
    DAY_14(TimeUnit.DAYS.toMillis(14), "14D", "14d"),
    MONTH_1_M(TimeUnit.DAYS.toMillis(30), "1M", "1mon"),
    MONTH_1(TimeUnit.DAYS.toMillis(30), "1Mon", "1mon"),
    MONTH_1MON(TimeUnit.DAYS.toMillis(30), "1mon", "1mon"),
    MONTH_3(TimeUnit.DAYS.toMillis(90), "3Mon", "3mon"),
    MONTH_6(TimeUnit.DAYS.toMillis(180), "6Mon", "6mon"),
    YEAR_1(TimeUnit.DAYS.toMillis(365), "1Y", "1y"),
    YEAR_1_YEAR(TimeUnit.DAYS.toMillis(365), "1year", "1y");

    private final long milliseconds;
    private final String symbol;
    private final String describe;

    CandleInterval(long milliseconds, String symbol, String describe) {
        this.milliseconds = milliseconds;
        this.symbol = symbol;
        this.describe = describe;
    }

    public long getMilliSeconds() {
        return milliseconds;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDescribe() {
        return describe;
    }

    public static CandleInterval fromSymbol(@NonNull String symbol) {
        for (CandleInterval candleInterval : CandleInterval.values()) {
            if (candleInterval.getSymbol().equals(symbol))
                return candleInterval;
        }
        throw new IllegalArgumentException("非法参数: " + symbol);
    }

    public static CandleInterval fromDescribe(@NonNull String describe) {
        for (CandleInterval candleInterval : CandleInterval.values()) {
            if (candleInterval.getDescribe().equals(describe))
                return candleInterval;
        }
        throw new IllegalArgumentException("非法参数: " + describe);
    }
}
