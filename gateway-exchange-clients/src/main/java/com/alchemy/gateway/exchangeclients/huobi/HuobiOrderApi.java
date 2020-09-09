package com.alchemy.gateway.exchangeclients.huobi;

import com.alchemy.gateway.core.AbstractExchangeApi;
import com.alchemy.gateway.core.GatewayException;
import com.alchemy.gateway.core.common.*;
import com.alchemy.gateway.core.order.*;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alchemy.gateway.core.wallet.CursorVo;
import com.alchemy.gateway.exchangeclients.common.HttpUtil;
import com.alchemy.gateway.exchangeclients.huobi.domain.HuobiOrderLimit;
import com.alchemy.gateway.exchangeclients.huobi.domain.HuobiOrderVo;
import com.alchemy.gateway.exchangeclients.huobi.domain.HuobiTradeVo;
import com.alchemy.gateway.exchangeclients.huobi.util.HuobiApiSignature;
import com.alchemy.gateway.exchangeclients.huobi.util.HuobiOrderTypeConverter;
import com.alchemy.gateway.exchangeclients.huobi.util.UrlParamsBuilder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author kevin chen
 */
@Slf4j
public class HuobiOrderApi implements OrderApi {
    private final AbstractExchangeApi exchangeApi;

    public HuobiOrderApi(AbstractExchangeApi exchangeApi) {
        this.exchangeApi = exchangeApi;
    }

    @Override
    @SneakyThrows
    public OrderVo placeOrder(Credentials credentials, OrderRequest request) {
        log.info("huobi placeOrder params-apiKey:{},param:{}", credentials.getApiKey(), request);
        exchangeApi.getRateLimiterManager()
                .getRateLimiter(exchangeApi.getName() + "placeOrder" + credentials.getApiKey(), 50)
                .acquire();

        // 下单交易规则校验
        verifyOrderParams(request);

        RestTemplate restTemplate = exchangeApi.getRestTemplate();
        String apiEndpoint = exchangeApi.getConnectionInfo().getRestfulApiEndpoint();

        // 获取账户id
        String accountId = getAccountId(credentials, restTemplate, apiEndpoint);

        CoinPairSymbolConverter symbolConverter = exchangeApi.getCoinPairSymbolConverter();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToPost("account-id", accountId)
                .putToPost("amount", request.getAmount().stripTrailingZeros().toPlainString())
                .putToPost("symbol", symbolConverter.coinPairToSymbol(request.getMarket().getCoinPair()))
                .putToPost("type", HuobiOrderTypeConverter.toOrderTypeStr(request.getType(), request.getOrderSide()))
                .putToPost("source", "spot-api");
        if (request.getPrice() != null) {
            builder.putToPost("price", request.getPrice().stripTrailingZeros().toPlainString());
        }
        if (OrderType.STOP_LIMIT == request.getType()) {
            builder.putToPost("stop-price", request.getStopPrice().stripTrailingZeros().toPlainString());
            builder.putToPost("operator", request.getOperatorType().name().toLowerCase());
        }

        String signedParams = HuobiApiSignature.createSignature(credentials.getApiKey(), credentials.getSecretKey(),
                HttpUtil.POST, new URL(apiEndpoint).getHost(), "/v1/order/orders/place", builder).buildUrl();
        URI uri = UriComponentsBuilder.fromHttpUrl(apiEndpoint + "/v1/order/orders/place" + signedParams).build(true).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("user-agent", HttpUtil.USER_AGENT);
        HttpEntity<String> httpEntity = new HttpEntity<>(builder.getPostParamsJson(), headers);

        ResponseEntity<String> exchangeResp = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        if (HttpUtil.isSuccessResp(exchangeResp)) {
            JSONObject respBody = JSON.parseObject(exchangeResp.getBody());

            assertOkResp(respBody, "huobi placeOrder error");

            HuobiOrderVo orderVo = new HuobiOrderVo();
            orderVo.setExchangeOrderId(respBody.getString("data"));
            return orderVo;
        } else {
            throw new GatewayException("huobi下单请求失败,statusCode:" + exchangeResp.getStatusCode());
        }
    }

