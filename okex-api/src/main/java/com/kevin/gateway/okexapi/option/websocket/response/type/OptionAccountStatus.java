package com.kevin.gateway.okexapi.option.websocket.response.type;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OptionAccountStatus {
    NORMAL(0),
    DELAYED_DELEVERAGING(1),
    DELEVERAGING(2);

    private final int value;

    OptionAccountStatus(int value) {
        this.value = value;
    }

    @JsonCreator
    public static OptionAccountStatus fromValue(int value){
        for (OptionAccountStatus oas : OptionAccountStatus.values()) {
            if (oas.value == value) return oas;
        }
        throw new IllegalArgumentException(String.format("不支持%d状态", value));
    }

}
