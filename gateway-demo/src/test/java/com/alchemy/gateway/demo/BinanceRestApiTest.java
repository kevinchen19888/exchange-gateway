package com.alchemy.gateway.demo;

import com.alchemy.gateway.core.AbstractExchangeApi;
import com.alchemy.gateway.core.common.*;
import com.alchemy.gateway.core.marketdata.CandleTick;
import com.alchemy.gateway.core.marketdata.MarketDataApi;
import com.alchemy.gateway.core.order.*;
import com.alchemy.gateway.core.wallet.AccountApi;
import com.alchemy.gateway.core.wallet.AccountAssetResp;
import com.alchemy.gateway.core.wallet.CursorVo;
import com.alchemy.gateway.core.wallet.DepositWithdrawResult;
import com.alchemy.gateway.exchangeclients.binance.BinanceExchangeApi;
import com.alchemy.gateway.exchangeclients.binance.domain.BinanceOrder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author kevin chen
 */
@Slf4j
public class BinanceRestApiTest {
    private final String apiKey = "";
    private final String secretKey = "";
    private AbstractExchangeApi exchangeApi = new BinanceExchangeApi();
    private Credentials credentials = Credentials.of(apiKey, secretKey, "");
    private Market market = new Market(new CoinPair("ETH", "USDT"), MarketType.SPOT, 0);
    private ExecutorService pool = Executors.newFixedThreadPool(10);

    {
        exchangeApi.setRestTemplate(new RestTemplate());
    }

    /**
     * todo
     */
    @Test
    public void placeOrder() {
        OrderApi orderApi = exchangeApi.getOrderApi();
        OrderRequest orderRequest = new OrderRequest(1232141234L, 1232141234L,
                Market.spotMarket(CoinPair.of("BTC", "USDT")), OrderType.MARKET, new BigDecimal("0.010000000000"),
                new BigDecimal("0.010000000000"), new BigDecimal("0.010000000000"), OrderSide.BUY, "Okex", null);

        OrderVo orderVo = orderApi.placeOrder(credentials, orderRequest);
        log.info("placeOrder result:{}", orderVo);
    }

    @Test
    public void cancelOrder() {
        OrderApi orderApi = exchangeApi.getOrderApi();
        boolean isSuccess = orderApi.cancelOrder(credentials, "2831297217", new CoinPair("BTC", "USDT"), null);

        Assertions.assertTrue(isSuccess);
    }

    @Test
    public void getOrder() {
        OrderApi orderApi = exchangeApi.getOrderApi();
        BinanceOrder order = (BinanceOrder) orderApi.getOrder(credentials, "2831297217", new CoinPair("BTC", "USDT"), null);
        log.info("getOrder:{}", order);
        log.info("orderState:{}",order.getOrderState());
    }

    @Test
    public void getTrades() {
        OrderApi orderApi = exchangeApi.getOrderApi();
        BinanceOrder vo = new BinanceOrder();
        vo.setMarket(market);

        TradesResult trades = orderApi.getTrades(credentials, vo);
        log.info("getTrades:{}", trades);
    }

    @Test
    public void getHistoryOrder() {
        OrderApi orderApi = exchangeApi.getOrderApi();

        CursorVo cur = CursorVo.builder().build();
        //cur.setRecordId("2831297217");
        HistoryOrderResult historyOrder = orderApi.getHistoryOrder(credentials, cur, market);
        List<OrderState> orderStates = historyOrder.getList().stream().map(a -> a.getOrderState()).collect(Collectors.toList());
        //log.info("getHistoryOrder:{}", historyOrder);
        log.info("getHistoryOrder orderStates:{}", orderStates);

    }

    @Test
    public void findUserAsset() throws InterruptedException {
        AccountApi accountApi = exchangeApi.getAccountApi();
        List<AccountAssetResp> userAsset = accountApi.findUserAsset(credentials);
        userAsset = userAsset.stream().filter(s -> s.getBalance().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList());
        log.info("findUserAsset:{}", userAsset);
        //final int threads = 20;
        //CountDownLatch latch = new CountDownLatch(threads);
        //long begin = System.currentTimeMillis();
        //
        //for (int i = 0; i < threads; i++) {
        //    pool.submit(new LimiterTask(accountApi, latch, credentials));
        //}
        //
        //latch.await();
        //System.out.println("执行耗时:" + (System.currentTimeMillis() - begin));
        //pool.shutdown();
    }

    private class LimiterTask implements Runnable {
        private AccountApi accountApi;
        private CountDownLatch latch;
        private Credentials credentials;

        private LimiterTask(AccountApi accountApi, CountDownLatch latch, Credentials credentials) {
            this.accountApi = accountApi;
            this.latch = latch;
            this.credentials = credentials;
        }

        @Override
        public void run() {
            accountApi.findUserAsset(credentials);
            //System.out.println(Thread.currentThread().getName() + ":LimiterTask execute");
            latch.countDown();
        }
    }


    @Test
    public void depositWithdrawRecord() {
        AccountApi accountApi = exchangeApi.getAccountApi();
        DepositWithdrawResult depositWithdraws = accountApi.findDepositWithdraws(credentials, CursorVo.builder().build());
        log.info("binance depositWithdrawRecord:{}", depositWithdraws);
    }

    @Test
    public void getHistory() {
        MarketDataApi marketDataApi = exchangeApi.getMarketDataApi();
        List<CandleTick> history = marketDataApi.getHistory(market, CandleInterval.MONTH_1_M, LocalDateTime.now().minusYears(53), null);

        log.info("binance market history:{}", history);
    }


    @Test
    public void getExchangeInfo() {
        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + "/api/v3/exchangeInfo";
        ResponseEntity<String> resp = exchangeApi.getRestTemplate().getForEntity(url, String.class);

        JSONArray symbols = JSON.parseObject(resp.getBody()).getJSONArray("symbols");
        Set<String> baseAssets = new HashSet<>();
        for (int i = 0; i < symbols.size(); i++) {
            JSONObject symbol = symbols.getJSONObject(i);
            JSONArray permissions = symbol.getJSONArray("permissions");
            if (permissions.contains("SPOT")) {
                baseAssets.add(symbol.getString("quoteAsset"));
            }
        }
        System.out.println(baseAssets);

    }

}
