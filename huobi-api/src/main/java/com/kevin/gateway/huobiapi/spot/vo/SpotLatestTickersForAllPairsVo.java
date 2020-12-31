package com.kevin.gateway.huobiapi.spot.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 所有交易对的最新 Tickers
 */
@Data
public class SpotLatestTickersForAllPairsVo {
    private BigDecimal amount;    //	以基础币种计量的交易量（以滚动24小时计）
    private Integer count;    //	交易笔数（以滚动24小时计）
    private BigDecimal open;    //	开盘价（以新加坡时间自然日计）
    private BigDecimal close;    //	最新价（以新加坡时间自然日计）
    private BigDecimal low;    //	最低价（以新加坡时间自然日计）
    private BigDecimal high;    //	最高价（以新加坡时间自然日计）
    private BigDecimal vol;    //	以报价币种计量的交易量（以滚动24小时计）
    private String symbol;    //	交易对，例如btcusdt, ethbtc
    private BigDecimal bid;    //	买一价
    private BigDecimal bidSize;    //	买一量
    private BigDecimal ask;    //	卖一价
    private BigDecimal askSize;    //	卖一量
}
