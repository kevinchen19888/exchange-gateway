package com.kevin.gateway.okexapi.swap.vo;

import com.kevin.gateway.okexapi.swap.SwapMarketId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**  示例
 *   {
 *     "table":"swap/ticker",
 *     "data":[
 *         {
 *             "last":"170.91",
 *             "open_24h":"198.4",
 *             "best_bid":"170.92",
 *             "high_24h":"199.03",
 *             "low_24h":"166",
 *             "volume_24h":"31943233",
 *             "volume_token_24h":"1730040.0174",
 *             "best_ask":"170.97",
 *             "open_interest":"4162489",
 *             "instrument_id":"ETH-USD-SWAP",
 *             "timestamp":"2020-03-12T08:30:41.738Z",
 *             "best_bid_size":"101",
 *             "best_ask_size":"2050",
 *             "last_qty":"1"
 *         }
 *     ]
 *   }
 */

@Data
public class SwapTickerData {
    /**
     *  合约名称，如BTC-USD-170310,BTC-USDT-191227
     */
    private SwapMarketId instrumentId;
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
