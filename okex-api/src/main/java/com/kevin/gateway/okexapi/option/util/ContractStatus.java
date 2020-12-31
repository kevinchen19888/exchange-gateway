package com.kevin.gateway.okexapi.option.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ContractStatus {
    /**
     * 1-预上线,
     */
    PREOPEN(1),
    /**
     * 2-已上线,
     */
    LIVE(2),
    /**
     * 3-暂停交易,
     */
    SUSPENDED(3),
    /**
     * 4-结算中
     */
    SETTLEMENT(4);

    private final int val;

    ContractStatus(int val) {
        this.val = val;
    }

    @JsonValue
    public int getVal() {
        return val;
    }

    @JsonCreator
    public static ContractStatus valueOf(int value) {
        for (ContractStatus status : ContractStatus.values()) {
            if (status.val == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("无效合约状态");
    }
}
