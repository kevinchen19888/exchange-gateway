package com.kevin.gateway.okexapi.future.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 获取当前手续费费率
 */
@Data
public class TradeFeeResponse {

    /**
     * 手续费档位
     */
    private String category;

    /**
     * 吃单手续费率
     */
    private BigDecimal taker;


    /**
     * 挂单手续费率
     * maker 的值：负数，代表是返佣的费率，正数，代表平台扣除的费率。
     */
    private BigDecimal maker;

    /**
     * 交割手续费率
     */
    private BigDecimal delivery;


    /**
     * 数据返回时间
     */
    private LocalDateTime timestamp;
}

