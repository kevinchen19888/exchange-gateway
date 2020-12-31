package com.kevin.gateway.okexapi.base.util;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AlgoOrderStatus {
    PENDING(1,"待生效"),
    EFFECTIVE(2, "已生效"),
    CANCELED(3, "已撤销"),
    PARTIALLY_EFFECTIVE(4,"部分生效"),
    PAUSED(5, "暂停生效"),
    FAILED(6, "委托失败");

    private int value;
    private String name;

    AlgoOrderStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @JsonCreator
    public static AlgoOrderStatus fromValue(int value){
        for (AlgoOrderStatus aot : AlgoOrderStatus.values()) {
            if (aot.value == value) return aot;
        }
        throw new IllegalArgumentException(String.format("找不到%S订单状态", value));
    }

}
