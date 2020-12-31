package com.kevin.gateway.okexapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotPlaceAlgoType {
    LIMIT(1),
    MARKET(2);

    SpotPlaceAlgoType(Integer value) {
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
    public static SpotPlaceAlgoType valueOf(int value) {
        for (SpotPlaceAlgoType spotPlaceAlgoType : SpotPlaceAlgoType.values()) {
            if (spotPlaceAlgoType.value == value) {
                return spotPlaceAlgoType;
            }
        }
        throw new IllegalArgumentException("无效现货委托单订单类型");
    }
}
