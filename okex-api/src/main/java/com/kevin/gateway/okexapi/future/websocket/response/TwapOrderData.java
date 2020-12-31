package com.kevin.gateway.okexapi.future.websocket.response;

import com.kevin.gateway.okexapi.base.util.AlgoOrderType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 时间加权委托
 */
@Data
public class TwapOrderData extends OrderAlgoData{
    /**
     * 1：计划委托
     * 2：跟踪委托
     * 3：冰山委托
     * 4：时间加权
     * 5：止盈止损
     */
    private AlgoOrderType orderType = AlgoOrderType.TWAP;
    /**
     * 扫单范围
     */
    private BigDecimal sweepRange;
    /**
     * 扫单比例
     */
    private BigDecimal sweepRatio;
    /**
     * 单笔上限
     */
    private BigDecimal singleLimit;
    /**
     * 价格限制
     */
    private BigDecimal priceLimit;
    /**
     * 委托间隔
     */
    private BigDecimal timeInterval;
    /**
     * 已下单数量
     */
    private BigDecimal dealValue;

}
