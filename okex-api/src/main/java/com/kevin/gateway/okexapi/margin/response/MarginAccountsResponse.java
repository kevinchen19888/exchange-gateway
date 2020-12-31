package com.kevin.gateway.okexapi.margin.response;

import com.kevin.gateway.okexapi.margin.MarginMarketId;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 币币杠杆账户信息
 */
@Data
public class MarginAccountsResponse {

    /**
     * 杠杆币对名称
     */
    private MarginMarketId instrumentId;
    /**
     * 强平价
     */
    private BigDecimal liquidationPrice;
    /**
     * 维持保证金率
     */
    private BigDecimal maintMarginRatio;
    /**
     * 保证金率
     */
    private BigDecimal marginRatio;
    /**
     * 风险率
     */
    private BigDecimal riskRate;
    /**
     * 当前借币数量档位
     */
    private BigDecimal tiers;
    /**
     * 多余字段
     */
    private MarginMarketId productId;

    @Data
    public static class MarginAccountInfo {

        /**
         * 可用于交易数量
         */
        private BigDecimal available;
        /**
         * 余额
         */
        private BigDecimal balance;
        /**
         * 已借币（已借未还的部分）
         */
        private BigDecimal borrowed;
        /**
         * 可划转数量
         */
        private BigDecimal canWithdraw;

        /**
         * 冻结（不可用）
         */
        private BigDecimal hold;
        /**
         * 利息（未还的利息）
         */
        private BigDecimal lendingFee;

        /**
         * 多余字段
         */
        private BigDecimal frozen;
        private BigDecimal holds;
    }

    @JsonIgnore
    private Map<String, MarginAccountInfo> info = new HashMap<>();

    @JsonAnySetter
    public void addInfo(String key, MarginAccountInfo value) {
        this.info.put(key, value);
    }

}
