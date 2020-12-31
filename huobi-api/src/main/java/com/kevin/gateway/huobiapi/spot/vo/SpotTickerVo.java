package com.kevin.gateway.huobiapi.spot.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 聚合行情（Ticker）
 */
@Data
public class SpotTickerVo {
    private long id;
    private BigDecimal amount;    //	以基础币种计量的交易量（以滚动24小时计）
    private BigDecimal count;    //	交易次数（以滚动24小时计）
    private BigDecimal open;    //	本阶段开盘价（以滚动24小时计）
    private BigDecimal close;    //	本阶段最新价（以滚动24小时计）
    private BigDecimal low;    //	本阶段最低价（以滚动24小时计）
    private BigDecimal high;    //	本阶段最高价（以滚动24小时计）
    private BigDecimal vol;    //	以报价币种计量的交易量（以滚动24小时计）
    private Object[] bid;    //	当前的最高买价 [price, size]
    private Object[] ask;    //	当前的最低卖价 [price, size]
    private String version;

    @Data
    public static class bidAsk {
        private BigDecimal price;
        private BigDecimal size;
    }
}
