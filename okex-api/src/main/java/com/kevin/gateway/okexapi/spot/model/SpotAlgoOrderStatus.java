package com.kevin.gateway.okexapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotAlgoOrderStatus {
    /**
     * 订单状态
     * 1：待生效
     * 2：已生效
     * 3：已撤销
     * 4：部分生效
     * 5：暂停生效
     * 6：委托失败
     * 【只有冰山和加权有4、5状态】
     */
    PENDING(1),
    EFFECTIVE(2),
    CANCELLED(3),
    PARTIALLY_EFFECTIVE(4),
    PAUSED(5),
    ORDER_FAILED(6);

    SpotAlgoOrderStatus(int value) {
        this.intValue = value;
    }

    private final int intValue;

    @JsonValue
    public int getIntValue() {
        return this.intValue;
    }

    @JsonCreator
    public static SpotAlgoOrderStatus valueOf(int value) {
        for (SpotAlgoOrderStatus status : SpotAlgoOrderStatus.values()) {
            if (status.intValue == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("无效现货委托订单状态");
    }
}
