package com.kevin.gateway.huobiapi.spot.vo;

import com.kevin.gateway.huobiapi.base.util.OrderSide;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 最近市场成交记录
 */
@Data
public class SpotTradeVo {
    private String id;    //	唯一交易id（将被废弃）
    @JsonProperty(value = "trade-id")
    private String tradeId;    //	唯一成交ID（NEW）
    private BigDecimal amount;    //	以基础币种为单位的交易量
    private BigDecimal price;    //	以报价币种为单位的成交价格
    private String ts;    //	调整为新加坡时间的时间戳，单位毫秒
    private OrderSide direction;    //	交易方向：“buy” 或 “sell”, “buy” 即买，“sell” 即卖
}
