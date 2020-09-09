package com.alchemy.gateway.core.marketdata;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 24小时Ticker数据
 */
@Data
public class Ticker {
    /**
     * 交易对 如 BTC/USDT、ETH/USDT等
     */
    private String symbol;
    /**
     * 以报币价计算的成交量(即卖方币种计算的成交量)
     */
    BigDecimal volume;
    /**
     * 以基础币种计算的成交额(即买方币种计算的成交量
     */
    BigDecimal amount;
    /**
     * 目前最高买单价
     */
    BigDecimal bid1Price;
    /**
     * 目前最高买单价的挂单量
     */
    BigDecimal bid1Volume;
    /**
     * 目前最低卖单价
     */
    BigDecimal ask1Price;
    /**
     * 目前最低卖单价的挂单量
     */
    BigDecimal ask1Volume;
    /**
     * 最新成交交易的成交量
     */
    BigDecimal volumeClose;
    /**
     * 时间戳
     */
    LocalDateTime timeStamp;
    /**
     * 最高价
     */
    BigDecimal high;
    /**
     * 开盘价
     */
    BigDecimal open;
    /**
     * 收盘价，即统计结束时的最近一笔交易发生价格
     */
    private BigDecimal close;
    /**
     * 最低价
     */
    BigDecimal low;
    /**
     * 备用字段，各交易所特色字段存放；key为字段名，value为值
     */
    Map<String, String> reserveMap = new HashMap<>();
}
