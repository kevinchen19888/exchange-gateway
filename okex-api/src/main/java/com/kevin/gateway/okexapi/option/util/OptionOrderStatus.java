package com.kevin.gateway.okexapi.option.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OptionOrderStatus {
    /**
     * 提交失败
     */
    FAILED(-2),
    /**
     * 撤单成功
     */
    CANCELED(-1),
    /**
     * 等待成交
     */
    OPEN(0),
    /**
     * 部分成交
     */
    PARTIALLY_FILLED(1),
    /**
     * 完全成交
     */
    FULLY_FILLED(2),
    /**
     * 下单中
     */
    SUBMITTING(3),
    /**
     * 撤单中
     */
    CANCELING(4),
    /**
     * 修改中
     */
    PENDING_AMEND(5),

    /**
     * 未完成 ==此状态只会出现在查询订单列表场景中
     */
    UNFINISHED(6),

    /**
     * 已完成 ==此状态只会出现在查询订单列表场景中
     */
    COMPLETED(7);

    private final int value;

    OptionOrderStatus(int val) {
        this.value = val;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static OptionOrderStatus fromValue(int value) {
        for (OptionOrderStatus status : OptionOrderStatus.values()) {
            if (status.value == value) return status;
        }
        throw new IllegalArgumentException(String.format("不支持的%d状态", value));
    }
}
