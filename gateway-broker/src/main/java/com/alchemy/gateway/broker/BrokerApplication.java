package com.alchemy.gateway.broker;

import com.alchemy.gateway.broker.config.BrokerConfig;
import com.alchemy.gateway.broker.config.RabbitConfig;
import com.alchemy.gateway.broker.config.RedisConfig;
import com.alchemy.gateway.market.MarketConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = {
        BrokerConfig.class, MarketConfig.class, RedisConfig.class, RabbitConfig.class})
public class BrokerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrokerApplication.class, args);
    }

}
