package com.kevin.gateway.okexapi.margin.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MarginLoanStatus {

    /**
     * 状态
     * 0：未还清
     * 1：已还清 不填默认返回0：未还清
     */

    OUTSTANDING(0),
    REPAID(1);

    MarginLoanStatus(Integer value) {
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
    public static MarginLoanStatus valueOf(int value) {
        for (MarginLoanStatus spotBillsType : MarginLoanStatus.values()) {
            if (spotBillsType.value == value) {
                return spotBillsType;
            }
        }
        throw new IllegalArgumentException("无效币币杠杆借币记录状态");
    }
}
