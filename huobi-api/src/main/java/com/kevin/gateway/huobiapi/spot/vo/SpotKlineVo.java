package com.kevin.gateway.huobiapi.spot.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * K 线数据（蜡烛图）
 */
@Data
public class SpotKlineVo {
    /**
     * 调整为新加坡时间的时间戳，单位秒，并以此作为此K线柱的id
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
}
