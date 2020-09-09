package com.alchemy.gateway.broker.config;

import com.alchemy.gateway.core.ExchangeManager;
import com.alchemy.gateway.core.common.AccountInfoProvider;
import com.alchemy.gateway.core.order.tracker.ThreadedOrderTracker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderTrackerConfig {

    @Bean
    ThreadedOrderTracker orderTracker(ExchangeManager exchangeManager, AccountInfoProvider accountInfoProvider) {
        ThreadedOrderTracker threadedOrderTracker = new ThreadedOrderTracker(accountInfoProvider, exchangeManager);
        threadedOrderTracker.start();
        return threadedOrderTracker;
    }
}
