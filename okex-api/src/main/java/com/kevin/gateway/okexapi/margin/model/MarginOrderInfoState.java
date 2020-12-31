package com.kevin.gateway.okexapi.margin.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MarginOrderInfoState {
    /**
     * -2:失败
     * -1:撤单成功
     * 0:等待成交
     * 1:部分成交
     * 2:完全成交
     * 3:下单中
     * 4:撤单中
     * 6: 未完成（等待成交+部分成交）
     * 7:已完成（撤单成功+完全成交）
     */
    FAILED(-2),
    CANCELED(-1),
    OPEN(0),
    PARTIALLY_FILLED(1),
    FULLY_FILLED(2),
    SUBMITTING(3),
    CANCELING(4),
    UNFINISHED(6),
    FINISHED(7);

    MarginOrderInfoState(int value) {
        this.intValue = value;
    }

    private final int intValue;

    @JsonValue
    public int getIntValue() {
        return this.intValue;
    }

    @JsonCreator
    public static MarginOrderInfoState valueOf(int value) {
        for (MarginOrderInfoState state : MarginOrderInfoState.values()) {
            if (state.intValue == value) {
                return state;
            }
        }
        throw new IllegalArgumentException("无效的币币杠杆订单状态");
    }
}
