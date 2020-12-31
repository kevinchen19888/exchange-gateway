package com.kevin.gateway.okexapi.option.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 时间粒度，以秒为单位，默认值为60。
 */
public enum TimeGranularity {
    /**
     * 1min
     */
    GRANULARITY_60(60),
    /**
     * 3min
     */
    GRANULARITY_180(180),
    /**
     * 5min
     */
    GRANULARITY_300(300),
    /**
     * 15min
     */
    GRANULARITY_900(900),
    /**
     * 30min
     */
    GRANULARITY_1800(1800),
    /**
     * 1hour
     */
    GRANULARITY_3600(3600),
    /**
     * 2hour
     */
    GRANULARITY_7200(7200),
    /**
     * 4hour
     */
    GRANULARITY_14400(14400),
    /**
     * 6hour
     */
    GRANULARITY_21600(21600),
    /**
     * 12hour
     */
    GRANULARITY_43200(43200),
    /**
     * 1day
     */
    GRANULARITY_86400(86400),
    /**
     * 1week
     */
    GRANULARITY_604800(604800);

    private final int val;

    TimeGranularity(int val) {
        this.val = val;
    }

    @JsonValue
    public int getVal() {
        return val;
    }

    @JsonCreator
    public static TimeGranularity fromVal(int val) {
        for (TimeGranularity granularity : TimeGranularity.values()) {
            if (granularity.val == val) {
                return granularity;
            }
        }
        throw new IllegalArgumentException("无效时间粒度,s:" + val);
    }
}
