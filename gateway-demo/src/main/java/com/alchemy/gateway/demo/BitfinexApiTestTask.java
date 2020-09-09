package com.alchemy.gateway.demo;

import com.alchemy.gateway.core.ExchangeApi;
import com.alchemy.gateway.core.ExchangeManager;
import com.alchemy.gateway.core.common.*;
import com.alchemy.gateway.core.marketdata.*;
import com.alchemy.gateway.core.wallet.AccountApi;
import com.alchemy.gateway.core.wallet.AccountAssetResp;
import com.alchemy.gateway.core.wallet.CursorVo;
import com.alchemy.gateway.core.wallet.DepositWithdrawResult;
import com.alchemy.gateway.exchangeclients.bitfinex.BitfinexExchangeApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Bitfinex api test
 *
 * @author allengent
 */
@Slf4j
//@Component
public class BitfinexApiTestTask implements CommandLineRunner {
    private ExchangeManager exchangeManager;

    public BitfinexApiTestTask(ExchangeManager exchangeManager) {
        this.exchangeManager = exchangeManager;
    }

    @Override
    public void run(String... args) {
        ExchangeApi exchangeApi = exchangeManager.getAPI(BitfinexExchangeApi.NAME);
        log.info("准备订阅Bitfinex行情数据==============");
        MarketDataApi marketDataApi = exchangeApi.getMarketDataApi();
        marketDataApi.subscribe(new Market(new CoinPair("ETH", "USDT"), MarketType.SPOT,0), marketDataApi.supportCandleIntervals(),
                false, true, false, new MarketDataReceiver() {
                    @Override
                    public void receiveCandles(CandleInterval candleInterval, CandleTick candleTick) {
                     //   log.info("Bitfinex 接收kline数据,interval:{},candleTick:{}", candleInterval, candleTick);
                    }
                    @Override
                    public void receiveOrderBook(OrderBook orderBook) {
                      //  log.info("Bitfinex 接收orderBook 数据:{} ", orderBook);
                    }

                    @Override
                    public void receiveTrades(List<TradeTick> tradeTicks) {
                     //   log.info("Bitfinex 接收TradeTick数据 {}", tradeTicks);

                    }


                    @Override
                    public void receive24Tickers(Ticker ticker) {
                        log.info("Bitfinex  接收 24 ticker数据:{}", ticker);
                    }
                });

        List<CandleTick> list=marketDataApi.getHistory(new Market(new CoinPair("ETH", "USDT"), MarketType.SPOT,0), CandleInterval.MINUTES_1, LocalDateTime.now().minusDays(1));
        for(CandleTick tick :list)
        {
            log.info("candle tick: {}",tick);
        }


        AccountApi accountApi = exchangeApi.getAccountApi();
        Credentials credentials = Credentials.of("utnN8cgNIAKkw6C7uHfymn2DHLpkYx3TnOYjiSY9Wde", "rP0yMeqTS7jXkc4TcJO0pHNEIX265GztnWeLMzzlCeL", null);

        CursorVo vo = CursorVo.builder().time(1594887583712L).build();
        List<AccountAssetResp> userAsset = accountApi.findUserAsset(credentials);
//        Boolean userStatus = accountApi.findUserStatus(credentials);
        accountApi.findUserAsset(credentials);
        DepositWithdrawResult depositWithdraws = accountApi.findDepositWithdraws(credentials, vo);
        log.info(" bitfinex 用户充提币记录:{}", depositWithdraws);
    }
}
