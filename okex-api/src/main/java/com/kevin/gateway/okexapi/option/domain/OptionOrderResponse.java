package com.kevin.gateway.okexapi.option.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 下单响应
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OptionOrderResponse extends OptionErrorResponse {
    /**
     * 订单ID，下单失败时，此字段值为-1
     */
    private String orderId;
    /**
     * 由您设置的订单ID来识别您的订单
     */
    private String clientOid;
    /**
     * 调用接口返回结果
     */
    private boolean result;
}
