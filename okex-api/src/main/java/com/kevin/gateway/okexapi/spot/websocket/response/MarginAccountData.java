package com.kevin.gateway.okexapi.spot.websocket.response;

import com.kevin.gateway.okexapi.spot.SpotMarketId;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户杠杆账户信息
 */
@Data
public class MarginAccountData {
    /**
     * 强平价
     */
    private BigDecimal liquidationPrice;
    /**
     * 当前借币数量档位
     */
    private String tiers;
    /**
     * 维持保证金率
     */
    private BigDecimal maintMarginRatio;
    /**
     * 杠杆币对名称
     */
    private SpotMarketId instrumentId;

    private LocalDateTime timestamp;
    /**
     *
     */
    @JsonIgnore
    private Map<String,CurrencyData> currencies = new HashMap<>();

    @JsonAnySetter
    public void addCurrencies(String key, CurrencyData value) {
        currencies.put(key, value);
    }

}
