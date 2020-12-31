package com.kevin.gateway.okexapi.index.vo;

import com.kevin.gateway.okexapi.index.IndexInstrumentId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**  示例
 *   {
 *     "table":"index/ticker",
 *     "data":[{
 *         "last":"3649.76",
 *         "high_24h":"3955.4",
 *         "low_24h":"10",
 *         "instrument_id":"BTC-USD",
 *         "open_24h":"3888.68",
 *         "timestamp":"2018-11-27T10:01:23.341Z"
 *       }]
 *   }
 */

@Data
public class IndexTickerData {
    /**
     *  指数名称，如	BTC-USD ,BTC-USDT
     */
    private IndexInstrumentId instrumentId;
    /**
     * 最新成交价
     */
    @JsonProperty("last")
    private BigDecimal close;

    /**
     * 24小时最高价
     */
    @JsonProperty("high_24h")
    private BigDecimal high;

    /**
     * 	24小时最低价
     */
    @JsonProperty("low_24h")
    private BigDecimal low;

    /**
     * 24h开盘价
     */
    @JsonProperty("open_24h")
    private BigDecimal open;

    /**
     * 系统时间戳
     */
    private LocalDateTime timestamp;

}
