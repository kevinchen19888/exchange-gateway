package com.kevin.gateway.okexapi.swap.common.model;


import com.kevin.gateway.okexapi.swap.SwapMarketId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SwapTickerResponse {

    /**
     * 合约ID
     */
    private SwapMarketId instrumentId;


    /**
     * 最新成交价
     */
    private BigDecimal last;


    /**
     * 卖一价
     */
    private BigDecimal bestAsk;


    /**
     * 买一价
     */
    private BigDecimal bestBid;


    /**
     * 24小时最高价
     */
    @JsonProperty("high_24h")
    private BigDecimal high24h;


    /**
     * 24小时最低价
     */
    @JsonProperty("low_24h")
    private BigDecimal low24h;


    /**
     * 24小时成交量，按张数统计
     */
    @JsonProperty("volume_24h")
    private int volume24h;


    /**
     * 24小时成交量
     */
    @JsonProperty("volume_token_24h")
    private BigDecimal volumeToken24h;



    /**
     * 时间
     */
    private LocalDateTime timestamp;


    /**
     * 最新成交的数量
     */
    private int lastQty;


    /**
     * 卖一价对应的量
     */
    private int bestAskSize;


    /**
     * 买一价对应的量
     */
    private int bestBidSize;

}
