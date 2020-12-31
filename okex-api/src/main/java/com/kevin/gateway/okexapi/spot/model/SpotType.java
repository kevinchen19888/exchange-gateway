package com.kevin.gateway.okexapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotType {

    LIMIT("limit"),
    MARKET("market");

    SpotType(String type) {
        this.type = type;
    }

    private final String type;

    @JsonValue
    public String getType() {
        return type;
    }

    @JsonCreator
    public static SpotType fromOf(String type) {
        for (SpotType okexSpotPlaceType : SpotType.values()) {
            if (okexSpotPlaceType.type.equals(type)) {
                return okexSpotPlaceType;
            }
        }
        throw new IllegalArgumentException("无效现货订单类型");
    }


}
