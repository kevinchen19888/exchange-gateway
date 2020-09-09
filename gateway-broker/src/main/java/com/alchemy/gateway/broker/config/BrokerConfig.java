package com.alchemy.gateway.broker.config;

import com.alchemy.gateway.core.ExchangeManager;
import com.alchemy.gateway.core.ExchangeManagerImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.client.standard.WebSocketContainerFactoryBean;

@Configuration
@EntityScan(
        basePackages = "com.alchemy.gateway.broker.entity"
)
@EnableJpaRepositories(
        basePackages = "com.alchemy.gateway.broker.repository"
)
public class BrokerConfig {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    WebSocketClient webSocketClient() {
        WebSocketContainerFactoryBean container = new WebSocketContainerFactoryBean();
        container.setMaxTextMessageBufferSize(163840);
        container.setMaxBinaryMessageBufferSize(81920);


        try {
            return new StandardWebSocketClient(container.getObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    ExchangeManager exchangeManager(RestTemplate restTemplate, WebSocketClient webSocketClient) {
        return new ExchangeManagerImpl(restTemplate, webSocketClient);
    }
}
