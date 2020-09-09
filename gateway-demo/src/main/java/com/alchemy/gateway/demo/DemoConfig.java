package com.alchemy.gateway.demo;

import com.alchemy.gateway.core.ExchangeManager;
import com.alchemy.gateway.core.ExchangeManagerImpl;
import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.market.ExchangeMarket;
import com.alchemy.gateway.market.MarketManager;
import com.alchemy.gateway.market.MarketManagerImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.client.standard.WebSocketContainerFactoryBean;

import java.util.Arrays;

@Configuration
public class DemoConfig {

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

    @Bean
    MarketManager marketManager() {
        return new MarketManagerImpl();
    }

    @Bean
    CommandLineRunner commandLineRunner(ExchangeManager exchangeManager, MarketManager marketManager) {
        return args -> {
            marketManager.load(() -> Arrays.asList(
                  //  new ExchangeMarket("binance", Market.spotMarket(CoinPair.fromSymbol("BTC/USDT"))),
                    new ExchangeMarket("bitfinex", Market.spotMarket(CoinPair.fromSymbol("BTC/USDT")))
                 //   new ExchangeMarket("huobi", Market.spotMarket(CoinPair.fromSymbol("ETH/USDT")))
            ));
            System.out.printf("%s\n", marketManager.getExchangeNames());
            System.out.printf("%s\n", marketManager.getMarkets("huobi"));

//            {
//                // TODO: 移动到单元测试中
//                CoinUtils.checkWellKnownCoin("XETH");
//                CoinPair coinPair = CoinPair.fromSymbol("XETH/USDT");
//                System.out.printf("%s", coinPair);
//            }

//            System.out.printf("%s, %s, %s, %s\n",
//                    exchangeManager.getNames(),
//                    exchangeManager.supports("huobi"),
//                    exchangeManager.getAPI("binance").getName(),
//                    exchangeManager.getAPI("binance").getConnectionInfo()
//            );

//            // binance, BTC, USDT
//            Market market = Market.spotMarket(new CoinPair("BTC", "USDT"));
//            exchangeManager.getAPI("binance").getMarketDataApi().subscribe(market, new MarketDataReceiver() {
//                @Override
//                public void receiveCandles(CandleInterval candleInterval, CandleTick candleTick) {
//
//                }
//
//                @Override
//                public void receiveOrderBook(OrderBook orderBook) {
//
//                }
//
//                @Override
//                public void receiveTrades(List<TradeTick> tradeTicks) {
//
//                }
//            });
        };
    }

}
