package com.alchemy.gateway.demo;

import com.alchemy.gateway.core.ExchangeApi;
import com.alchemy.gateway.core.ExchangeManager;
import com.alchemy.gateway.core.common.CandleInterval;
import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.marketdata.CandleTick;
import com.alchemy.gateway.core.marketdata.HistoryCrawler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DemoCrawler {

    private final ExchangeManager exchangeManager;

    public DemoCrawler(ExchangeManager exchangeManager) {
        this.exchangeManager = exchangeManager;
    }

    public void crawl() {

        // 演示数据，真实数据从数据库中获取
        List<Market> markets = Arrays.asList(
                Market.spotMarket(CoinPair.fromSymbol("BTC/USDT")),
                Market.spotMarket(CoinPair.fromSymbol("ETH/USDT")),
                Market.spotMarket(CoinPair.fromSymbol("BTC/ETH"))
        );

        ExchangeApi exchangeApi = exchangeManager.getAPI("binance");
        // 对于所有市场，开始爬取
        for (Market market : markets) {
            HistoryCrawler historyCrawler = new HistoryCrawler(exchangeApi, market);
            LocalDateTime startTime = LocalDateTime.of(2020, 7, 1, 0, 0);
            historyCrawler.start(CandleInterval.MINUTES_5, startTime, false);   // 向后爬，直到爬完所有数据！
            while (historyCrawler.hasNext()) {
                List<CandleTick> candleTickList = historyCrawler.next();
                // TODO: 将 candleTickList 写入数据库中
            }
        }
    }
}
