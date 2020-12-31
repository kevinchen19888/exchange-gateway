package com.kevin.gateway.okexapi.option.domain;

import com.kevin.gateway.okexapi.base.util.OrderSide;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 公共:期权合约的成交记录
 */
@Data
public class OptionFilledOrderResponse {
    /**
     * 成交id
     */
    private String tradeId;
    /**
     * 成交价格
     */
    private BigDecimal price;
    /**
     * 成交数量
     */
    private int qty;
    /**
     * 成交方向：buy/sell
     */
    private OrderSide side;
    /**
     * 成交时间，ISO 8601格式
     */
    private LocalDateTime timestamp;
}
