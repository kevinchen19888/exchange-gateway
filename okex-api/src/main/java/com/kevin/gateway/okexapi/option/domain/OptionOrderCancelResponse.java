package com.kevin.gateway.okexapi.option.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 期权撤单响应响应
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OptionOrderCancelResponse extends OptionErrorResponse {
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 由您设置的订单ID来识别您的订单
     */
    private String clientOid;
    /**
     * 接口调用返回结果，true/false
     */
    private boolean result;
}
