package com.kevin.gateway.okexapi.spot.response;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.base.util.OrderSide;
import com.kevin.gateway.okexapi.spot.model.SpotTransactionExecType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 获取成交明细响应实体
 */
@Data
public class SpotTransactionDetailsResponse {
    /**
     * 账单ID
     */
    private String ledgerId;

    /**
     * 成交ID
     */
    private String tradeId;

    /**
     * 币对名称
     */
    private CoinPair instrumentId;

    /**
     * 成交价格
     */
    private BigDecimal price;

    /**
     * 成交数量
     */
    private BigDecimal size;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单成交时间
     */
    private LocalDateTime timestamp;

    /**
     * 流动性方向（T 或 M）
     */
    private SpotTransactionExecType execType;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 账单方向（buy、sell）
     */
    private OrderSide side;

    /**
     * 币种
     */
    private Coin currency;

    private LocalDateTime createdAt;

    private String liquidity;

    private String productId;
}
