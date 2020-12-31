package com.kevin.gateway.okexapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotAlgoTriggerAndOrderSide {
    TAKE_PROFIT(1),//止盈
    STOP_LOSS(2),//止损
    BIDIRECTIONAL(3);//双向

    SpotAlgoTriggerAndOrderSide(int value) {
        this.intValue = value;
    }

    private final int intValue;

    @JsonValue
    public int getIntValue() {
        return this.intValue;
    }

    @JsonCreator
    public static SpotAlgoTriggerAndOrderSide valueOf(int value) {
        for (SpotAlgoTriggerAndOrderSide side : SpotAlgoTriggerAndOrderSide.values()) {
            if (side.intValue == value) {
                return side;
            }
        }
        throw new IllegalArgumentException("无效的现货触发方向或者下单委托方向");
    }
}
