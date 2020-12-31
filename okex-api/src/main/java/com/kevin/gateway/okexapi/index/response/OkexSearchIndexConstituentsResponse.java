package com.kevin.gateway.okexapi.index.response;

import com.kevin.gateway.core.CoinPair;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class OkexSearchIndexConstituentsResponse {

    private long code;
    private String msg;
    private String errorCode;
    private String errorMessage;
    @JsonProperty("detailMsg")
    private String detailMsg;
    private OkexIndexContentResponse data;

    @Data
    public static class OkexIndexContentResponse {
        /**
         * 最新指数价格
         */
        private BigDecimal last;

        /**
         * 指数币对，如BTC指数则为BTC-USD
         */
        private CoinPair instrumentId;

        /**
         * 系统时间戳
         */
        private LocalDateTime timestamp;

        public List<OkexIndexConstituent> constituents;

        @Data
        private static class OkexIndexConstituent {
            /**
             * 原始价格
             */
            private BigDecimal originalPrice;
            /**
             * 权重
             */
            private BigDecimal weight;
            /**
             * 折算成USD价格
             */
            private BigDecimal usdPrice;
            /**
             * 交易所名称
             */
            private String exchange;

            /**
             * 交易所币对名称，如BTC-USD
             */
//            private CoinPair symbol;
            private String symbol;  // OKEx 返回的格式是 "BTC/USDT" :-( TODO: 此处要统一为 CoinPair 格式吗？
        }
    }
}
