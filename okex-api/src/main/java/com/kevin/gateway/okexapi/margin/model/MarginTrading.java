package com.kevin.gateway.okexapi.margin.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MarginTrading {
    SPOT_TRADING(1),
    MARGIN_TRADING(2);

    MarginTrading(Integer value) {
        this.value = value;
    }

    private final int value;

    public String getName() {
        return this.name().toLowerCase();
    }

    @JsonValue
    public int getIntValue() {
        return value;
    }

    @JsonCreator
    public static MarginTrading valueOf(int value) {
        for (MarginTrading spotBillsType : MarginTrading.values()) {
            if (spotBillsType.value == value) {
                return spotBillsType;
            }
        }
        throw new IllegalArgumentException("无效币币杠杆下单类型");
    }
}
