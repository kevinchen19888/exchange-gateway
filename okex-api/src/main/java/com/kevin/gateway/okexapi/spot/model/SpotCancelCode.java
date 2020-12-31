package com.kevin.gateway.okexapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SpotCancelCode {

    OVERTIME(0),
    INITIATIVE(1),
    BALANCE_INSUFFICIENT(2);

    private final int type;

    SpotCancelCode(int value) {
        this.type = value;
    }

    @JsonCreator
    public static SpotCancelCode fromValue(int value) {
        for (SpotCancelCode oscc : SpotCancelCode.values()) {
            if (oscc.type == value ) return oscc;
        }
        throw new IllegalArgumentException(String.format("不支持%d状态码", value));
    }


}
