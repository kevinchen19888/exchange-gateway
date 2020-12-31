package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotSymbolsState {
    //online - 已上线；offline - 交易对已下线，不可交易；suspend -- 交易暂停；pre-online - 即将上线

    ONLINE("online"),
    OFFLINE("offline"),
    SUSPEND("suspend"),
    PRE_ONLINE("pre-online");

    SpotSymbolsState(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static SpotSymbolsState fromOf(String state) {
        for (SpotSymbolsState type : SpotSymbolsState.values()) {
            if (type.state.equals(state)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币现货交易对状态");
    }
}
