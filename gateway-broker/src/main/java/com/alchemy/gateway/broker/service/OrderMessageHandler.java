package com.alchemy.gateway.broker.service;

import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.order.OperatorType;
import com.alchemy.gateway.core.order.OrderSide;
import com.alchemy.gateway.core.order.OrderType;

import java.math.BigDecimal;

/**
 * describe:
 *
 * @author zoulingwei
 */
public interface OrderMessageHandler {

    void orderSubmit(String accountId, String eName, OrderType type, BigDecimal price, BigDecimal amount, OrderSide side,
                     Long orderId, CoinPair coinPair, BigDecimal stopPrice, OperatorType operatorType);

    void orderCancel(String accountId, String exchangeName, Long orderId);

}
