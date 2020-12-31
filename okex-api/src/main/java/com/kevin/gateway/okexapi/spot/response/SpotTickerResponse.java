package com.kevin.gateway.okexapi.spot.response;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.spot.vo.SpotTickerData;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 获取全部ticker信息/获取某个ticker信息
 */
@Data
public class SpotTickerResponse extends SpotTickerData {
//    /**
//     * 币对名称
//     */
//    private CoinPair instrumentId;
//
//    /**
//     * 最新成交价
//     */
//    private BigDecimal last;
//
//    /**
//     * 最新成交的数量
//     */
//    private BigDecimal lastQty;
//
//    /**
//     * 卖一价
//     */
//    private BigDecimal bestAsk;
//
//    /**
//     * 卖一价对应的量
//     */
//    private BigDecimal bestAskSize;
//
//    /**
//     * 买一价
//     */
//    private BigDecimal bestBid;
//
//    /**
//     * 买一价对应的数量
//     */
//    private BigDecimal bestBidSize;
//
//    /**
//     * 24小时开盘价
//     */
//    @JsonProperty(value = "open_24h")
//    private BigDecimal open24h;
//
//    /**
//     * 24小时最高价
//     */
//    @JsonProperty(value = "high_24h")
//    private BigDecimal high24h;
//
//    /**
//     * 24小时最低价
//     */
//    @JsonProperty(value = "low_24h")
//    private BigDecimal low24h;
//
//    /**
//     * 24小时成交量，按交易货币统计
//     */
//    @JsonProperty(value = "base_volume_24h")
//    private BigDecimal baseVolume24h;
//
//    /**
//     * 24小时成交量，按计价货币统计
//     */
//    @JsonProperty(value = "quote_volume_24h")
//    private BigDecimal quoteVolume24h;
//
//    /**
//     * 系统时间戳
//     */
//    private LocalDateTime timestamp;

    private String productId;
    private BigDecimal ask;
    private BigDecimal bid;
}
