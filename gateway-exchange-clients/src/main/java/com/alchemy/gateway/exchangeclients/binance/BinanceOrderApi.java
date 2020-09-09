package com.alchemy.gateway.exchangeclients.binance;

import com.alchemy.gateway.core.GatewayException;
import com.alchemy.gateway.core.common.*;
import com.alchemy.gateway.core.order.*;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alchemy.gateway.core.wallet.CursorVo;
import com.alchemy.gateway.exchangeclients.binance.domain.BinanceOrder;
import com.alchemy.gateway.exchangeclients.binance.domain.BinanceOrderLimit;
import com.alchemy.gateway.exchangeclients.binance.domain.BinanceTradeVo;
import com.alchemy.gateway.exchangeclients.binance.util.BinanceOrderTypeConverter;
import com.alchemy.gateway.exchangeclients.common.HmacSHA256Signer;
import com.alchemy.gateway.exchangeclients.common.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author kevin chen
 */
@Slf4j
public class BinanceOrderApi implements OrderApi {
    private final BinanceExchangeApi exchangeApi;

    public BinanceOrderApi(BinanceExchangeApi exchangeApi) {
        this.exchangeApi = exchangeApi;
    }

    @Override
    public OrderVo placeOrder(Credentials credentials, @NonNull OrderRequest request) {
        log.info("binance placeOrder param:{}", request);

        rateLimit(exchangeApi.getName() + "placeOrder" + credentials.getApiKey());

        String innerSymbol = request.getMarket().getCoinPair().toSymbol();
        final String orderType = BinanceOrderTypeConverter.toOrderTypeStr(request.getType(), request.getOrderSide(), request.getStopPrice(), request.getPrice());

        // 进行参数校验&订单交易规则校验
        verifyOrderParams(request, innerSymbol, orderType);

        Map<String, Object> paramsMap = new LinkedHashMap<>();
        assert request.getType() != null;
        paramsMap.put("type", orderType);
        paramsMap.put("quantity", request.getAmount().stripTrailingZeros().toPlainString());
        paramsMap.put("symbol", innerSymbol.replace("/", ""));
        paramsMap.put("side", request.getOrderSide().name());
        paramsMap.put("timestamp", System.currentTimeMillis());
        if (OrderType.STOP_LIMIT == request.getType()) {
            paramsMap.put("stopPrice", request.getStopPrice().stripTrailingZeros().toPlainString());
        }
        if (request.getPrice() != null) {
            paramsMap.put("price", request.getPrice().stripTrailingZeros().toPlainString());
        }
        if (request.getType() != null && (OrderType.LIMIT == request.getType() || OrderType.STOP_LIMIT == request.getType())) {
            paramsMap.put("timeInForce", "GTC");
        }
        paramsMap.put("recvWindow", exchangeApi.recvWindow);
        paramsMap.put("signature", HmacSHA256Signer.sign(credentials.getSecretKey(), paramsMap));

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-MBX-APIKEY", credentials.getApiKey());
        String requestParams = HmacSHA256Signer.sortAndJointParam(paramsMap);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestParams, headers);
        ResponseEntity<String> exchangeResp;
        try {
            exchangeResp = exchangeApi.getRestTemplate().postForEntity(exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + "/api/v3/order",
                    httpEntity, String.class);
        } catch (RestClientException e) {
            log.error("binance placeOrder errorMsg:{}", e.getMessage());
            throw e;// TODO: 2020/8/18 待根据异常码进行分类处理
        }

