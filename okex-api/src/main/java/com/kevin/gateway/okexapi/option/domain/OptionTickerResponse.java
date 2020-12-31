package com.kevin.gateway.okexapi.option.domain;

import com.kevin.gateway.okexapi.option.OptionMarketId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 某个期权合约的最新成交价、买一价、卖一价和对应的量 vo
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OptionTickerResponse extends OptionErrorResponse {
    /**
     * 合约名称，如BTC-USD-190927-12500-C
     */
    private OptionMarketId instrumentId;
    /**
     * 买一价
     */
    private BigDecimal bestBid;
    /**
     * 买一价对应的数量
     */
    private int bestBidSize;
    /**
     * 卖一价
     */
    private BigDecimal bestAsk;
    /**
     * 卖一价对应的数量
     */
    private int bestAskSize;
    /**
     * 最新成交价
     */
    private BigDecimal last;
    /**
     * 最新成交数量
     */
    private int lastQty;
    /**
     * 持仓量
     */
    private BigDecimal openInterest;
    /**
     * 24小时开盘价
     */
    @JsonProperty("open_24h")
    private BigDecimal open24H;
    /**
     * 24小时最高价
     */
    @JsonProperty("high_24h")
    private BigDecimal high24H;
    /**
     * 24小时最低价
     */
    @JsonProperty("low_24h")
    private BigDecimal low24H;
    /**
     * 24小时成交量（按张数统计）
     */
    @JsonProperty("volume_24h")
    private int volume24H;
    /**
     * 系统时间戳，ISO 8601格式
     */
    private LocalDateTime timestamp;
}
