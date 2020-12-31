package com.kevin.gateway.okexapi.option.domain;

import com.kevin.gateway.okexapi.option.OptionMarketId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 公共-获取期权合约详细定价
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OptionMarketDataResponse extends OptionErrorResponse {
    /**
     * 合约ID
     */
    private OptionMarketId instrumentId;
    /**
     * 系统时间戳，ISO 8061格式
     */
    private LocalDateTime timestamp;
    /**
     * 买一价
     */
    private BigDecimal bestBid;
    /**
     * 卖一价
     */
    private BigDecimal bestAsk;
    /**
     * 卖一价数量
     */
    private Integer bestAskSize;
    /**
     * 买一价数量
     */
    private Integer bestBidSize;
    /**
     * 最新成交价
     */
    private BigDecimal last;
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
     * 24小时成交量，按张数统计
     */
    @JsonProperty("volume_24h")
    private BigDecimal volume24H;
    /**
     * 持仓量
     */
    private BigDecimal openInterest;
    /**
     * 标记价格
     */
    private BigDecimal markPrice;
    /**
     * 最高买价
     */
    private BigDecimal highestBuy;
    /**
     * 最低卖价
     */
    private BigDecimal lowestSell;
    /**
     * 期权价格对underlying价格的敏感度
     */
    private BigDecimal delta;
    /**
     * delta对underlying价格的敏感度
     */
    private BigDecimal gamma;
    /**
     * 期权价格对隐含波动率的敏感度
     */
    private BigDecimal vega;
    /**
     * 期权价格对剩余期限的敏感度
     */
    private BigDecimal theta;
    /**
     * 杠杆倍数
     */
    private BigDecimal leverage;
    /**
     * 标记波动率
     */
    private BigDecimal markVol;
    /**
     * bid波动率
     */
    private BigDecimal bidVol;
    /**
     * ask波动率
     */
    private BigDecimal askVol;
    /**
     * 已实现波动率（目前该字段暂未启用）
     */
    private BigDecimal realizedVol;
    /**
     * 预估交割价
     */
    private BigDecimal estimatedPrice;
}
