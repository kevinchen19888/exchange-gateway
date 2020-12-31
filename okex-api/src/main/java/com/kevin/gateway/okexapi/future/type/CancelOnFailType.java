package com.kevin.gateway.okexapi.future.type;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 0 ：不自动撤单 1:自动撤单 当订单修改失败时，该订单是否需要自动撤销。默认为0
 */
public enum CancelOnFailType {

    NOT_WITHDRAW(0),
    AUTO_WITHDRAW(1);

    private final Integer value;

    CancelOnFailType(Integer value) {
        this.value = value;
    }

    @JsonValue
    public Integer getValue() {
        return this.value;
    }
}
