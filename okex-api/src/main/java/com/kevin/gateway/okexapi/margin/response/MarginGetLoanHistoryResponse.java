package com.kevin.gateway.okexapi.margin.response;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.okexapi.margin.MarginMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 获取借币记录
 */
@Data
public class MarginGetLoanHistoryResponse {
    /**
     * 借币记录ID
     */
    private String borrowId;
    /**
     * 杠杆币对名称
     */
    private MarginMarketId instrumentId;
    /**
     * 币种
     */
    private Coin currency;
    /**
     * 借币时间
     */
    private LocalDateTime timestamp;
    /**
     * 借币总数量
     */
    private BigDecimal amount;
    /**
     * 利息总数量
     */
    private BigDecimal interest;
    /**
     * 已还借币数量
     */
    private BigDecimal returnedAmount;
    /**
     * 已还利息数量
     */
    private BigDecimal paidInterest;
    /**
     * 最后一次计息时间
     */
    private LocalDateTime lastInterestTime;
    /**
     * 强制还息时间
     */
    private LocalDateTime forceRepayTime;
    /**
     * 利率
     */
    private BigDecimal rate;

    /**
     * 多余字段
     */
    private LocalDateTime createdAt;
    private MarginMarketId productId;
    private BigDecimal repayAmount;
    private BigDecimal repayInterest;
}
