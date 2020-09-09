package com.alchemy.gateway.broker.service;

import com.alchemy.gateway.broker.config.RabbitConstants;
import com.alchemy.gateway.broker.entity.Trade;
import com.alchemy.gateway.broker.vo.OrderRecordMessage;
import com.alchemy.gateway.broker.vo.OrderSendMessage;
import com.alchemy.gateway.broker.vo.OrderStateEndMessage;
import com.alchemy.gateway.core.order.OrderState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * describe:MQ消息发送服务
 *
 * @author zoulingwei
 */
@Component
@Slf4j
public class OrderMessageSender {

    private final RabbitTemplate template;

    @Autowired
    public OrderMessageSender(RabbitTemplate template) {
        this.template = template;
    }

    @Async
    public void sendPlaceOrderMessage(Long orderId, String exchangeName, int code, String msg) {
        OrderSendMessage orderSendMessage = OrderSendMessage.builder()
                .orderId(orderId)
                .errorCode(code)
                .errorText(msg)
                .build();
        template.convertAndSend(RabbitConstants.EXCHANGE_MINE_TOPIC,
                RabbitConstants.ROUTING_KEY_SEND_PLACE_ORDER, orderSendMessage);
        log.info("routingKey {} 发送消息 {}", RabbitConstants.ROUTING_KEY_SEND_PLACE_ORDER, orderSendMessage.toString());
    }

    @Async
    public void sendCancelOrderMessage(Long orderId, String exchangeName, int code, String msg) {
        OrderSendMessage orderSendMessage = OrderSendMessage.builder()
                .orderId(orderId)
                .errorCode(code)
                .errorText(msg)
                .build();
        template.convertAndSend(RabbitConstants.EXCHANGE_MINE_TOPIC,
                RabbitConstants.ROUTING_KEY_SEND_CANCEL_ORDER, orderSendMessage);
        log.info("routingKey {} 发送消息 {}", RabbitConstants.ROUTING_KEY_SEND_CANCEL_ORDER, orderSendMessage.toString());
    }

    @Async
    public void sendOrderRecord(Trade trade, Long mineOrderId) {
        OrderRecordMessage orderRecordMessage = OrderRecordMessage.builder()
                .orderId(mineOrderId)
                .orderRecordId(String.valueOf(trade.getId()))
                .exchangeDealId(String.valueOf(trade.getExchangeTradeId()))
                .price(trade.getPrice().toPlainString())
                .amount(trade.getFilledAmount().toPlainString())
                .fee(trade.getFilledFee().toPlainString())
                .feeCoin(trade.getFilledFeeCoin())
                .dealTime(trade.getCreatedAt()).build();
        template.convertAndSend(RabbitConstants.EXCHANGE_MINE_TOPIC, RabbitConstants.ROUTING_KEY_SEND_ORDER_RECORD, orderRecordMessage);
        log.info("routingKey {} 发送消息 {}", RabbitConstants.ROUTING_KEY_SEND_ORDER_RECORD, orderRecordMessage.toString());
    }


    public void sendOrderStateEnd(Long mineOrderId, OrderState state, BigDecimal finishedAmount, BigDecimal finishedVolume, BigDecimal finishedFee, BigDecimal rebate, String rebateCoin) {
        OrderStateEndMessage orderStateEndMessage = OrderStateEndMessage.builder()
                .orderId(mineOrderId)
                .status(String.valueOf(state.getIntValue()))
                .fee(finishedFee!=null?finishedFee.toPlainString():"0")
                .finishedAmount(finishedAmount!=null?finishedAmount.toPlainString():"0")
                .finishedVolume(finishedVolume!=null?finishedVolume.toPlainString():"0")
                .rebate(rebate!=null?rebate.toPlainString():"")
                .rebateCoin(rebateCoin!=null?rebateCoin:"")
                .build();
        template.convertAndSend(RabbitConstants.EXCHANGE_MINE_TOPIC, RabbitConstants.ROUTING_KEY_SEND_ORDER_STATUS, orderStateEndMessage);
        log.info("routingKey {} 发送消息 {}", RabbitConstants.ROUTING_KEY_SEND_ORDER_STATUS, orderStateEndMessage.toString());
    }
}
