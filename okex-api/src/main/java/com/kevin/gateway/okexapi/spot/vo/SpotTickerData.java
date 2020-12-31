package com.kevin.gateway.okexapi.spot.vo;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.spot.SpotMarketId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**  示例
 *   {
 *     "table":"spot/ticker",
 *     "data":[
 *         {
 *             "instrument_id":"ETH-USDT",
 *             "last":"146.24",
 *             "last_qty":"0.082483",
 *             "best_bid":"146.24",
 *             "best_bid_size":"0.006822",
 *             "best_ask":"146.25",
 *             "best_ask_size":"80.541709",
 *             "open_24h":"147.17",
 *             "high_24h":"147.48",
 *             "low_24h":"143.88",
 *             "base_volume_24h":"117387.58",
 *             "quote_volume_24h":"17159427.21",
 *             "timestamp":"2019-12-11T02:31:40.436Z"
 *         }
 *     ]
 *   }
 */

@Data
public class SpotTickerData {
    /**
     *  币对名称
     */
    private SpotMarketId instrumentId;
    /**
     * 最新成交价
     */
    @JsonProperty("last")
    protected BigDecimal close;
    /**
     * 最新成交的数量
     */
    @JsonProperty("last_qty")
    protected BigDecimal lastVolume;
    /**
     * 买一价
     */
    @JsonProperty("best_bid")
    protected BigDecimal bid1Price;
    /**
     * 买一价对应的量
     */
    @JsonProperty("best_bid_size")
    protected BigDecimal bid1Volume;
    /**
     * 卖一价
     */
    @JsonProperty("best_ask")
    protected BigDecimal ask1Price;
    /**
     * 卖一价对应的数量
     */
    @JsonProperty("best_ask_size")
    protected BigDecimal ask1Volume;
    /**
     * 24小时开盘价
     */
    @JsonProperty("open_24h")
    protected BigDecimal open;
    /**
     * 24小时最高价
     */
    @JsonProperty("high_24h")
    protected BigDecimal high;
    /**
     * 24小时最低价
     */
    @JsonProperty("low_24h")
    protected BigDecimal low;
    /**
     * 24小时成交量，按交易货币统计
     */
    @JsonProperty("base_volume_24h")
    protected BigDecimal volume;
    /**
     * 24小时成交量，按计价货币统计
     */
    @JsonProperty("quote_volume_24h")
    protected BigDecimal amount;
    /**
     * 系统时间戳
     */
    protected LocalDateTime timestamp;

}
