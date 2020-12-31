package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotOrderState {
    //closed : 订单早已关闭
    //partial-canceled : 部分成交撤销。该状态订单不在撮合队列中，此状态由partial-filled转化而来，订单数量有部分被成交，但是被撤销。
    //filled : 已成交。该状态订单不在撮合队列中，订单的全部数量已经被市场成交。
    //canceled : 已撤销。该状态订单不在撮合订单中，此状态订单没有任何成交数量，且被成功撤销。
    //canceling : 撤销中。该状态订单正在被撤销的过程中，因订单最终需在撮合队列中剔除才会被真正撤销，所以此状态为中间过渡态。
    CLOSED(-1),
    PARTIAL_CANCELED(5),
    FILLED(6),
    CANCELED(7),
    CANCELING(10);

    SpotOrderState(Integer value) {
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
    public static SpotOrderState valueOf(int value) {
        for (SpotOrderState type : SpotOrderState.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币订单状态");
    }
}
