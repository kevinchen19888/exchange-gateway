package com.kevin.gateway.okexapi.margin.response;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.okexapi.margin.MarginMarketId;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 杠杆配置信息
 */
@Data
public class MarginAccountSettingsResponse {
    /**
     * 杠杆币对名称
     */
    private MarginMarketId instrumentId;

    private MarginMarketId productId;

    @Data
    public static class MarginAccountSetting {
        /**
         * 当前最大可借
         */
        private BigDecimal available;
        /**
         * 借币利率
         */
        private BigDecimal rate;
        /**
         * 杠杆倍数
         */
        private BigDecimal leverage;

        private BigDecimal leverage_ratio;
    }

    @JsonIgnore
    private Map<String, MarginAccountSetting> info = new HashMap<>();

    @JsonAnySetter
    public void addInfo(String key, MarginAccountSetting value) {
        this.info.put(key, value);
    }
}
