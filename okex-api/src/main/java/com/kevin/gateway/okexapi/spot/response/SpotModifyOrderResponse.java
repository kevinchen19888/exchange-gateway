package com.kevin.gateway.okexapi.spot.response;

import lombok.Data;

/**
 * 修改订单/批量修改订单
 * 响应实体
 */
@Data
public class SpotModifyOrderResponse {

    /**
     * 订单ID。
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
     * 下单结果。若是下单失败，将给出错误码提示
     */
    private boolean result;

    /**
     * 错误码，下单成功时为0，下单失败时会显示相应错误码
     */
    private String errorCode;

    /**
     * 错误信息，下单成功时为空，下单失败时会显示错误信息
     */
    private String errorMessage;

    private String code;

    private String message;

}
