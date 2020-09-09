package com.alchemy.gateway.broker.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableRabbit
public class RabbitConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }
    @Bean
    Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Declarables declarables() {

        List<Declarable> declarableList = new ArrayList<>();

        Exchange exchange = ExchangeBuilder.topicExchange(RabbitConstants.EXCHANGE_MINE_TOPIC).build();
        declarableList.add(exchange);

        Queue[] accountQueues = {//账户相关队列
                QueueBuilder.durable(RabbitConstants.QUEUE_ACCOUNT_CREATE).build(),
                QueueBuilder.durable(RabbitConstants.QUEUE_ACCOUNT_DELETE).build(),
                QueueBuilder.durable(RabbitConstants.QUEUE_ACCOUNT_ENABLE).build(),
                QueueBuilder.durable(RabbitConstants.QUEUE_ACCOUNT_DISABLE).build(),
                QueueBuilder.durable(RabbitConstants.QUEUE_ACCOUNT_SYNC_ASSET).build(),
                QueueBuilder.durable(RabbitConstants.QUEUE_ACCOUNT_RESOLVE_ERROR).build()
        };

        for (Queue q : accountQueues) {
            declarableList.add(q);
            declarableList.add(BindingBuilder.bind(q).to(exchange).with(q.getName() + ".#").noargs());
        }

        Queue[] orderQueues = {//订单相关队列
                QueueBuilder.durable(RabbitConstants.QUEUE_ORDER_MARKET).build(),
                QueueBuilder.durable(RabbitConstants.QUEUE_ORDER_LIMIT).build(),
                QueueBuilder.durable(RabbitConstants.QUEUE_ORDER_STOPLIMIT).build(),
                QueueBuilder.durable(RabbitConstants.QUEUE_ORDER_CANCEL).build()
        };

        for (Queue q : orderQueues) {
            declarableList.add(q);
            declarableList.add(BindingBuilder.bind(q).to(exchange).with(q.getName() + ".#").noargs());
        }

        return new Declarables(declarableList.toArray(new Declarable[0]));
    }

}
