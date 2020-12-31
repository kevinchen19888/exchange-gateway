package com.kevin.gateway.okexapi.fundingaccount.domain;

import com.kevin.gateway.core.Coin;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 母账户获取子账户的各个账户里的资金余额信息。
 */
@Data
public class SubAccountResponse {
    /**
     * 账户名
     */
    private String subAccount;
    /**
     * 以btc为单位 账户资产总估值
     */
    private BigDecimal assetValuation;

    /**
     * 现货
     */
    @JsonProperty("account_type:spot")
    private List<SubAccountType> accountTypeSpot;
    /**
     * 交割合约账户
     */
    @JsonProperty("account_type:futures")
    private List<SubAccountType> accountTypeFutures;
    @JsonProperty("account_type:otc")
    private List<SubAccountType> accountTypeOtc;
    @JsonProperty("account_type:margin")
    private List<SubAccountType> accountTypeMargin;
    /**
     * 资金账户账户
     */
    @JsonProperty("account_type:funding")
    private List<SubAccountType> accountTypeFunding;
    @JsonProperty("account_type:PiggyBank")
    private List<SubAccountType> accountTypePiggyBank;
    /**
     * 永续合约账户
     */
    @JsonProperty("account_type:swap")
    private List<SubAccountType> accountTypeSwap;
    /**
     * 期权合约账户
     */
    @JsonProperty("account_type:option")
    private List<SubAccountType> accountTypeOption;
    /**
     * 挖矿账户
     */
    @JsonProperty("account_type:Mining account")
    private List<SubAccountType> accountTypeMiningAccount;

    @Data
    public static class SubAccountType {
        /**
         * 账户余额 （可用余额和冻结余额的总和）
         */
        private BigDecimal balance;
        /**
         * 冻结（不可用）
         */
        private BigDecimal hold;
        /**
         * 可用余额 --币币和资金
         */
        private BigDecimal available;
        /**
         * 账户权益 --交割和永续
         */
        private BigDecimal equity;
        /**
         * 可划转数量
         */
        private BigDecimal maxWithdraw;
        /**
         * 币种，如BTC
         */
        private Coin currency;

        public void setOthers(String key, String val) {
            this.others.put(key, val);
        }

        /**
         * 未知字段
         */
        @JsonAnySetter
        private Map<String, String> others = new HashMap<>();

    }

}
