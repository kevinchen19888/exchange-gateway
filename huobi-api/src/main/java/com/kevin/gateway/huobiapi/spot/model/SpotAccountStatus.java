package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotAccountStatus {
    //working, lock, fl-sys, fl-mgt, fl-end, fl-negative

    working("working"),
    lock("lock"),
    fl_sys("fl-sys"),
    fl_mgt("fl-mgt"),
    fl_end("fl-end"),
    fl_negative("fl-negative");

    SpotAccountStatus(String status) {
        this.status = status;
    }

    private final String status;

    @JsonValue
    public String getStatus() {
        return status;
    }

    @JsonCreator
    public static SpotAccountStatus fromOf(String status) {
        for (SpotAccountStatus type : SpotAccountStatus.values()) {
            if (type.status.equals(status)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币现货市场状态");
    }
}
