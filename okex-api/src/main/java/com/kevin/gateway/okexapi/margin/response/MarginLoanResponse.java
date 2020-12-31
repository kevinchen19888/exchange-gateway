package com.kevin.gateway.okexapi.margin.response;

import lombok.Data;

/**
 * 借币
 */
@Data
public class MarginLoanResponse {
    /**
     * 借币记录ID
     */
    private String borrowId;
    /**
     * 由您设置的订单ID来识别您的订单
     */
    private String clientOid;
    /**
     * 结果
     */
    private boolean result;
}
