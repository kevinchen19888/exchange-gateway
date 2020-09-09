package com.alchemy.gateway.demo;

import com.alchemy.gateway.core.ExchangeManager;
import com.alchemy.gateway.core.ExchangeManagerImpl;
import com.alchemy.gateway.core.common.*;
import com.alchemy.gateway.core.order.*;
import com.alchemy.gateway.core.order.tracker.OrderTracker;
import com.alchemy.gateway.core.order.tracker.ThreadedOrderTracker;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
public class DemoOrderManager implements OrderStatusCallback, AccountInfoProvider {
    private final ExchangeManager exchangeManager;

    public DemoOrderManager(ExchangeManager exchangeManager) {
        this.exchangeManager = exchangeManager;
    }

    @SneakyThrows
    public void demo() {
        OrderApi orderApi = exchangeManager.getAPI("bitfinex").getOrderApi();
        OrderTracker orderTracker = new ThreadedOrderTracker(this, exchangeManager);
        OrderManager orderManager = new OrderManagerImpl(orderApi, orderTracker);

        Credentials credentials = Credentials.of("apikey", "secretKey", "passphrase"); // 获取 凭证
        OrderRequest orderRequest = new OrderRequest(1232141234L, 1232141234L,
                Market.spotMarket(CoinPair.of("BTC", "USDT")), OrderType.MARKET, new BigDecimal("0.010000000000"),
                new BigDecimal("0.010000000000"), new BigDecimal("0.010000000000"), OrderSide.BUY, "Okex",null);
        OrderVo orderVo = orderManager.placeOrder(credentials, orderRequest, this);
        Thread.sleep(30000);    // 等待，期间会回调
    }

    @Override
    public void stateChanged(OrderVo orderVo) {
        log.info("stateChanged: {} - {} - {}", orderVo.getExchangeOrderId(), orderVo.getOrderState(), orderVo);
    }

    @Override
    public AccountInfo getAccountInfo(Long accountId) {
        return AccountInfo.of("bitfinex",
                Credentials.of("utnN8cgNIAKkw6C7uHfymn2DHLpkYx3TnOYjiSY9Wde", "rP0yMeqTS7jXkc4TcJO0pHNEIX265GztnWeLMzzlCeL", null));
    }

    @Override
    public void insertTrade(List<TradeVo> tradeVos, Long orderId) {

    }
}

