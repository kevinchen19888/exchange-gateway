package com.kevin.gateway.okexapi.system.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 系统维护的状态 0:等待中 ; 1:进行中 ; 2:已完成
 */
public enum SystemStatus {
    WAITING(0),
    RUNNING(1),
    FINISHED(2);

    private final long code;

    SystemStatus(long code) {
        this.code = code;
    }

    @JsonValue
    public long getCode() {
        return code;
    }

    @JsonCreator
    private static SystemStatus fromCode(long code) {
        for (SystemStatus systemStatus : values()) {
            if (systemStatus.code == code) {
                return systemStatus;
            }
        }
        throw new IllegalArgumentException("非法的系统状态枚举值" + code);
    }
}
