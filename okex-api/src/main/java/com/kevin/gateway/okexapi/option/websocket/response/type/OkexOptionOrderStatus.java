package com.kevin.gateway.okexapi.option.websocket.response.type;

public enum OkexOptionOrderStatus {
    FAILED(-2, "提交失败"),
    CANCELED(-1, "撤单成功"),
    OPEN(0, "等待成交"),
    PARTIALLY_FILLED(1, "部分成交"),
    FULLY_FILLED(2, "完全成交"),
    SUBMITTING(3, "下单中"),
    CANCELING(4, "撤单中"),
    PENDING_AMEND(5, "修改中");

    private int value;
    private String name;

    OkexOptionOrderStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static OkexOptionOrderStatus fromValue(int value){
        for (OkexOptionOrderStatus ooos : OkexOptionOrderStatus.values()) {
            if (ooos.value == value) return ooos;
        }
        throw new IllegalArgumentException(String.format("不支持%d状态",value));
    }
}
