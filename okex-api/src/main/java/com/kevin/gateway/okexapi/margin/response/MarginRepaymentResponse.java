package com.kevin.gateway.okexapi.margin.response;

import lombok.Data;

/**
 * 还币
 */
@Data
public class MarginRepaymentResponse {
    /**
     * 还币记录ID
     */
    private String repaymentId;
    /**
     * 由您设置的订单ID来识别您的订单
     */
    private String clientOid;
    /**
     * 结果
     */
    private boolean result;
}
