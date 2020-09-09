package com.alchemy.gateway.quotation.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class TickerVo {
    /**
     * 交易所 如 huobi、binance等
     */
    private String exchange;
    /**
     * 交易对 如 BTC/USDT、ETH/USDT等
     */
    private String symbol;
    /**
     * 交易市场类型,如现货交易（spot）市场、永续合约(perpetual)交易市场等
     */
    private String marketType;

    private List<DataDetail> dataDetail;

    @Data
    public static class DataDetail {
        /**
         * 统计开始时戳， 即24小时内第1笔交易发生赶时间
         */
        private Long timestamp;

        /**
         * 最高价格
         */
        private BigDecimal high;
        /**
         * 最高价格
         */
        private BigDecimal low;
        /**
         * 开盘价，即统计开始的第一笔交易发生价格
         */
        private BigDecimal open;
        /**
         * 收盘价，即统计结束时的最近一笔交易发生价格
         */
        private BigDecimal close;
        /**
         * 以报币价计算的成交量(即卖方币种计算的成交量)
         */
        private BigDecimal volume;
        /**
         * 以基础币种计算的成交额(即买方币种计算的成交量
         */
        private BigDecimal amount;

        /**
         * 最新成交交易的成交量
         */
        private BigDecimal volumeClose;
        /**
         * 目前最高买单价
         */
        private BigDecimal bid1Price;
        /**
         * 目前最高买单价的挂单量
         */
        private BigDecimal bid1Volume;
        /**
         * 目前最低卖单价
         */
        private BigDecimal ask1Price;
        /**
         * 目前最低卖单价的挂单量
         */
        private BigDecimal ask1Volume;

        private Map<String, String> reserveMap;
    }
}
