package com.kevin.gateway.okexapi.swap.vo;

import com.kevin.gateway.okexapi.base.util.OrderSide;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** 示例
 * {
 *     "table": "swap/trade",
 *     "data": [{
 *         "instrument_id": "BTC-USD-SWAP",
 *         "price": "5611.9",
 *         "side": "buy",
 *         "size": "2",
 *         "timestamp": "2019-05-06T06:51:24.389Z",
 *         "trade_id": "227897880202387456"
 *     }]
 * }
 */
@Data
public class SwapTradeData {
    private SwapMarketId instrumentId;
    private BigDecimal price;
    private OrderSide side;

    /**
     * 成交数量
     */
    @JsonProperty("size")
    private int volume;
    private LocalDateTime timestamp;
    private String tradeId;

}
