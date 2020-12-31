package com.kevin.gateway.okexapi.swap.websocket.response;

import lombok.Data;

/**
 *
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
    private String orderType = "3";
    /**
     * 委托深度
     */
    private String algoVariance;
    /**
     * 单笔均值
     */
    private String avgAmount;
    /**
     * 价格限制
     */
    private String priceLimit;

}
