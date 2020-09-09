package com.alchemy.gateway.quotation.util;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;


public class RabbitConfigUtils {

    public static final String ALCHEMY = "alchemy";
    public static final String GATEWAY = "gateway";
    public static final String ROUTING_KEY_KLINE = "kline";
    public static final String ROUTING_KEY_DEPTH = "depth";
    public static final String ROUTING_KEY_TICKER = "tick24h";
    public static final String ROUTING_KEY_TRADE = "trade";
    public static final String TOPIC_NAME_DEFAULT = "alchemy.gateway.quotation.topic";
    private static final String[] DEFAULT_JSON_TRUSTED_PACKAGE = {
            "*"
    };

    public static TopicExchange createTopic(String name) {
        return new TopicExchange(name, true, false);
    }

    public static TopicExchange createTopic() {
        return createTopic(TOPIC_NAME_DEFAULT);
    }

    private static MessageConverter createMessageConverter(String[] trustedPackages) {
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages(trustedPackages);
        jackson2JsonMessageConverter.setClassMapper(classMapper);
        return jackson2JsonMessageConverter;
    }

    private static MessageConverter createMessageConverter() {
        return createMessageConverter(DEFAULT_JSON_TRUSTED_PACKAGE);
    }

    public static RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }


    public static RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        return createRabbitTemplate(connectionFactory, createMessageConverter());
    }

    public static RabbitListenerContainerFactory createRabbitListenerContainerFactory(ConnectionFactory connectionFactory, String[] jsonTrustedPackages) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(createMessageConverter(jsonTrustedPackages));
        return factory;
    }

    public static RabbitListenerContainerFactory createRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        return createRabbitListenerContainerFactory(connectionFactory, DEFAULT_JSON_TRUSTED_PACKAGE);
    }

}
