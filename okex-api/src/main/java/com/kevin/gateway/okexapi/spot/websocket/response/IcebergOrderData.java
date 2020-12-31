package com.kevin.gateway.okexapi.spot.websocket.response;

import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 冰山委托
 */
@Data
public class IcebergOrderData extends OrderAlgoData {
    /**
     * 1：计划委托
     * 2：跟踪委托
     * 3：冰山委托
     * 4：时间加权
     * 5：止盈止损
     */
    private SpotAlgoOrderType orderType = SpotAlgoOrderType.ICEBERG_ORDER;
    /**
     * 委托深度
     */
    private BigDecimal algoVariance;
    /**
     * 单笔均值
     */
    private BigDecimal avgAmount;
    /**
     * 	价格限制
     */
    private BigDecimal priceLimit;
    /**
     * 已下单数量
     */
    private BigDecimal dealValue;

}
