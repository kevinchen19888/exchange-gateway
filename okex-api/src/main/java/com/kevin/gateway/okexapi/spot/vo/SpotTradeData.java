package com.kevin.gateway.okexapi.spot.vo;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.base.util.OrderSide;
import com.kevin.gateway.okexapi.spot.SpotMarketId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** 示例
 * {
 *     "table": "spot/trade",
 *     "data": [{
 *         "instrument_id": "ETH-USDT",
 *         "price": "162.12",
 *         "side": "buy",
 *         "size": "11.085",
 *         "timestamp": "2019-05-06T06:51:24.389Z",
 *         "trade_id": "1210447366"
 *     }]
 * }
 */
@Data
public class SpotTradeData {
    private SpotMarketId instrumentId;
    private BigDecimal price;
    private OrderSide side;
    @JsonProperty("size")
    private BigDecimal volume;
    private LocalDateTime timestamp;
    private String tradeId;

}