    @SneakyThrows
    @Override
    public OrderVo getOrder(Credentials credentials, @NonNull String orderId, CoinPair coinPair, OrderType type) {
        log.info("huobi getOrder params-apiKey:{},orderId:{}", credentials.getApiKey(), orderId);
        exchangeApi.getRateLimiterManager()
                .getRateLimiter(exchangeApi.getName() + "getOrder" + credentials.getApiKey(), 25)
                .acquire();

        RestTemplate restTemplate = exchangeApi.getRestTemplate();
        String apiEndpoint = exchangeApi.getConnectionInfo().getRestfulApiEndpoint();

        final String address = String.format("/v1/order/orders/%s", orderId);
        String signedParams = HuobiApiSignature.createSignature(credentials.getApiKey(), credentials.getSecretKey(),
                HttpUtil.GET, new URL(apiEndpoint).getHost(), address, UrlParamsBuilder.build()).buildUrl();

        URI uri = UriComponentsBuilder.fromHttpUrl(apiEndpoint + address + signedParams).build(true).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", HttpUtil.USER_AGENT);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> exchangeResp = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        if (HttpUtil.isSuccessResp(exchangeResp)) {
            JSONObject respBody = JSON.parseObject(exchangeResp.getBody());

            assertOkResp(respBody, "huobi getOrder error");

            JSONObject data = respBody.getJSONObject("data");
            HuobiOrderVo vo = new HuobiOrderVo();
            if (data != null) {
                vo.setSymbol(exchangeApi.getCoinPairSymbolConverter().symbolToCoinPair(data.getString("symbol")).toSymbol());
                vo.setOrderAmount(data.getString("amount"));
                vo.setExchangeOrderId(orderId);
                vo.setOrderPrice(data.getString("price"));
                vo.setOrderCreatedAt(data.getString("created-at"));
                vo.setFinishedAmount(data.getBigDecimal("field-cash-amount"));
                vo.setFinishedFees(data.getBigDecimal("field-fees"));
                vo.setState(data.getString("state"));
                vo.setOrderSide(data.getString("type"));
                vo.setOrderType(data.getString("type"));
                vo.setStopPrice(data.getBigDecimal("stop-price"));
                vo.setExchangeName("huobi");
                Long finishAt = data.getLong("finished-at");
                if (finishAt != null) {
                    vo.setFinishedAt(DateUtils.getEpochMilliByTime(finishAt));
                }
                vo.setFinishedVolume(data.getBigDecimal("field-amount"));
            }

            return vo;
        } else {
            throw new GatewayException("huobi获取订单详情请求失败,statusCode:" + exchangeResp.getStatusCode());
        }
    }

    @SneakyThrows
    @Override
    public boolean cancelOrder(Credentials credentials, String orderId, CoinPair coinPair, OrderType type) {
        log.info("huobi cancelOrder params-apiKey:{},orderId:{}", credentials.getApiKey(), orderId);
        exchangeApi.getRateLimiterManager()
                .getRateLimiter(exchangeApi.getName() + "cancelOrder" + credentials.getApiKey(), 50)
                .acquire();

        String apiEndpoint = exchangeApi.getConnectionInfo().getRestfulApiEndpoint();

        final String address = String.format("/v1/order/orders/%s/submitcancel", orderId);
        String signedParams = HuobiApiSignature.createSignature(credentials.getApiKey(), credentials.getSecretKey(),
                HttpUtil.POST, new URL(apiEndpoint).getHost(), address, UrlParamsBuilder.build()).buildUrl();

        URI uri = UriComponentsBuilder.fromHttpUrl(apiEndpoint + address + signedParams).build(true).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("user-agent", HttpUtil.USER_AGENT);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> exchangeResp = exchangeApi.getRestTemplate().exchange(uri, HttpMethod.POST, httpEntity, String.class);
        if (HttpUtil.isSuccessResp(exchangeResp)) {
            JSONObject respBody = JSON.parseObject(exchangeResp.getBody());

            assertOkResp(respBody, "huobi cancelOrder error");

            return "ok".equals(respBody.getString("status"));
        } else {
            throw new GatewayException("huobi cancelOrder异常,statusCode:" + exchangeResp.getStatusCode());
        }
    }

    @Override
    @SneakyThrows
    public TradesResult getTrades(Credentials credentials, OrderVo orderVo) {
        log.info("huobi getTrades params-apiKey:{},orderVo:{}", credentials.getApiKey(), orderVo);

        exchangeApi.getRateLimiterManager()
                .getRateLimiter(exchangeApi.getName() + "getTrades" + credentials.getApiKey(), 25)
                .acquire();

        String apiEndpoint = exchangeApi.getConnectionInfo().getRestfulApiEndpoint();
        final String address = String.format("/v1/order/orders/%s/matchresults", orderVo.getExchangeOrderId());

        String signedParams = HuobiApiSignature.createSignature(credentials.getApiKey(), credentials.getSecretKey(),
                HttpUtil.GET, new URL(apiEndpoint).getHost(), address, UrlParamsBuilder.build()).buildUrl();

        URI uri = UriComponentsBuilder.fromHttpUrl(apiEndpoint + address + signedParams).build(true).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", HttpUtil.USER_AGENT);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> exchangeResp = exchangeApi.getRestTemplate().exchange(uri, HttpMethod.GET, httpEntity, String.class);
        if (HttpUtil.isSuccessResp(exchangeResp)) {
            JSONObject respBody = JSON.parseObject(exchangeResp.getBody());

            assertOkResp(respBody, "huobi getTrades error");

            TradesResult result = new TradesResult();
            JSONArray trades = respBody.getJSONArray("data");
            List<TradeVo> tradeVoList = new ArrayList<>();
            if (trades != null) {
                BigDecimal feeDeduct = BigDecimal.ZERO;
                for (int i = 0; i < trades.size(); i++) {
                    JSONObject trade = trades.getJSONObject(i);
                    HuobiTradeVo vo = new HuobiTradeVo();
                    vo.setCreatedAt(DateUtils.getEpochMilliByTime(trade.getLong("created-at")));
                    vo.setExchangeName(exchangeApi.getName());
                    vo.setExchangeOrderId(orderVo.getExchangeOrderId());
                    vo.setPrice(trade.getBigDecimal("price"));
                    BigDecimal filledFee = trade.getBigDecimal("filled-fees");
                    if (filledFee.compareTo(BigDecimal.ZERO) >= 0) {
                        vo.setFilledFee(filledFee);
                    } else {
                        feeDeduct = feeDeduct.add(filledFee);
                    }
                    vo.setFilledAmount(trade.getBigDecimal("filled-amount"));
                    vo.setFeeDeductAmount(trade.getBigDecimal("filled-points"));
                    vo.setFeeDeductCoin(trade.getString("fee-deduct-currency"));
                    String role = trade.getString("role");
                    vo.setRole("maker".equals(role) ? RoleType.maker : "taker".equals(role) ? RoleType.taker : null);

                    if (result.getRebateCoin() == null) {
                        result.setRebateCoin(trade.getString("fee-currency"));
                    }

                    tradeVoList.add(vo);
                }
                result.setRebate(feeDeduct);
            }
            result.setTradeVos(tradeVoList);

            return result;
        } else {
            throw new GatewayException("获取huobi订单成交记录请求失败,code:" + exchangeResp.getStatusCode());
        }
    }

    @Override
    @SneakyThrows
    public HistoryOrderResult getHistoryOrder(Credentials credentials, CursorVo cursorVo, Market market) {
        log.info("huobi getHistoryOrder params-apiKey:{},cursorVo:{},coinPair:{}", credentials.getApiKey(), cursorVo, market.getCoinPair());

        exchangeApi.getRateLimiterManager()
                .getRateLimiter(exchangeApi.getName() + "getHistoryOrder" + credentials.getApiKey(), 25)
                .acquire();

        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", exchangeApi.getCoinPairSymbolConverter().coinPairToSymbol(market.getCoinPair()))
                .putToUrl("states", "filled,canceled")
                .putToUrl("direct", "prev");
        if (cursorVo != null && cursorVo.getRecordId() != null) {
            builder.putToUrl("from", cursorVo.getRecordId());
        }

        String apiEndpoint = exchangeApi.getConnectionInfo().getRestfulApiEndpoint();
        String signedParams = HuobiApiSignature.createSignature(credentials.getApiKey(), credentials.getSecretKey(),
                HttpUtil.GET, new URL(apiEndpoint).getHost(), "/v1/order/orders", builder).buildUrl();

        URI uri = UriComponentsBuilder.fromHttpUrl(apiEndpoint + "/v1/order/orders" + signedParams).build(true).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", HttpUtil.USER_AGENT);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> exchangeResp = exchangeApi.getRestTemplate().exchange(uri, HttpMethod.GET, httpEntity, String.class);
        if (HttpUtil.isSuccessResp(exchangeResp)) {
            JSONObject respBody = JSON.parseObject(exchangeResp.getBody());

            assertOkResp(respBody, "huobi getHistoryOrder");

            HistoryOrderResult result = new HistoryOrderResult();
            List<OrderVo> orderVoList = new ArrayList<>();
            JSONArray orders = respBody.getJSONArray("data");
            if (orders != null) {
                for (int i = 0; i < orders.size(); i++) {
                    HuobiOrderVo vo = new HuobiOrderVo();
                    JSONObject order = orders.getJSONObject(i);
                    vo.setExchangeOrderId(order.getString("id"));
                    vo.setFinishedAmount(order.getBigDecimal("field-cash-amount"));
                    vo.setFinishedVolume(order.getBigDecimal("field-amount"));
                    vo.setFinishedAt(DateUtils.getEpochMilliByTime(order.getLong("finished-at")));
                    vo.setOrderPrice(order.getString("price"));
                    vo.setOrderAmount(order.getString("amount"));
                    vo.setState(order.getString("state"));
                    vo.setOrderType(order.getString("type"));
                    vo.setExchangeName(exchangeApi.getName());
                    vo.setSymbol(exchangeApi.getCoinPairSymbolConverter().symbolToCoinPair(order.getString("symbol")).toSymbol());

                    orderVoList.add(vo);
                }
                // 向前查询，赋值为上一次查询结果中得到的第一条id
                if (orders.size() > 0) {
                    // 排除游标数据
                    if (cursorVo != null && cursorVo.getRecordId() != null) {
                        if (cursorVo.getRecordId().equals(orders.getJSONObject(0).getString("id"))) {
                            return result;
                        }
                    }
                    if (cursorVo == null) {
                        cursorVo = CursorVo.builder().build();
                    }
                    cursorVo.setRecordId(orders.getJSONObject(0).getString("id"));
                    result.setCursorVo(cursorVo);
                }
            }
            result.setList(orderVoList);
            return result;
        } else {
            throw new GatewayException("获取huobi历史订单记录请求失败,code:" + exchangeResp.getStatusCode());
        }
    }

    @Override
    public boolean checkOrderDefined(OrderRequest request) {
        return true;
    }

    @Override
    public void initOrderLimit(List<Market> markets) {
        final String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + "/v1/common/symbols";
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", HttpUtil.USER_AGENT);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<JSONObject> resp = exchangeApi.getRestTemplate().exchange(url, HttpMethod.GET, httpEntity, JSONObject.class);

        if (resp.getStatusCode().is2xxSuccessful()) {
            OrderLimitManager limitManager = exchangeApi.getOrderLimitManager();
            CoinPairSymbolConverter symbolConverter = exchangeApi.getCoinPairSymbolConverter();
            if (CollectionUtils.isEmpty(markets)) {
                throw new IllegalArgumentException("huobi交易所规则初始化markets不能为空");
            }
            List<String> availableSymbols = markets.stream()
                    .map(m -> symbolConverter.coinPairToSymbol(m.getCoinPair()))
                    .collect(Collectors.toList());

            JSONArray data = Objects.requireNonNull(resp.getBody()).getJSONArray("data");
            for (int i = 0; i < data.size(); i++) {
                JSONObject symbol = data.getJSONObject(i);
                if (!availableSymbols.contains(symbol.getString("symbol"))) {
                    continue;
                }
                HuobiOrderLimit orderLimit = new HuobiOrderLimit();
                String innerSymbol = symbolConverter.symbolToCoinPair(symbol.getString("symbol")).toSymbol();
                orderLimit.setSymbol(innerSymbol);
                orderLimit.setMinOrderAmount(symbol.getBigDecimal("min-order-amt"));
                orderLimit.setMaxOrderQty(symbol.getBigDecimal("max-order-amt"));
                orderLimit.setMinOrderVal(symbol.getBigDecimal("min-order-value"));
                orderLimit.setMaxOrderVal(symbol.getBigDecimal("max-order-value"));
                orderLimit.setMaxLimitOrderQty(symbol.getBigDecimal("limit-order-max-order-amt"));
                orderLimit.setMinLimitOrderQty(symbol.getBigDecimal("limit-order-min-order-amt"));
                orderLimit.setMaxBuyMarketOrderVal(symbol.getBigDecimal("buy-market-max-order-value"));
                orderLimit.setMinSellMarketOrderQty(symbol.getBigDecimal("sell-market-min-order-amt"));
                orderLimit.setMaxSellMarketOrderQty(symbol.getBigDecimal("sell-market-max-order-amt"));
                orderLimit.setPricePrecision(symbol.getIntValue("price-precision"));
                orderLimit.setAmountPrecision(symbol.getIntValue("amount-precision"));

                limitManager.addOrderLimit(exchangeApi.getName(), orderLimit);
            }
        } else {
            throw new GatewayException("huobi读取市场交易规则失败,statusCode:" + resp.getStatusCode());
        }
        log.info("huobiApi initOrderLimit完成");
    }

    private String getAccountId(Credentials credentials, RestTemplate restTemplate, String apiEndpoint) throws MalformedURLException {
        log.info("huobi getAccountId,apiKey:{}", credentials.getApiKey());

        String accountSignParams = HuobiApiSignature.createSignature(credentials.getApiKey(), credentials.getSecretKey(),
                HttpUtil.GET, new URL(apiEndpoint).getHost(), "/v1/account/accounts", UrlParamsBuilder.build()).buildUrl();
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", HttpUtil.USER_AGENT);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        URI uri = UriComponentsBuilder.fromHttpUrl(apiEndpoint + "/v1/account/accounts" + accountSignParams).build(true).toUri();
        ResponseEntity<String> resp = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);

        if (HttpUtil.isSuccessResp(resp)) {
            JSONObject respBody = JSON.parseObject(resp.getBody());

            assertOkResp(respBody, "huobi getAccountId error");

            JSONArray data = respBody.getJSONArray("data");
            for (int i = 0; i < data.size(); i++) {
                if ("spot".equals(data.getJSONObject(i).getString("type"))) {
                    return data.getJSONObject(i).getString("id");
                }
            }
        } else {
            throw new GatewayException("huobi获取账户信息请求异常,statusCode:" + resp.getStatusCode());
        }
        return null;
    }

    private void assertOkResp(JSONObject respBody, String source) {
        if ("error".equals(respBody.getString("status"))) {
            // 如果相关记录不存在
            if ("base-argument-record-not-exist".equals(respBody.getString("err-code"))) {
                log.warn("{}记录不存在", source);
                return;
            }
            throw new GatewayException(source + "error,errCode:"
                    + respBody.getString("err-code") + ",errMsg:" + respBody.getString("err-msg"));
        }
    }

    private void verifyOrderParams(OrderRequest request) {
        String innerSymbol = request.getMarket().getCoinPair().toSymbol();
        HuobiOrderLimit limit = (HuobiOrderLimit) exchangeApi.getOrderLimitManager().getOrderLimit(exchangeApi.getName() + innerSymbol);
        if (request.getAmount() == null) {
            throw new IllegalArgumentException("下单数量不能为空");
        }
        if (request.getAmount().scale() > limit.getAmountPrecision()) {
            throw new IllegalArgumentException(String.format("%s下单数量小数位数不能超过%s位", innerSymbol, limit.getAmountPrecision()));
        }
        if (OrderType.LIMIT == request.getType() || OrderType.STOP_LIMIT == request.getType()) {
            if (request.getPrice() == null) {
                throw new IllegalArgumentException("限价订单price不能为空");
            }
            if (request.getPrice().scale() > limit.getPricePrecision()) {
                throw new IllegalArgumentException(String.format("%s限价订单价格小数位数不能超过%s位", innerSymbol, limit.getPricePrecision()));
            }
            if (request.getAmount().compareTo(limit.getMinLimitOrderQty()) < 0 || request.getAmount().compareTo(limit.getMaxLimitOrderQty()) > 0) {
                String s = String.format("违反huobi下单限价交易数量规则,参数:%s要不小于%s不大于%s", request.getAmount(), limit.getMinLimitOrderQty(), limit.getMaxLimitOrderQty());
                throw new IllegalArgumentException(s);
            }
        }
        if (OrderType.STOP_LIMIT == request.getType()) {
            if (request.getStopPrice() == null) {
                throw new IllegalArgumentException("止盈止损订单stopPrice不能为空");
            }
            if (request.getOperatorType() == null) {
                throw new IllegalArgumentException("止盈止损订单operatorType不能为空");
            }
        }
        if (OrderType.MARKET == request.getType()) {
            if (OrderSide.BUY == request.getOrderSide() && request.getAmount().compareTo(limit.getMaxBuyMarketOrderVal()) > 0) {
                throw new IllegalArgumentException(String.format("违反huobi市价买入交易规则,参数:%s不能超过%s", request.getAmount(), limit.getMaxBuyMarketOrderVal()));
            }
            if (OrderSide.SELL == request.getOrderSide()) {
                if (request.getAmount().compareTo(limit.getMinSellMarketOrderQty()) < 0 || request.getAmount().compareTo(limit.getMaxSellMarketOrderQty()) > 0) {
                    String s = String.format("违反huobi市价卖出交易规则,参数:%s要不小于%s不大于%s", request.getAmount(), limit.getMinSellMarketOrderQty(), limit.getMaxSellMarketOrderQty());
                    throw new IllegalArgumentException(s);
                }
            }
        }
    }
}
