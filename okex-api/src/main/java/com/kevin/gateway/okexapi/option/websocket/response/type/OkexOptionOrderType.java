package com.kevin.gateway.okexapi.option.websocket.response.type;

public enum OkexOptionOrderType {
    BUY(1, "提交失败"),
    SELL(2, "撤单成功"),
    LIQUIDATION_SELL(11, "撤单成功"),
    LIQUIDATION_BUY(12, "撤单成功"),
    PARTIAL_LIQUIDATION_SELL(13, "撤单成功"),
    PARTIAL_LIQUIDATION_BUY(14, "等待成交");

    private int value;
    private String name;

    OkexOptionOrderType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    private static OkexOptionOrderType fromValue(int value){
        for (OkexOptionOrderType ooot : OkexOptionOrderType.values()) {
            if (ooot.value == value) return ooot;
        }
        throw new IllegalArgumentException(String.format("不支持%d类型", value));
    }

}