        if (HttpUtil.isSuccessResp(exchangeResp)) {
            BinanceOrder order = new BinanceOrder();
            JSONObject jsonResp = JSON.parseObject(exchangeResp.getBody());
            order.setSymbol(exchangeApi.getCoinPairSymbolConverter().symbolToCoinPair(jsonResp.getString("symbol")).toSymbol());
            order.setOrderSide(jsonResp.getString("side"));
            order.setOrderId(jsonResp.getString("orderId"));
            order.setOrderType(request.getType().name());
            order.setOrderCreatedAt(jsonResp.getString("transactTime"));
            order.setOrderPrice(jsonResp.getString("price"));
            return order;
        } else {
            log.warn("binance下单请求失败-apiKey:{},statusCode:{}", credentials.getApiKey(), exchangeResp.getStatusCode());
            throw new GatewayException("binance下单请求失败,statusCode:" + exchangeResp.getStatusCode());
        }

    }

    @Override
    public OrderVo getOrder(Credentials credentials, @NonNull String orderId, @NonNull CoinPair coinPair, OrderType type) {
        log.info("binance getOrder param,orderId:{},coinPair:{}", orderId, coinPair);

        rateLimit(exchangeApi.getName() + "getOrder" + credentials.getApiKey());

        RestTemplate restTemplate = exchangeApi.getRestTemplate();

        Map<String, Object> params = new HashMap<>();
        long timeStamp = System.currentTimeMillis();
        params.put("orderId", orderId);
        final String symbol = coinPair.toSymbol().replace("/", "");
        params.put("recvWindow", exchangeApi.recvWindow);
        params.put("symbol", symbol);
        params.put("timestamp", timeStamp);
        String signature = HmacSHA256Signer.sign(credentials.getSecretKey(), params);
        params.put("signature", signature);
        String jointParam = HmacSHA256Signer.sortAndJointParam(params);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-MBX-APIKEY", credentials.getApiKey());
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> exchangeResp;
        try {
            exchangeResp = restTemplate.exchange(exchangeApi.getConnectionInfo().getRestfulApiEndpoint()
                    + "/api/v3/order?" + jointParam, HttpMethod.GET, httpEntity, String.class);
        } catch (RestClientException e) {
            // 订单不存在
            if (Objects.requireNonNull(e.getMessage()).contains("2013")) {
                log.warn("binance getOrder 订单不存在-orderId:{},coinPair:{}", orderId, coinPair);

                BinanceOrder order = new BinanceOrder();
                order.setFinishedFees(BigDecimal.ZERO);
                order.setFinishedAmount(BigDecimal.ZERO);
                order.setFinishedVolume(BigDecimal.ZERO);
                order.setOrderId(orderId);
                order.setSymbol(coinPair.toSymbol());
                order.setExchangeName(exchangeApi.getName());
                order.setMarket(Market.spotMarket(coinPair));
                order.setState("created");
                order.setFinishAt(LocalDateTime.now());
                order.setOrderAmount("0");
                return order;
            }
            throw e;
        }

        if (HttpUtil.isSuccessResp(exchangeResp)) {
            BinanceOrder order = new BinanceOrder();
            JSONObject jsonResp = JSON.parseObject(exchangeResp.getBody());
            order.setOrderId(jsonResp.getString("orderId"));
            order.setOrderPrice(jsonResp.getString("price"));
            order.setOrderCreatedAt(jsonResp.getString("time"));
            order.setOrderType(jsonResp.getString("type"));
            order.setOrderSide(jsonResp.getString("side"));
            order.setState(jsonResp.getString("status"));
            order.setStopPrice(jsonResp.getString("stopPrice"));

            CoinPairSymbolConverter converter = exchangeApi.getCoinPairSymbolConverter();
            order.setSymbol(converter.symbolToCoinPair(jsonResp.getString("symbol")).toSymbol());
            order.setMarket(Market.spotMarket(converter.symbolToCoinPair(jsonResp.getString("symbol"))));
            order.setOrderAmount(jsonResp.getString("origQty"));
            order.setFinishedVolume(new BigDecimal(jsonResp.getString("executedQty")));
            order.setFinishedAmount(new BigDecimal(jsonResp.getString("cummulativeQuoteQty")));
            order.setExchangeName(exchangeApi.getName());

            order.setFinishedFees(BigDecimal.ZERO); // binance 成交费用通过成交记录获取
            return order;
        } else {
            log.warn("binance查询订单请求失败-apiKey:{},statusCode:{}", credentials.getApiKey(), exchangeResp.getStatusCode());
            throw new GatewayException("binance查询订单请求失败,statusCode:" + exchangeResp.getStatusCode());
        }
    }

    @Override
    public boolean cancelOrder(Credentials credentials, @NonNull String orderId, @NonNull CoinPair coinPair, OrderType type) {
        log.info("binance cancelOrder param,orderId:{},coinPair:{}", orderId, coinPair);

        rateLimit(exchangeApi.getName() + "cancelOrder" + credentials.getApiKey());

        RestTemplate restTemplate = exchangeApi.getRestTemplate();

        Map<String, Object> params = new TreeMap<>();
        params.put("orderId", orderId);
        final String symbol = coinPair.toSymbol().replace("/", "");
        params.put("symbol", symbol);
        params.put("timestamp", System.currentTimeMillis());
        String signature = HmacSHA256Signer.sign(credentials.getSecretKey(), params);
        params.put("signature", signature);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-MBX-APIKEY", credentials.getApiKey());
        String requestParams = HmacSHA256Signer.sortAndJointParam(params);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestParams, headers);
        ResponseEntity<String> exchangeResp = restTemplate.exchange(exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + "/api/v3/order",
                HttpMethod.DELETE, httpEntity, String.class);

        if (HttpUtil.isSuccessResp(exchangeResp)) {
            JSONObject jsonResp = JSON.parseObject(exchangeResp.getBody());
            return "CANCELED".equalsIgnoreCase(jsonResp.getString("status"));
        } else {
            log.warn("binance撤销订单请求失败-apiKey:{},statusCode:{}", credentials.getApiKey(), exchangeResp.getStatusCode());
            throw new GatewayException("binance撤销订单请求失败,statusCode:" + exchangeResp.getStatusCode());
        }

    }

    @Override
    public TradesResult getTrades(Credentials credentials, OrderVo orderVo) {
        log.info("binance getTrades param,apiKey:{},orderVo:{}", credentials.getApiKey(), orderVo);

        exchangeApi.getRateLimiterManager().getRateLimiter(exchangeApi.getName() + "getTrades" + credentials.getApiKey(),
                4).acquire();

        final String symbol = orderVo.getMarket().getCoinPair().toSymbol().replace("/", "");
        long startTime = 0;
        if (orderVo.getCreatedAt() != null) {
            startTime = DateUtils.getEpochMilliByTime(orderVo.getCreatedAt());
        }

        ResponseEntity<String> exchangeResp = findTrades(credentials, symbol, startTime);

        if (HttpUtil.isSuccessResp(exchangeResp)) {
            JSONArray respOrders = JSON.parseArray(exchangeResp.getBody());
            TradesResult result = new TradesResult();
            List<TradeVo> tradeVoList = new ArrayList<>();
            assert respOrders != null;
            for (int i = 0; i < respOrders.size(); i++) {
                JSONObject respOrder = respOrders.getJSONObject(i);
                BinanceTradeVo vo = new BinanceTradeVo();
                vo.setExchangeName(exchangeApi.getName());
                vo.setExchangeOrderId(respOrder.getString("orderId"));
                vo.setExchangeTradeId(respOrder.getString("id"));
                vo.setPrice(respOrder.getBigDecimal("price"));
                vo.setFilledAmount(respOrder.getBigDecimal("qty"));
                vo.setCreatedAt(DateUtils.getEpochMilliByTime(respOrder.getLong("time")));
                // binance暂时不支持返佣数据获取
                vo.setFilledFee(respOrder.getBigDecimal("commission"));
                Boolean isBuyer = respOrder.getBoolean("isBuyer");
                vo.setRole(Boolean.TRUE.equals(isBuyer) ? RoleType.taker : RoleType.maker);

                tradeVoList.add(vo);
            }
            // 过滤非相关记录
            tradeVoList = tradeVoList.stream().
                    filter(s -> s.getExchangeOrderId().equals(orderVo.getExchangeOrderId()))
                    .collect(Collectors.toList());
            result.setTradeVos(tradeVoList);
            return result;
        } else {
            log.warn("binance获取订单成交记录请求失败-apiKey:{},statusCode:{}", credentials.getApiKey(), exchangeResp.getStatusCode());
            throw new GatewayException("binance获取订单成交记录请求失败,code:" + exchangeResp.getStatusCode());
        }
    }

    @Override
    public HistoryOrderResult getHistoryOrder(Credentials credentials, CursorVo cursorVo, Market market) {
        log.info("binance getHistoryOrder param,apiKey,{},market:{}", credentials.getApiKey(), market);

        exchangeApi.getRateLimiterManager().getRateLimiter(exchangeApi.getName() + "getHistoryOrder" + credentials.getApiKey(),
                4).acquire();

        Map<String, Object> params = new TreeMap<>();
        final String symbol = market.getCoinPair().toSymbol().replace("/", "");
        params.put("symbol", symbol);

        String recordId = "";
        if (cursorVo != null && cursorVo.getRecordId() != null) {
            params.put("orderId", cursorVo.getRecordId());
            recordId = cursorVo.getRecordId();
        }
        params.put("recvWindow", exchangeApi.recvWindow);
        params.put("timestamp", System.currentTimeMillis());
        String signature = HmacSHA256Signer.sign(credentials.getSecretKey(), params);
        params.put("signature", signature);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-MBX-APIKEY", credentials.getApiKey());
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        String requestParams = HmacSHA256Signer.sortAndJointParam(params);
        ResponseEntity<String> exchangeResp = exchangeApi.getRestTemplate()
                .exchange(exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + "/api/v3/allOrders?" + requestParams,
                        HttpMethod.GET, httpEntity, String.class);

        if (HttpUtil.isSuccessResp(exchangeResp)) {
            HistoryOrderResult result = new HistoryOrderResult();
            List<OrderVo> orderVoList = new ArrayList<>();
            JSONArray historyOrders = JSON.parseArray(exchangeResp.getBody());
            assert historyOrders != null;
            for (int i = 0; i < historyOrders.size(); i++) {
                // 排除游标数据
                JSONObject order = historyOrders.getJSONObject(i);
                if (recordId.equals(order.getString("orderId"))) {
                    continue;
                }
                BinanceOrder vo = new BinanceOrder();
                vo.setSymbol(market.getCoinPair().toSymbol());
                vo.setOrderId(order.getString("orderId"));
                vo.setMarket(market);

                vo.setFinishedVolume(new BigDecimal(order.getString("executedQty")));
                vo.setStopPrice(order.getString("stopPrice"));
                vo.setOrderSide(order.getString("side"));
                vo.setState(order.getString("status"));
                vo.setOrderType(order.getString("type"));
                vo.setOrderCreatedAt(order.getString("time"));
                vo.setOrderPrice(order.getString("price"));

                orderVoList.add(vo);
            }
            result.setList(orderVoList);
            // 设置游标
            Optional<Long> max = orderVoList.stream().map(o -> Long.parseLong(o.getExchangeOrderId())).max(Comparator.naturalOrder());
            if (max.isPresent()) {
                if (cursorVo == null) {
                    cursorVo = CursorVo.builder().build();
                }
                cursorVo.setRecordId(String.valueOf(max.get()));
                result.setCursorVo(cursorVo);
            }
            return result;
        } else {
            log.warn("binance获取所有订单记录请求失败-apiKey:{},statusCode:{}", credentials.getApiKey(), exchangeResp.getStatusCode());
            throw new GatewayException("binance获取所有订单记录请求失败,code:" + exchangeResp.getStatusCode());
        }
    }

    @Override
    public boolean checkOrderDefined(OrderRequest request) {
        return true;
    }

    @Override
    public void initOrderLimit(List<Market> markets) {
        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + "/api/v3/exchangeInfo";
        ResponseEntity<JSONObject> resp = exchangeApi.getRestTemplate().getForEntity(url, JSONObject.class);
        if (resp.getStatusCode().is2xxSuccessful()) {
            JSONArray symbols = Objects.requireNonNull(resp.getBody()).getJSONArray("symbols");
            CoinPairSymbolConverter symbolConverter = exchangeApi.getCoinPairSymbolConverter();
            OrderLimitManager manager = exchangeApi.getOrderLimitManager();
            if (CollectionUtils.isEmpty(markets)) {
                throw new IllegalArgumentException("markets 列表不能为空");
            }
            List<String> availableSymbols = markets.stream()
                    .map(m -> symbolConverter.coinPairToSymbol(m.getCoinPair()))
                    .collect(Collectors.toList());
            for (int i = 0; i < symbols.size(); i++) {
                JSONObject symbol = symbols.getJSONObject(i);
                String symbolName = symbol.getString("symbol");
                if (availableSymbols.contains(symbolName)) {
                    BinanceOrderLimit orderLimit = new BinanceOrderLimit();
                    orderLimit.setSymbol(symbolConverter.symbolToCoinPair(symbolName).toSymbol());
                    JSONArray filters = symbol.getJSONArray("filters");
                    for (int j = 0; j < filters.size(); j++) {
                        JSONObject filter = filters.getJSONObject(j);
                        if ("PRICE_FILTER".equals(filter.getString("filterType"))) {
                            orderLimit.setMaxOrderPrice(filter.getBigDecimal("maxPrice"));
                            orderLimit.setMinOrderPrice(filter.getBigDecimal("minPrice"));
                        }
                        if ("MIN_NOTIONAL".equals(filter.getString("filterType"))) {
                            orderLimit.setMinOrderVal(filter.getBigDecimal("minNotional"));
                        }
                        if ("LOT_SIZE".equals(filter.getString("filterType"))) {
                            orderLimit.setMinOrderAmount(filter.getBigDecimal("minQty"));
                            orderLimit.setMaxOrderQty(filter.getBigDecimal("maxQty"));
                        }
                        if ("MARKET_LOT_SIZE".equals(filter.getString("filterType"))) {
                            orderLimit.setMarketMinOrderQty(filter.getBigDecimal("minQty"));
                            orderLimit.setMarketMaxOrderQty(filter.getBigDecimal("maxQty"));
                        }
                        if ("MAX_NUM_ORDERS".equals(filter.getString("filterType"))) {
                            orderLimit.setMaxNumOrders(filter.getInteger("maxNumOrders"));
                        }
                    }
                    manager.addOrderLimit(exchangeApi.getName(), orderLimit);
                }
            }
        } else {
            throw new GatewayException("binance读取市场交易规则失败,statusCode:" + resp.getStatusCode());
        }
        log.info("binance initOrderLimit完成");
    }

    private ResponseEntity<String> findTrades(Credentials credentials, String symbol, long startTime) {
        Map<String, Object> params = new TreeMap<>();
        params.put("symbol", symbol);
        long timestamp = System.currentTimeMillis();
        params.put("recvWindow", exchangeApi.recvWindow);
        params.put("timestamp", timestamp);
        if (startTime > 0) {
            params.put("startTime", startTime);
        }
        String signature = HmacSHA256Signer.sign(credentials.getSecretKey(), params);
        params.put("signature", signature);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-MBX-APIKEY", credentials.getApiKey());
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        String requestParams = HmacSHA256Signer.sortAndJointParam(params);
        return exchangeApi.getRestTemplate()
                .exchange(exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + "/api/v3/myTrades?" + requestParams,
                        HttpMethod.GET, httpEntity, String.class);
    }

    private void rateLimit(String key) {
        RateLimiterManager limiterManager = exchangeApi.getRateLimiterManager();
        RateLimiter limiter = limiterManager.getRateLimiter(key, 10);

        limiter.acquire();
    }

    private void verifyOrderParams(@NonNull OrderRequest request, String innerSymbol, String orderType) {
        BinanceOrderLimit limit = (BinanceOrderLimit) exchangeApi.getOrderLimitManager().getOrderLimit(exchangeApi.getName() + innerSymbol);
        if (request.getAmount() == null) {
            throw new IllegalArgumentException("binance下单必须包含下单数量");
        }
        if (request.getAmount().compareTo(limit.getMinOrderAmount()) < 0 || request.getAmount().compareTo(limit.getMaxOrderQty()) > 0) {
            String s = String.format("违反binance下单交易数量规则,参数:%s要大于%s小于%s", request.getAmount(), limit.getMinOrderAmount(), limit.getMaxOrderQty());
            throw new IllegalArgumentException(s);
        }
        if (OrderType.STOP_LIMIT == request.getType()) {
            if (request.getStopPrice() == null) {
                throw new IllegalArgumentException("限价止盈/止损订单必须指定stopPrice");
            }
        }
        if (OrderType.MARKET == request.getType()) {
            if (request.getAmount().compareTo(limit.getMarketMinOrderQty()) < 0 || request.getAmount().compareTo(limit.getMarketMaxOrderQty()) > 0) {
                String s = String.format("违反binance市价下单数量规则,参数:%s要大于%s小于%s", request.getAmount(), limit.getMarketMinOrderQty(), limit.getMarketMaxOrderQty());
                throw new IllegalArgumentException(s);
            }
        }
        if (Arrays.asList("LIMIT", "STOP_LOSS_LIMIT", "TAKE_PROFIT_LIMIT").contains(orderType)) {
            if (request.getPrice() == null) {
                throw new IllegalArgumentException("限价和止盈/止损订单必须指定price");
            }
            if (request.getPrice().compareTo(limit.getMinOrderPrice()) < 0 || request.getPrice().compareTo(limit.getMaxOrderPrice()) > 0) {
                String s = String.format("违反binance下单交易价格规则,参数:%s要大于%s小于%s", request.getPrice(), limit.getMinOrderPrice(), limit.getMaxOrderPrice());
                throw new IllegalArgumentException(s);
            }
        }
        if (request.getPrice() != null) {
            BigDecimal orderValue = request.getAmount().multiply(request.getPrice());
            if (orderValue.compareTo(limit.getMinOrderVal()) < 0) {
                throw new IllegalArgumentException(String.format("违反binance最小下单金额规则,参数:%s要大于%s", orderValue, limit.getMinOrderVal()));
            }
        }
    }
}
