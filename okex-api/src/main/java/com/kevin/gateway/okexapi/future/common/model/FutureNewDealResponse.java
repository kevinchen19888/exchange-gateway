package com.kevin.gateway.okexapi.future.common.model;

import com.kevin.gateway.okexapi.base.util.OrderSide;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FutureNewDealResponse {


    /**
     * 记录成交信息的递增ID
     */
    private String tradeId;

    /**
     * 指Taker订单的下单方向
     */
    private OrderSide side;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private int qty;

    /**
     * 时间
     */
    private LocalDateTime timestamp;

}
