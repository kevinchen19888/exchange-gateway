package com.kevin.gateway.okexapi.option.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class OptionAmendOrderResponse extends OptionErrorResponse {
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 由您设置的订单ID来识别您的订单
     */
    private String clientOid;
    /**
     * 如果客户在修改请求中提供request_id，则返回相应request_id
     */
    private String requestId;
    /**
     * 接口调用返回结果，true/false
     */
    private boolean result;

}
