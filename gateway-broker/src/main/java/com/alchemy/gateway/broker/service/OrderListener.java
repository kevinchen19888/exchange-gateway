package com.alchemy.gateway.broker.service;

import com.alchemy.gateway.broker.config.RabbitConstants;
import com.alchemy.gateway.broker.vo.OrderCancelMessage;
import com.alchemy.gateway.broker.vo.OrderLimitMessage;
import com.alchemy.gateway.broker.vo.OrderMarketMessage;
import com.alchemy.gateway.broker.vo.OrderStopLimitMessage;
import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.order.OperatorType;
import com.alchemy.gateway.core.order.OrderSide;
import com.alchemy.gateway.core.order.OrderType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * describe:
 *
 * @author zoulingwei
 */
@Component
@Slf4j
public class OrderListener {

    private OrderMessageHandler orderMessageHandler;

    @Autowired
    public OrderListener(OrderMessageHandler orderMessageHandler) {
        this.orderMessageHandler = orderMessageHandler;
    }

    @RabbitListener(queues = RabbitConstants.QUEUE_ORDER_LIMIT)
    public void orderLimitQueueListener(OrderLimitMessage orderLimitMessage) {
        log.info("队列 {} 接收到消息 {}", RabbitConstants.QUEUE_ORDER_LIMIT, orderLimitMessage.toString());
        try {
            BigDecimal price = new BigDecimal(orderLimitMessage.getPrice());

            BigDecimal amount = new BigDecimal(orderLimitMessage.getAmount());
            CoinPair coinPair = CoinPair.fromSymbol(orderLimitMessage.getSymbol());

            OrderSide orderSide = orderLimitMessage.getDirection() == 1 ? OrderSide.BUY : OrderSide.SELL;
            orderMessageHandler.orderSubmit(orderLimitMessage.getAccountId(), orderLimitMessage.getExchangeName(), OrderType.LIMIT, price,
                    amount, orderSide, Long.valueOf(orderLimitMessage.getOrderId()), coinPair, null,null);
        } catch (Exception e) {
            log.info("限价委托消息异常:" + orderLimitMessage.toString() + ";异常:" + e);
        }
    }

    @RabbitListener(queues = RabbitConstants.QUEUE_ORDER_MARKET)
    public void orderMarketQueueListener(OrderMarketMessage orderMarketMessage) {
        log.info("队列 {} 接收到消息 {}", RabbitConstants.QUEUE_ORDER_MARKET, orderMarketMessage.toString());
        try {
            BigDecimal amount = new BigDecimal(orderMarketMessage.getAmount());
            CoinPair coinPair = CoinPair.fromSymbol(orderMarketMessage.getSymbol());
            OrderSide orderSide = orderMarketMessage.getDirection() == 1 ? OrderSide.BUY : OrderSide.SELL;
            orderMessageHandler.orderSubmit(orderMarketMessage.getAccountId(), orderMarketMessage.getExchangeName(), OrderType.MARKET, null,
                    amount, orderSide, Long.valueOf(orderMarketMessage.getOrderId()), coinPair, null,null);
        } catch (Exception e) {
            log.error("市价委托消息异常:" + orderMarketMessage.toString() + ";异常:" + e);
        }
    }

    @RabbitListener(queues = RabbitConstants.QUEUE_ORDER_STOPLIMIT)
    public void orderStoplimitQueueListener(OrderStopLimitMessage orderStopLimitMessage) {
        log.info("队列 {} 接收到消息 {}", RabbitConstants.QUEUE_ORDER_STOPLIMIT, orderStopLimitMessage.toString());
        try {
            BigDecimal price = new BigDecimal(orderStopLimitMessage.getPrice());
            BigDecimal amount = new BigDecimal(orderStopLimitMessage.getAmount());
            OrderSide orderSide = orderStopLimitMessage.getDirection() == 1 ? OrderSide.BUY : OrderSide.SELL;
            BigDecimal stopPrice = new BigDecimal(orderStopLimitMessage.getStopPrice());
            CoinPair coinPair = CoinPair.fromSymbol(orderStopLimitMessage.getSymbol());
            OperatorType operatorType = null;
            if (orderStopLimitMessage.getOperator() != null) {
                operatorType = orderStopLimitMessage.getOperator() == 1 ? OperatorType.GTE : OperatorType.LTE;
            }
            orderMessageHandler.orderSubmit(orderStopLimitMessage.getAccountId(), orderStopLimitMessage.getExchangeName(), OrderType.STOP_LIMIT, price,
                    amount, orderSide, Long.valueOf(orderStopLimitMessage.getOrderId()), coinPair, stopPrice, operatorType);
        } catch (Exception e) {
            log.error("限价止损委托消息异常:" + orderStopLimitMessage.toString() + ";异常:" + e);
        }
    }

    @RabbitListener(queues = RabbitConstants.QUEUE_ORDER_CANCEL)
    public void orderCancelQueueListener(OrderCancelMessage orderCancelMessage) {
        log.info("队列 {} 接收到消息 {}", RabbitConstants.QUEUE_ORDER_CANCEL, orderCancelMessage.toString());
        try {
            orderMessageHandler.orderCancel(orderCancelMessage.getAccountId(), orderCancelMessage.getExchangeName(), orderCancelMessage.getOrderId());
        } catch (Exception e) {
            log.error("撤销委托消息异常:" + orderCancelMessage.toString() + ";异常:" + e);
        }
    }

}
