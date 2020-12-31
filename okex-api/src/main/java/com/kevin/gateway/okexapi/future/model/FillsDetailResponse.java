package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.type.OpenCloseLongShortType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 获取成交明细
 */
@Data
public class FillsDetailResponse {

    /**
     * 账单ID
     */
    private String tradeId;

    /**
     * 合约ID，如BTC-USD-180213,BTC-USDT-191227
     */
    private FutureMarketId instrumentId;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private BigDecimal orderQty;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单成交时间  2019-03-21T04:41:58.0Z
     */
    private LocalDateTime createdAt;

    /**
     * 流动性方向（T 或 M）
     */
    private String execType;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 订单方向（buy 或 sell）
     */
    private String side;

    /**
     * 1:开多
     * 2:开空
     * 3:平多
     * 4:平空
     */
    private OpenCloseLongShortType type;

    /**
     * 由您设置的订单ID来识别您的订单
     */
    private String clientOid;

}

