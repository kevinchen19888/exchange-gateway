package com.alchemy.gateway.demo;

import com.alchemy.gateway.core.AbstractExchangeApi;
import com.alchemy.gateway.core.ExchangeApi;
import com.alchemy.gateway.core.common.*;
import com.alchemy.gateway.core.marketdata.CandleTick;
import com.alchemy.gateway.core.marketdata.MarketDataApi;
import com.alchemy.gateway.core.order.*;
import com.alchemy.gateway.core.wallet.*;
import com.alchemy.gateway.exchangeclients.common.HttpUtil;
import com.alchemy.gateway.exchangeclients.huobi.HuobiExchangeApi;
import com.alchemy.gateway.exchangeclients.huobi.domain.HuobiOrderVo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author kevin chen
 */
@Slf4j
public class HuobiRestApiTest {
    private ExecutorService pool = Executors.newFixedThreadPool(10);
    private final String apiKey = "";
    private final String secretKey = "";
    private Credentials credentials = Credentials.of(apiKey, secretKey, "");

    private Market market = new Market(new CoinPair("BTC", "USDT"), MarketType.SPOT, 0);
    private AbstractExchangeApi exchangeApi = new HuobiExchangeApi();

    {
        exchangeApi.setRestTemplate(new RestTemplate());
    }

    /**
     * warning:下单接口涉及资金,慎用
     */
    @Test
    public void placeOrder() {
        OrderApi orderApi = exchangeApi.getOrderApi();
        CoinPair coinPair = CoinPair.of("ETH", "USDT");

        orderApi.initOrderLimit(Arrays.asList(Market.spotMarket(coinPair)));

        exchangeApi.getOrderLimitManager().getOrderLimit(exchangeApi.getName() + coinPair.toSymbol());

        OrderRequest request = new OrderRequest(null, null,
                Market.spotMarket(coinPair), OrderType.STOP_LIMIT,
                new BigDecimal("300"),
                new BigDecimal("400"),
                new BigDecimal("0.04"),
                OrderSide.BUY, exchangeApi.getName(), OperatorType.GTE);

        orderApi.placeOrder(credentials, request);
    }

    @Test
    public void getOrder() {
        OrderApi orderApi = exchangeApi.getOrderApi();

        OrderVo order = orderApi.getOrder(credentials, "71366805847790", new CoinPair("XRP", "USDT"), OrderType.MARKET);
        log.info("order is:{}", order);
    }

    @Test
    public void getHistoryOrder() {
        OrderApi orderApi = exchangeApi.getOrderApi();
        CursorVo vo = null;
        //vo = CursorVo.builder().recordId("83704744657716").time(1597743354000L).build();
        HistoryOrderResult historyOrder = orderApi.getHistoryOrder(credentials, vo, market);
        List<OrderState> orderStates = historyOrder.getList().stream().map(a -> a.getOrderState()).collect(Collectors.toList());
        //log.info("historyOrder:{}", historyOrder);
        log.info("historyOrder orderStates:{}", orderStates);


    }

    @Test
    public void cancel() {
        OrderApi orderApi = exchangeApi.getOrderApi();
        boolean result = orderApi.cancelOrder(credentials, "71366805847790", new CoinPair("XRP", "USDT"), null);
        assertTrue(result);
    }

    @Test
    public void getTrades() {
        OrderApi orderApi = exchangeApi.getOrderApi();
        HuobiOrderVo vo = new HuobiOrderVo();
        vo.setSymbol("btc/usdt");
        vo.setOrderType("buy-market");// buy-stop-limit
        //vo.setExchangeOrderId("86695379821865");

        TradesResult trades = orderApi.getTrades(credentials, vo);
        log.info("trades is:{}", trades);
    }


    @Test
    public void findUserStatus() {
        AccountApi accountApi = exchangeApi.getAccountApi();
        Boolean status = accountApi.findUserStatus(credentials);

        assertTrue(status);
    }

    @Test
    public void findUserAsset() {
        AccountApi accountApi = exchangeApi.getAccountApi();
        List<AccountAssetResp> userAsset = accountApi.findUserAsset(credentials);
        log.info("userAsset:{}", userAsset);
    }

    @Test
    public void findDepositWithdraws() {
        AccountApi accountApi = exchangeApi.getAccountApi();
        CursorVo vo = CursorVo.builder().recordId("40831575").build();
        DepositWithdrawResult result = accountApi.findDepositWithdraws(credentials, vo);
        log.info("DepositWithdrawResult is:{}", result);
    }

    @Test
    public void findAssetTransfers() {
        AccountApi accountApi = exchangeApi.getAccountApi();
        AssetTransferResult result = accountApi.findAssetTransfers(credentials, null, null);

        log.info("AssetTransferResult is:{}", result);
    }

    /**
     * binance 暂不支持自定义时间区间查询历史行情
     */
    @Test
    public void getHistory() throws InterruptedException {
        final int threads = 10;
        CountDownLatch latch = new CountDownLatch(threads);
        long begin = System.currentTimeMillis();

        exchangeApi.setWebSocketClient(new StandardWebSocketClient());

        MarketDataApi marketDataApi = exchangeApi.getMarketDataApi();
        List<CandleTick> history = marketDataApi.getHistory(market, CandleInterval.MINUTES_1MIN,
                LocalDateTime.now(ZoneOffset.UTC).minusMinutes(3), LocalDateTime.now(ZoneOffset.UTC));

        //for (int i = 0; i < threads; i++) {
        //    pool.submit(new LimiterTask(exchangeApi, latch));
        //}
        //
        //latch.await();
        System.out.println("执行耗时:" + (System.currentTimeMillis() - begin));
        //pool.shutdown();
        //log.info("huobi market history:{}", history);
    }


    private class LimiterTask implements Runnable {
        private ExchangeApi exchangeApi;
        private CountDownLatch latch;

        private LimiterTask(ExchangeApi exchangeApi, CountDownLatch latch) {
            this.exchangeApi = exchangeApi;
            this.latch = latch;
        }

        @Override
        public void run() {
            exchangeApi.getMarketDataApi().getHistory(market, CandleInterval.MINUTES_1MIN, LocalDateTime.now().minusMinutes(10), null);
            latch.countDown();
        }
    }

    @Test
    public void getSymbols() {
        String uri = "/v1/common/symbols";
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", HttpUtil.USER_AGENT);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<JSONObject> exchangeResp = exchangeApi.getRestTemplate().exchange(exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + uri,
                HttpMethod.GET, httpEntity, JSONObject.class);
        JSONObject body = exchangeResp.getBody();
        Set<String> currencies = new LinkedHashSet<>();
        JSONArray data = body.getJSONArray("data");
        for (int i = 0; i < data.size(); i++) {
            currencies.add(data.getJSONObject(i).getString("quote-currency"));
        }

        log.info("resp symbols:{}", currencies);
    }

}
