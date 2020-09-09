package com.alchemy.gateway.quotation.config;

import com.alchemy.gateway.quotation.util.RabbitConfigUtils;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public class QuotationRabbitConfig {
    @Bean
    @ConditionalOnMissingBean(TopicExchange.class)
    public TopicExchange topicMarket() {
        return RabbitConfigUtils.createTopic();
    }

    @Bean
    @ConditionalOnMissingBean(RabbitTemplate.class)
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return RabbitConfigUtils.createRabbitTemplate(connectionFactory);
    }

    @Bean
    @ConditionalOnMissingBean(RabbitListenerContainerFactory.class)
    public RabbitListenerContainerFactory marketListenerContainerFactory(ConnectionFactory connectionFactory) {
        // 需要 @EnableRabbit 注解
        return RabbitConfigUtils.createRabbitListenerContainerFactory(connectionFactory);
    }

}
