package com.alchemy.gateway.core.marketdata;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 最新成交
 */
@Data
public class TradeTick {
    /**
     * 交易Id
     */
    String tradeId;
    /**
     * 成交价格
     */
    BigDecimal price;
    /**
     * 成交时间
     */
    LocalDateTime timeStamp;
    /**
     * 成交方向
     */
    String Side;
    /**
     * 成交数量
     */
    BigDecimal volume;
    /**
     * 备用字段，各交易所特色字段存放；key为字段名，value为值
     */
    Map<String, String> reserveMap = new HashMap<>();
}
