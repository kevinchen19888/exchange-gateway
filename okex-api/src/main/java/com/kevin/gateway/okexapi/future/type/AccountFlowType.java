package com.kevin.gateway.okexapi.future.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 1.开多
 * 2.开空
 * 3.平多
 * 4.平空
 * 5.手续费
 * 6.转入，
 * 7.转出
 * 8.清算已实现盈亏
 * 13.强平平多
 * 14.强平平空
 * 15.交割平多
 * 16.交割平空
 * 17.清算未实现收益-多头
 * 18.清算未实现收益-空头
 * 20.强减空
 * 21.强减多
 */
public enum AccountFlowType {
    OPEN_LONG(1, "开多"),
    OPEN_SHORT(2, "开空"),
    CLOSE_LONG(3, "平多"),
    CLOSE_SHORT(4, "平空"),
    FEE(5, "手续费"),
    TRANSFER_INTO(6, "转入"),
    TRANSFER_OUT(7, "转出"),
    CLEARING_REAL(8, "清算已实现盈亏"),
    FORCE_LONG(13, "强平平多"),
    FORCE_SHORT(14, "强平平空"),
    DELEVERY_LONG(15, "交割平多"),
    DELEVERY_SHORT(16, "交割平空"),
    CLEARING_UNREAL_LONG(17, "清算未实现收益-多头"),
    CLEARING_UNREAL_SHORT(18, "清算未实现收益-空头"),
    FORCE_DECREASE_SHORT(20, "强减空"),
    FORCE_DECREASE_LONG(21, "强减多");

    private final Integer value;

    private final String name;

    AccountFlowType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @JsonCreator
    public static AccountFlowType fromValue(String value) {
        for (AccountFlowType flowType : AccountFlowType.values()) {
            if (flowType.value.equals(Integer.valueOf(value))) {
                return flowType;
            }
        }
        throw new IllegalArgumentException("AccountFlowType 无效 ,value= " + value);

    }

    @JsonValue
    public Integer getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }
}
