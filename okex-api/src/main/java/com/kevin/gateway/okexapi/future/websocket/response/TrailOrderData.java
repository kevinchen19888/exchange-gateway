package com.kevin.gateway.okexapi.future.websocket.response;

import com.kevin.gateway.okexapi.base.util.AlgoOrderType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 跟踪委托
 */
@Data
public class TrailOrderData extends OrderAlgoData {
    /**
     * 1：计划委托
     * 2：跟踪委托
     * 3：冰山委托
     * 4：时间加权
     * 5：止盈止损
     */
    private AlgoOrderType orderType = AlgoOrderType.TRAIL;
    /**
     * 回调幅度
     */
    private BigDecimal callbackRate;
    /**
     * 激活价格
     */
    private BigDecimal triggerPrice;
    /**
     * 实际委托量
     */
    private BigDecimal realAmount;

}
