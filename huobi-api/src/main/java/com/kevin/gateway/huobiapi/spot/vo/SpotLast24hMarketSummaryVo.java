package com.kevin.gateway.huobiapi.spot.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 最近24小时行情数据
 */
@Data
public class SpotLast24hMarketSummaryVo {
    /**
     * 响应id
     */
    private long id;
    /**
     * 以基础币种计量的交易量
     */
    private BigDecimal amount;
    /**
     * 交易次数
     */
    private Integer count;
    /**
     * 本阶段开盘价
     */
    private BigDecimal open;
    /**
     * 本阶段收盘价
     */
    private BigDecimal close;
    /**
     * 本阶段最低价
     */
    private BigDecimal low;
    /**
     * 本阶段最高价
     */
    private BigDecimal high;
    /**
     * 以报价币种计量的交易量
     */
    private BigDecimal vol;
    /**
     * 内部数据
     */
    private String version;
}
