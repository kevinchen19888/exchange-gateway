package com.kevin.gateway.okexapi.swap.model;

import com.kevin.gateway.okexapi.base.util.OrderSide;
import com.kevin.gateway.okexapi.future.type.DirectionType;
import com.kevin.gateway.okexapi.future.type.OpenCloseLongShortType;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 获取成交明细
 */
@Data
public class SwapFillsDetailResponse {

    /**
     * 账单ID
     */
    private String tradeId;

    /**
     * 合约ID，如BTC-USD-SWAP
     */
    private SwapMarketId instrumentId;

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
    private LocalDateTime timestamp;

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
    private OrderSide orderSide;

    /**
     * 开仓方向
     */
    private DirectionType side;


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

