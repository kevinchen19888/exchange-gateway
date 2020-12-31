package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderState {
    /*submitted : 等待成交，该状态订单已进入撮合队列当中。
    partial-filled : 部分成交，该状态订单在撮合队列当中，订单的部分数量已经被市场成交，等待剩余部分成交。
    filled : 已成交。该状态订单不在撮合队列中，订单的全部数量已经被市场成交。
    partial-canceled : 部分成交撤销。该状态订单不在撮合队列中，此状态由partial-filled转化而来，订单数量有部分被成交，但是被撤销。
    canceled : 已撤销。该状态订单不在撮合订单中，此状态订单没有任何成交数量，且被成功撤销。
    canceling : 撤销中。该状态订单正在被撤销的过程中，因订单最终需在撮合队列中剔除才会被真正撤销，所以此状态为中间过渡态。*/
    SUBMITTED("submitted"),
    PARTIAL_FILLED("partial-filled"),
    FILLED("filled"),
    PARTIAL_CANCELED("partial-canceled"),
    CANCELED("canceled"),
    CANCELING("canceling"),
    CREATED("created");

    OrderState(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static OrderState fromOf(String state) {
        for (OrderState type : OrderState.values()) {
            if (type.state.equals(state)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币订单状态");
    }
}
