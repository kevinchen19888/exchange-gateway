package com.kevin.gateway.okexapi.future.vo;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**  示例
 *   {
 *     "table":"future/ticker",
 *     "data":[
 *         {
 *             "last":"43.259",
 *             "open_24h":"49.375",
 *             "best_bid":"43.282",
 *             "high_24h":"49.488",
 *             "low_24h":"41.649",
 *             "volume_24h":"11295421",
 *             "volume_token_24h":"2430793.6742",
 *             "best_ask":"43.317",
 *             "open_interest":"1726003",
 *             "instrument_id":"LTC-USD-200327",
 *             "timestamp":"2020-03-12T08:31:45.288Z",
 *             "best_bid_size":"171",
 *             "best_ask_size":"2",
 *             "last_qty":"1"
 *         }
 *     ]
 *   }
 */

@Data
public class FutureTickerData {
    /**
     *  合约名称，如BTC-USD-170310,BTC-USDT-191227
     */
    private FutureMarketId instrumentId;
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
