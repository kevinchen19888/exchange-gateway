package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AlgoOrderState {
    //触发前被主动撤销的策略委托（orderStatus=canceled）
    // 触发失败的策略委托（orderStatus=rejected）
    // 触发成功的策略委托（orderStatus=triggered）

    CANCELED("canceled"),
    REJECTED("rejected"),
    TRIGGERED("triggered"),
    CREATED("created");

    AlgoOrderState(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static AlgoOrderState fromOf(String state) {
        for (AlgoOrderState type : AlgoOrderState.values()) {
            if (type.state.equals(state)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币策略委托订单状态");
    }
}
