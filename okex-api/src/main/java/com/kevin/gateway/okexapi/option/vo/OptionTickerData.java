package com.kevin.gateway.okexapi.option.vo;

import com.kevin.gateway.okexapi.option.OptionMarketId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**  示例
 *   {
 *     "table":"option/ticker",
 *     "data":[
 *         {
 *             "last":"0.0015",
 *             "open_24h":"0.0035",
 *             "best_bid":"0.001",
 *             "high_24h":"0.0035",
 *             "low_24h":"0.0015",
 *             "volume_24h":"18",
 *             "volume_token_24h":"1.8",
 *             "best_ask":"0.002",
 *             "open_interest":"668",
 *             "instrument_id":"BTC-USD-200103-8000-C",
 *             "timestamp":"2019-12-31T07:59:05.519Z",
 *             "best_bid_size":"1",
 *             "best_ask_size":"305",
 *             "last_qty":"0"
 *         }
 *     ]
 * }
 */

@Data
public class OptionTickerData {
    /**
     *  合约名称，如BTC-USD-190927-12500-C
     */
    private OptionMarketId instrumentId;

    /**
     * 最新成交价
     */
    @JsonProperty("last")
    private BigDecimal close;

    /**
     * 最新成交的数量
     */
    @JsonProperty("last_qty")
    private int lastVolume;

    /**
     * 买一价
     */
    @JsonProperty("best_bid")
    private BigDecimal bid1Price;

    /**
     * 买一价对应的量
     */
    @JsonProperty("best_bid_size")
    private int bid1Volume;

    /**
     * 卖一价
     */
    @JsonProperty("best_ask")
    private BigDecimal ask1Price;

    /**
     * 卖一价对应的数量
     */
    @JsonProperty("best_ask_size")
    private int ask1Volume;
    /**
     * 24小时开盘价
     */
    @JsonProperty("open_24h")
    private BigDecimal open;

    /**
     * 24小时最高价
     */
    @JsonProperty("high_24h")
    private BigDecimal high;

    /**
     * 24小时最低价
     */
    @JsonProperty("low_24h")
    private BigDecimal low;

    /**
     * 	24小时成交量（按张数统计）
     */
    @JsonProperty("volume_24h")
    private int volume;

    /**
     * 持仓量
     */
    @JsonProperty("volume_token_24h")
    private BigDecimal amount;

    /**
     * 成交量（按币统计）
     */
    @JsonProperty("open_interest")
    private BigDecimal openInterest;

    /**
     * 系统时间戳
     */
    private LocalDateTime timestamp;

}
