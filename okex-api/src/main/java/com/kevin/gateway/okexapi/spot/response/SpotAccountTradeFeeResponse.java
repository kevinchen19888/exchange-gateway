package com.kevin.gateway.okexapi.spot.response;

import com.kevin.gateway.okexapi.spot.model.SpotAccountTradeFeeCategory;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 获取当前账户费率响应实体
 * 备注：maker 的值：负数，代表是返佣的费率，正数，代表平台扣除的费率。
 */
@Data
public class SpotAccountTradeFeeResponse {
    /**
     * 手续费档位
     * 1：第一档，2：第二档， 3：第三档
     */
    private SpotAccountTradeFeeCategory category;

    /**
     * 吃单手续费率
     */
    private BigDecimal taker;

    /**
     * 挂单手续费率
     */
    private BigDecimal maker;

    /**
     * 数据返回时间
     */
    private LocalDateTime timestamp;

}
