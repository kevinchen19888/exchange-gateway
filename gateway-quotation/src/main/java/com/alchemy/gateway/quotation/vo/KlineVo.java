package com.alchemy.gateway.quotation.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class KlineVo {

    /**
     * 交易所名称
     */
    private String exchange;
    /**
     * 币对名称
     */
    private String symbol;
    /**
     * 请求的KLine线类型,如 1m、5m、1M
     */
    private String klineType;
    /**
     * 交易市场类型,如现货交易（spot）市场、永续合约(perpetual)交易市场等
     */
    private String marketType;

    private List<DataDetail> dataDetail;

    @Builder
    @Data
    public static class DataDetail {
        /**
         * 开盘时戳(币安有此信息，火币没有),即第一笔交易发生时间
         */
        private Long timestamp;
        /**
         * 最高价格
         */
        private BigDecimal high;
        /**
         * 最低价
         */
        private BigDecimal low;
        /**
         * 开盘价
         */
        private BigDecimal open;
        /**
         * 收盘价
         */
        private BigDecimal close;
        /**
         * 交易量
         */
        private BigDecimal volume;

        private Map<String, String> reserveMap;
    }
}
