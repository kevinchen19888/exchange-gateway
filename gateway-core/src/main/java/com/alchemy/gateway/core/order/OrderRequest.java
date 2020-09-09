package com.alchemy.gateway.core.order;

import com.alchemy.gateway.core.common.Market;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequest {
    private final Long alchemyId;//平台订单id
    private final Long accountId;//服务内部用户id
    private final Market market;
    private final OrderType type;
    private final BigDecimal price;
    private final BigDecimal stopPrice;
    private final BigDecimal amount;
    private final OrderSide orderSide;
    private final String exchangeName;
    private final OperatorType operatorType;
}
