package com.kevin.gateway.okexapi.future.vo;

import com.kevin.gateway.okexapi.base.util.OrderSide;
import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** 示例
 * {
 *     "table": "future/trade",
 *     "data": [{
 *         "side": "buy",
 *         "trade_id": "2778148208082945",
 *         "price": "5556.91",
 *         "qty": "5",
 *         "instrument_id": "BTC-USD-190628",
 *         "timestamp": "2019-05-06T07:19:37.496Z"
 *     }]
 * }
 */
@Data
public class FutureTradeData {
    private FutureMarketId instrumentId;
    private BigDecimal price;
    private OrderSide side;
    @JsonProperty("qty")
    private int volume;
    private LocalDateTime timestamp;
    private String tradeId;

}
