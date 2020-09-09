package com.alchemy.gateway.demo;

import com.alchemy.gateway.core.ExchangeApi;
import com.alchemy.gateway.core.ExchangeManager;
import com.alchemy.gateway.core.common.CandleInterval;
import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.common.MarketType;
import com.alchemy.gateway.core.marketdata.*;
import com.alchemy.gateway.exchangeclients.binance.BinanceExchangeApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


/**
 * binance api test
 *
 * @author kevin chen
 */
@Slf4j
//@Component
public class BinanceWebsocketTest implements CommandLineRunner {
    private ExchangeManager exchangeManager;

    @Autowired
    public BinanceWebsocketTest(ExchangeManager exchangeManager) {
        this.exchangeManager = exchangeManager;
    }

    @Override
    public void run(String... args) {
        ExchangeApi exchangeApi = exchangeManager.getAPI(BinanceExchangeApi.NAME);
        log.info("准备订阅行情数据==============");
        MarketDataApi marketDataApi = exchangeApi.getMarketDataApi();
        marketDataApi.subscribe(new Market(new CoinPair("BTC", "USDT"), MarketType.SPOT, 0),
                Arrays.asList(CandleInterval.MINUTES_1)/*marketDataApi.supportCandleIntervals()*/, false, false, false, new MarketDataReceiver() {
                    @Override
                    public void receiveCandles(CandleInterval candleInterval, CandleTick candleTick) {
                        log.info("binance 接收的  kline数据,interval:{},tick:{}", candleInterval.getSymbol(), candleTick);
                    }

                    @Override
                    public void receiveOrderBook(OrderBook orderBook) {
                        log.info("binance 接收到的  深度数据:{}", orderBook);
                    }

                    @Override
                    public void receiveTrades(List<TradeTick> tradeTicks) {
                        log.info("binance 接收到的  最近成交数据:{}", tradeTicks);
                    }

                    @Override
                    public void receive24Tickers(Ticker ticker) {
                        log.info("binance 接收到的  24h ticker数据:{}", ticker);
                    }
                }
        );

    }
}
