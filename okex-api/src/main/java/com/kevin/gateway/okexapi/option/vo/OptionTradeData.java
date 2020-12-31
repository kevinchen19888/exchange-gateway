package com.kevin.gateway.okexapi.option.vo;

import com.kevin.gateway.okexapi.base.util.OrderSide;
import com.kevin.gateway.okexapi.option.OptionMarketId;
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
public class OptionTradeData {

    /**
     * 合约ID，如BTC-USD-190927-12500-C
     */
    private OptionMarketId instrumentId;

    /**
     * 成交价格
     */
    private BigDecimal price;

    /**
     * 成交方向：buy/sell
     */
    private OrderSide side;

    /**
     * 成交数量
     */
    @JsonProperty("qty")
    private int volume;

    /**
     * 成交时间，ISO 8601格式
     */
    private LocalDateTime timestamp;

    /**
     * 成交id
     */
    private String tradeId;

}
