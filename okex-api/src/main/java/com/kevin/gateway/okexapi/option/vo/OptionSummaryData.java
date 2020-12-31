package com.kevin.gateway.okexapi.option.vo;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.option.OptionMarketId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** 期权详细定价
 * {
 *     "table":"option/summary",
 *     "data":[
 *         {
 *             "instrument_id":"BTC-USD-200110-9000-P",
 *             "underlying":"BTC-USD",
 *             "best_ask":"0.2495",
 *             "best_bid":"0.2395",
 *             "best_ask_size":"35",
 *             "best_bid_size":"35",
 *             "change_rate":"0",
 *             "delta":"-1.204251749",
 *             "gamma":"1.9206755384",
 *             "high_24h":"0",
 *             "highest_buy":"0.398",
 *             "realized_vol":"0",
 *             "bid_vol":"",
 *             "ask_vol":"0.957",
 *             "mark_vol":"0.7243",
 *             "last":"0",
 *             "leverage":"4.0922",
 *             "low_24h":"0",
 *             "lowest_sell":"0.0905",
 *             "mark_price":"0.013",
 *             "theta":"-0.0005075296",
 *             "vega":"0.0001420339",
 *             "volume_24h":"0",
 *             "open_interest":"0",
 *             "estimated_price":"0",
 *             "timestamp":"2019-12-31T08:13:28.794Z"
 *         }
 *     ]
 * }
 */
@Data
public class OptionSummaryData {

    /**
     * 	标的指数，如BTC-USD
     */
    private CoinPair underlying;

    /**
     * 	合约ID
     */
    private OptionMarketId instrumentId;

    /**
     * 	系统时间戳，ISO 8061格式
     */
    private LocalDateTime timestamp;

    /**
     * 买一价
     */
    @JsonProperty("best_bid")
    private BigDecimal bid1Price;

    /**
     * 买一价对应的数量
     */
    @JsonProperty("best_bid_size")
    private int bid1Volume;
    /**
     * 	卖一价
     */
    @JsonProperty("best_ask")
    private BigDecimal ask1Price;

    /**
     * 	卖一价对应的数量
     */
    @JsonProperty("best_ask_size")
    private int ask1Volume;

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
     * 24小时最低价
     */
    @JsonProperty("low_24h")
    private BigDecimal low;

    /**
     * 24小时成交量，按张数统计
     */
    @JsonProperty("volume_24h")
    private int volume;

    /**
     * 24小时涨跌幅
     */
    private BigDecimal changeRate;

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
     *  最低卖价
     */
    private BigDecimal lowestSell;

    /**
     *  期权价格对underlying价格的敏感度
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
    @JsonProperty("mark_vol")
    private BigDecimal markVolume;

    /**
     * 	bid波动率
     */
    @JsonProperty("bid_vol")
    private BigDecimal bidVolume;

    /**
     * ask波动率
     */
    @JsonProperty("ask_vol")
    private BigDecimal askVolume;

    /**
     * 已实现波动率 （目前该字段暂未启用）
     */
    @JsonProperty("realized_vol")
    private BigDecimal realizedVolume;

    /**
     * 预估交割价
     */
    private BigDecimal estimatedPrice;

}
