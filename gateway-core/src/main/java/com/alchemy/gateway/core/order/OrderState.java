package com.alchemy.gateway.core.order;

public enum OrderState {

    SUBMITTING(0), // 提交中
    SUBMIT_FAILED(1), // 提交失败(网络超时,key无效,余额不足)
    CREATED(4), // 已创建订单
    PARTIAL_FILLED(5), // 部分成交
    FILLED(3), // 完成
    CANCELLING(6), // 撤销中
    CANCELLED(2); // 已撤销

    OrderState(int value) {
        this.intValue = value;
    }

    private final int intValue;

    public int getIntValue() {
        return this.intValue;
    }

    public static OrderState valueOf(int value) {
        for (OrderState orderState : OrderState.values()) {
            if (orderState.intValue == value) {
                return orderState;
            }
        }
        throw new IllegalArgumentException("无效订单状态");
    }

    public boolean isFinished() {
        return this.equals(CANCELLED) || this.equals(FILLED);
    }
}
