package com.kevin.gateway.okexapi.future.websocket.response;

import com.kevin.gateway.okexapi.base.util.AlgoOrderType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 冰山委托
 */
@Data
public class IcebergOrderData extends OrderAlgoData{
    /**
     * 1：计划委托
     * 2：跟踪委托
     * 3：冰山委托
     * 4：时间加权
     * 5：止盈止损
     */
    private AlgoOrderType orderType = AlgoOrderType.ICEBERG;
    /**
     * 委托深度
     */
    private BigDecimal algoVariance;
    /**
     * 单笔均值
     */
    private BigDecimal avgAmount;
    /**
     * 价格限制
     */
    private BigDecimal priceLimit;
    /**
     * 已下单数量
     */
    private BigDecimal dealValue;

}
