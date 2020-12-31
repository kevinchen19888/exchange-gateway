package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotHaltReason {
    EMERGENCY_MAINTENANCE(2),
    SCHEDULED_MAINTENANCE(3);

    SpotHaltReason(Integer value) {
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
    public static SpotHaltReason valueOf(int value) {
        for (SpotHaltReason type : SpotHaltReason.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币现货市场暂停原因");
    }
}
