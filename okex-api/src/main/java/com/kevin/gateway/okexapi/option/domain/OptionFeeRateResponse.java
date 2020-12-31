package com.kevin.gateway.okexapi.option.domain;

import com.kevin.gateway.okexapi.base.util.OkexFeeCategory;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 获取手续费费率
 */
@Data
public class OptionFeeRateResponse {

    /**
     * 手续费档位
     */
    private OkexFeeCategory category;
    /**
     * 挂单手续费率
     */
    private BigDecimal maker;
    /**
     * 吃单手续费率
     */
    private BigDecimal taker;
    /**
     * 行权手续费率
     */
    private BigDecimal exercise;
    /**
     * 当前时间，ISO 8601格式
     */
    private LocalDateTime timestamp;
}
