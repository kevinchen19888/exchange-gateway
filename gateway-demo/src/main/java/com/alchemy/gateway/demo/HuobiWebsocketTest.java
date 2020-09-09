package com.alchemy.gateway.demo;

import com.alchemy.gateway.core.ExchangeApi;
import com.alchemy.gateway.core.ExchangeManager;
import com.alchemy.gateway.core.ExchangeManagerImpl;
import com.alchemy.gateway.core.common.CandleInterval;
import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.common.MarketType;
import com.alchemy.gateway.core.marketdata.*;
import com.alchemy.gateway.exchangeclients.huobi.HuobiExchangeApi;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.client.standard.WebSocketContainerFactoryBean;

import javax.websocket.WebSocketContainer;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * binance api test
 *
 * @author kevin chen
 */
@Slf4j
@Component
public class HuobiWebsocketTest implements CommandLineRunner {
    private ExchangeManager exchangeManager;

    @Autowired
    public HuobiWebsocketTest(ExchangeManager exchangeManager) {
        this.exchangeManager = exchangeManager;
    }


    @Override
    public void run(String... args) {
        ExchangeApi exchangeApi = exchangeManager.getAPI(HuobiExchangeApi.NAME);
        //log.info("准备订阅huobi行情数据==============");
        //MarketDataApi marketDataApi = exchangeApi.getMarketDataApi();
        //marketDataApi.subscribe(new Market(new CoinPair("BTC", "USDT"), MarketType.SPOT,0),
        //        Arrays.asList(CandleInterval.MINUTES_15MIN)/*marketDataApi.supportCandleIntervals()*/,
        //        false, false, false, new MarketDataReceiver() {
        //            @Override
        //            public void receiveCandles(CandleInterval candleInterval, CandleTick candleTick) {
        //                log.info("huobi 接收的 kline数据,interval:{},tick:{}", candleInterval.getSymbol(), candleTick);
        //            }
        //
        //            @Override
        //            public void receiveOrderBook(OrderBook orderBook) {
        //                log.info("huobi 接收到的深度数据:{}", orderBook);
        //            }
        //
        //            @Override
        //            public void receiveTrades(List<TradeTick> tradeTicks) {
        //                log.info("huobi 接收到的最近成交数据:{}",tradeTicks);
        //            }
        //
        //            @Override
        //            public void receive24Tickers(Ticker ticker) {
        //                log.info("huobi 接收到的24h ticker数据:{}", ticker);
        //            }
        //        }
        //);


    }

    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(5);

        RestTemplate restTemplate = new RestTemplate();
        WebSocketContainerFactoryBean container = new WebSocketContainerFactoryBean();
        container.setMaxTextMessageBufferSize(163840);
        container.setMaxBinaryMessageBufferSize(81920);
        WebSocketClient webSocketClient = new StandardWebSocketClient(Objects.requireNonNull(container.getObject()));

        ExchangeManager exchangeManager = new ExchangeManagerImpl(restTemplate, webSocketClient);
        Market market = new Market(new CoinPair("BTC", "USDT"), MarketType.SPOT, 0);

        for (int i = 0; i < 5; i++) {
            pool.submit(() -> {
                try {

                    ExchangeApi exchangeApi = exchangeManager.getAPI(HuobiExchangeApi.NAME);
                    MarketDataApi marketDataApi = exchangeApi.getMarketDataApi();
                    List<CandleTick> history = marketDataApi.getHistory(market, CandleInterval.MINUTES_1MIN,
                            LocalDateTime.now(ZoneOffset.UTC).minusMinutes(900),
                            LocalDateTime.now(ZoneOffset.UTC));

                    Thread.sleep(5000);
                    System.out.println("history size:" + history.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        pool.shutdown();


    }


}
