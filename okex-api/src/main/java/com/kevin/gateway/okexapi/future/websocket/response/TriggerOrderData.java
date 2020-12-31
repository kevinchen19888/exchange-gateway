package com.kevin.gateway.okexapi.future.websocket.response;

import com.kevin.gateway.okexapi.base.util.AlgoOrderType;
import com.kevin.gateway.okexapi.future.type.TriggerOrderType;
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
    private AlgoOrderType orderType = AlgoOrderType.TRIGGER;
    /**
     * 委托价格
     */
    private BigDecimal algoPrice;
    /**
     * 触发价格
     */
    private BigDecimal triggerPrice;
    /**
     * 实际委托量
     */
    private BigDecimal realAmount;
    /**
     * 1:限价 2:市场价；
     */
    private TriggerOrderType algoType;

}
