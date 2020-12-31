package com.kevin.gateway.okexapi.option.domain;

import com.kevin.gateway.okexapi.option.OptionMarketId;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 历史结算/行权记录 vo
 */
@Data
public class SettlementAndExerciseHistoryResponse {
    /**
     * 穿仓用户亏损分摊
     */
    private BigDecimal clawbackLoss;
    /**
     * 准备金分摊
     */
    private BigDecimal reserve;
    /**
     * 分摊比例 如 0 ，0.01（1%）
     */
    private BigDecimal clawbackRate;
    /**
     * 交割/行权日期
     */
    private LocalDateTime timestamp;

    private List<InfoData> info;

    @Data
    public static class InfoData {
        /**
         * 期权ID，如BTC-USD-200605-5500-C
         */
        private OptionMarketId instrumentId;
        /**
         * 结算价格
         */
        private BigDecimal settlementPrice;
        /**
         * 到期日标的价格
         */
        private BigDecimal underlyingFixing;
        /**
         * 行权：exercised/expired OTM (实值已行权/虚值已过期) 结算：settlement（结算）
         */
        private SettlementType type;
    }

    public enum SettlementType {
        EXERCISED("Exercised"),
        EXPIRED_OTM("Expired OTM"),
        SETTLED("Settled");

        private final String val;

        SettlementType(String val) {
            this.val = val;
        }

        @JsonValue
        public String getVal() {
            return val;
        }

        @JsonCreator
        public static SettlementType fromVal(String value) {
            for (SettlementType execType : SettlementType.values()) {
                if (execType.val.equals(value)) {
                    return execType;
                }
            }
            throw new IllegalArgumentException("无效结算类型,s:" + value);
        }
    }


}
