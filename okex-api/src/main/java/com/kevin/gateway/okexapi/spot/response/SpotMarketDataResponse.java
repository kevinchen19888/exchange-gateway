package com.kevin.gateway.okexapi.spot.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 获取K线数据/获取历史K线数据
 */
@Data
public class SpotMarketDataResponse {
    /**
     * 开始时间
     */
    private LocalDateTime time;

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
     * 交易量
     */
    private BigDecimal volume;
}
