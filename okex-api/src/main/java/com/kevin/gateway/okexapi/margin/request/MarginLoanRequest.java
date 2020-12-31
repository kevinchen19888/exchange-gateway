package com.kevin.gateway.okexapi.margin.request;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.okexapi.margin.MarginMarketId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 借币
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarginLoanRequest {
    /**
     * 杠杆币对名称
     */
    private MarginMarketId instrumentId;
    /**
     * 由您设置的订单ID来识别您的订单
     */
    private String clientOid;
    /**
     * 币种
     */
    private Coin currency;
    /**
     * 借币数量
     */
    private BigDecimal amount;
}
