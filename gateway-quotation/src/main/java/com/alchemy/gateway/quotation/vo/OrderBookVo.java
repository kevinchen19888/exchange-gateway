package com.alchemy.gateway.quotation.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 深度数据
 */
@Data
public class OrderBookVo {
    /**
     * 交易所
     */
    private String exchange;
    /**
     * 交易对
     */
    private String symbol;
    /**
     * 交易市场类型,如现货交易（spot）市场、永续合约(perpetual)交易市场等
     */
    private String marketType;
    /**
     * 买卖盘详情
     */
    private List<OrderBookDetail> dataDetail;

    @Data
    public static class OrderBookDetail {

        /**
         * 档位(精度)
         */
        private int level;
        /**
         * 时戳
         */
        private Long timestamp;
        /**
         * 卖盘
         */
        private List<OrderDetail> asks;
        /**
         * 买盘
         */
        private List<OrderDetail> bids;

        @Data
        public static class OrderDetail {
            /**
             * 挂单价格
             */
            private BigDecimal price;
            /**
             * 挂单量
             */
            private BigDecimal volume;

            private Map<String, String> reserveMap;
        }
    }
}
