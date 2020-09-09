package com.alchemy.gateway.demo;

import com.alchemy.gateway.core.AbstractExchangeApi;
import com.alchemy.gateway.core.ExchangeManager;
import com.alchemy.gateway.core.common.CandleInterval;
import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.common.MarketType;
import com.alchemy.gateway.core.marketdata.*;
import com.alchemy.gateway.exchangeclients.huobi.HuobiExchangeApi;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@SpringBootTest
class DemoApplicationTests {

    private final String apiKey = "";
    private final String secretKey = "";

    @Autowired
    private ExchangeManager exchangeManager;

    @Test
    public void testHuobiKline() {
        AbstractExchangeApi huobiApi = new HuobiExchangeApi();
        huobiApi.setRestTemplate(new RestTemplate());

        MarketDataApi marketDataApi = huobiApi.getMarketDataApi();
        List<CandleTick> klines = marketDataApi.getHistory(new Market(new CoinPair("USDT", "BTC"), MarketType.SPOT,0),
                CandleInterval.MINUTES_15MIN,
                null, null);
        log.info("获取huobi Kline数据:{}", klines);
    }

    @Test
    public void testHuobiWebsocket() {

    }


    @Test
    void contextLoads() {
    }

}
