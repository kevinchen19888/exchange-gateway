package com.kevin.gateway.okexapi.spot.websocket.response;

import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderType;
import com.kevin.gateway.okexapi.spot.model.SpotTriggerOrderType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 计划委托
 */
@Data
public class TriggerOrderData extends OrderAlgoData {
    /**
     * 1：计划委托
     * 2：跟踪委托
     * 3：冰山委托
     * 4：时间加权
     * 5：止盈止损
     */
    private SpotAlgoOrderType orderType = SpotAlgoOrderType.TRIGGER_ORDER;
    /**
     * 委托价格
     */
    private BigDecimal algoPrice;
    /**
     * 触发价格
     */
    private BigDecimal triggerPrice;
    /**
     * 1：大于或等于触发价格 2：小于或等于触发价格
     */
    private String stopType;
    /**
     * 1:限价 2:市场价；
     */
    private SpotTriggerOrderType algoType;

}
