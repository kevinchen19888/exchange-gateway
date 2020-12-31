package com.kevin.gateway.okexapi.option.domain;

import com.kevin.gateway.okexapi.option.util.CandlestickResponseDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 期权合约的K线数据响应，
 * K线数据最多可获取最近1440条
 */
@Data
@JsonDeserialize(using = CandlestickResponseDeserializer.class)
public class CandlestickResponse {
    /**
     * 开始时间，ISO 8601格式
     */
    private LocalDateTime timestamp;
    /**
     * 开盘价格
     */
    private BigDecimal open;
    /**
     * 最高价格
     */
    private BigDecimal high;
    /**
     * 最低价格
     */
    private BigDecimal low;
    /**
     * 收盘价格
     */
    private BigDecimal close;
    /**
     * 交易量(按张折算)
     */
    private int volume;
    /**
     * 交易量(按币种折算)
     */
    private BigDecimal currencyVolume;
}
