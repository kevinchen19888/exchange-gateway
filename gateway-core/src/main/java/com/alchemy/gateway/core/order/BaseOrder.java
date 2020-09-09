package com.alchemy.gateway.core.order;

import lombok.Data;

/**
 * @author kevin chen
 */
@Data
public class BaseOrder {
    private Long mineOrderId;
    private Long accountId;
    private String orderId;
    private String orderPrice;
    private String orderSide;
    private String exchangeName;

    /**
     * 订单状态
     */
    private String state;
    private String symbol;
    private String orderAmount;
    private String orderCreatedAt;

}
