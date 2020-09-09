package com.alchemy.gateway.quotation.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 最新成交
 */
@Data
public class TradeTickVo {
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
         * 成交时戳
         */
        private Long timestamp;
        /**
         * 成交价
         */
        private BigDecimal price;
        /**
         * 成交量(即卖方币种计算的成交量)
         */
        private BigDecimal volume;
        /**
         * 成交方向
         */
        private String side;
        /**
         * 交易id
         */
        private String tradeId;

        private Map<String, String> reserveMap;

    }
}
