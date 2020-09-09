package com.alchemy.gateway.exchangeclients.okex;

import com.alchemy.gateway.core.common.*;
import com.alchemy.gateway.core.order.*;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alchemy.gateway.core.wallet.CursorVo;
import com.alchemy.gateway.exchangeclients.okex.exception.APIException;
import com.alchemy.gateway.exchangeclients.okex.impl.*;
import com.alchemy.gateway.exchangeclients.okex.param.OrderAlgoParam;
import com.alchemy.gateway.exchangeclients.okex.param.PlaceOrderParam;
import com.alchemy.gateway.exchangeclients.okex.resultModel.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
public class OkexOrderApi implements OrderApi {

    private final OkexExchangeApi exchangeApi;

    public OkexOrderApi(OkexExchangeApi exchangeApi) {
        this.exchangeApi = exchangeApi;
    }

    @Override
    public OrderVo placeOrder(Credentials credentials, OrderRequest request) {
        OkexOrderVo orderVo = new OkexOrderVo();
        if (request.getType().equals(OrderType.STOP_LIMIT)) {//止盈止损
            orderVo = placeStopLimitOrder(credentials, request);
        } else {//限价、市价

            PlaceOrderParam orderParam = new PlaceOrderParam();
            orderParam.setInstrumentId(exchangeApi.getCoinPairSymbolConverter().coinPairToSymbol(request.getMarket().getCoinPair()));
            orderParam.setType(request.getType().getName());
            orderParam.setSide(request.getOrderSide().name().toLowerCase());
            if (request.getType().equals(OrderType.LIMIT)) {
                orderParam.setPrice(request.getPrice().stripTrailingZeros().toPlainString());
                orderParam.setSize(request.getAmount().stripTrailingZeros().toPlainString());
            }
            if (request.getType().equals(OrderType.MARKET)) {
                if (OrderSide.SELL.equals(request.getOrderSide())) {
                    orderParam.setSize(request.getAmount().stripTrailingZeros().toPlainString());
                } else if (OrderSide.BUY.equals(request.getOrderSide())) {
                    orderParam.setNotional(request.getAmount().stripTrailingZeros().toPlainString());
                }
            }

            RestTemplate restTemplate = exchangeApi.getRestTemplate();
            String requestPath = "/api/spot/v3/orders";

            final String key = exchangeApi.getName() + requestPath + credentials.getApiKey();
            rateLimit(key, 30.0);

            ObjectMapper objectMapper = new ObjectMapper();
            String body;
            try {
                body = objectMapper.writeValueAsString(orderParam);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("placeOrder的json解析错误");
            }

            HttpHeaders headers = OkexFeatures.getHeadersToApiKey(credentials, HttpMethod.POST.name(), requestPath, "", body);
            HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
            String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath;
            log.info("okex{}下单请求路径:{}", request.getType(), url);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            log.info("okex{}下单请求结果数据:{}", request.getType(), responseEntity.getBody());

            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                if (!Objects.requireNonNull(responseEntity.getBody()).isEmpty()) {
                    OrderResult orderResult = JSON.parseObject(responseEntity.getBody(), OrderResult.class);
                    if (!orderResult.getError_code().equals("0")) {
                        throw new APIException("error_code:" + orderResult.getError_code() + ",error_message:" + orderResult.getError_message());
                    } else {
                        orderVo.setExchangeOrderId(String.valueOf(orderResult.getOrder_id()));
                        orderVo.setMarket(request.getMarket());
                        orderVo.setExchangeName(exchangeApi.getName());
                    }
                }
            } else {
                throw new IllegalStateException(HttpMethod.POST + url + "请求失败,statusCode:" + responseEntity.getStatusCode());
            }
        }
        return orderVo;
    }

    private OkexOrderVo placeStopLimitOrder(Credentials credentials, OrderRequest request) {

        OrderAlgoParam orderParam = new OrderAlgoParam();
        orderParam.setInstrumentId(exchangeApi.getCoinPairSymbolConverter().coinPairToSymbol(request.getMarket().getCoinPair()));
        orderParam.setMode("1");
        orderParam.setOrderType("1");
        orderParam.setSide(request.getOrderSide().name().toLowerCase());
        orderParam.setSize(request.getAmount().stripTrailingZeros().toPlainString());
        orderParam.setAlgoPrice(request.getPrice().stripTrailingZeros().toPlainString());
        orderParam.setAlgoType(request.getType());
        orderParam.setTriggerPrice(request.getStopPrice().stripTrailingZeros().toPlainString());

        RestTemplate restTemplate = exchangeApi.getRestTemplate();
        String requestPath = "/api/spot/v3/order_algo";

        final String key = exchangeApi.getName() + requestPath + credentials.getApiKey();
        rateLimit(key, 10.0);

        ObjectMapper objectMapper = new ObjectMapper();
        String body;
        try {
            body = objectMapper.writeValueAsString(orderParam);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("placeStopLimitOrder的json解析错误");
        }

        HttpHeaders headers = OkexFeatures.getHeadersToApiKey(credentials, HttpMethod.POST.name(), requestPath, "", body);
        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath;
        log.info("okex{}下单请求路径:{}", request.getType(), url);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        log.info("okex{}下单请求结果:{}", request.getType(), responseEntity.getBody());
        OkexOrderVo orderVo = new OkexOrderVo();
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            if (!Objects.requireNonNull(responseEntity.getBody()).isEmpty()) {
                OrderAlgoResult orderResult = JSON.parseObject(responseEntity.getBody(), OrderAlgoResult.class);
                if (!orderResult.getError_code().equals("0")) {
                    throw new APIException("error_code:" + orderResult.getError_code() + ",error_message:" + orderResult.getError_message());
                } else {
                    orderVo.setExchangeOrderId(String.valueOf(orderResult.getAlgo_id()));
                    orderVo.setMarket(request.getMarket());
                    orderVo.setExchangeName(exchangeApi.getName());
                }
            }
        } else {
            throw new IllegalStateException(HttpMethod.POST + url + "请求失败,statusCode:" + responseEntity.getStatusCode());
        }
        return orderVo;
    }

    @Override
    public OrderVo getOrder(Credentials credentials, String orderId, CoinPair coinPair, OrderType type) {
        RestTemplate restTemplate = exchangeApi.getRestTemplate();

        if (type.equals(OrderType.STOP_LIMIT)) {
            return getStopLimitOrder(restTemplate, credentials, orderId, coinPair);
        } else {
            return getOkexOrder(credentials, orderId, coinPair, restTemplate);
        }
    }

    private OkexOrderVo getOkexOrder(Credentials credentials, String orderId, CoinPair coinPair, RestTemplate restTemplate) {
        final String key = exchangeApi.getName() + "/api/spot/v3/orders/" + credentials.getApiKey();
        rateLimit(key, 5.0);

        String requestPath = "/api/spot/v3/orders/" + orderId;
        String queryString = "instrument_id=" + exchangeApi.getCoinPairSymbolConverter().coinPairToSymbol(coinPair);
        HttpHeaders headers = OkexFeatures.getHeadersToApiKey(credentials, HttpMethod.GET.name(), requestPath, queryString, "");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath + "?" + queryString;
        log.info("okex订单号:{}的订单详情的请求路径:{}", orderId, url);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        log.info("okex订单号:{}的订单详情的请求结果:{}", orderId, responseEntity.getBody());
        OkexOrderVo orderVo = new OkexOrderVo();
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            if (!Objects.requireNonNull(responseEntity.getBody()).isEmpty()) {
                OrderInfo orderInfo = JSON.parseObject(responseEntity.getBody(), OrderInfo.class);
                orderVo.setAmount(new BigDecimal(StringUtils.isEmpty(orderInfo.getSize()) ? "0" : orderInfo.getSize()));
                orderVo.setCreatedAt(DateUtils.getTimeByUtc(orderInfo.getTimestamp()));
                orderVo.setExchangeOrderId(String.valueOf(orderInfo.getOrder_id()));
                orderVo.setMarket(Market.spotMarket(exchangeApi.getCoinPairSymbolConverter().symbolToCoinPair(orderInfo.getInstrument_id())));
                orderVo.setExchangeName(exchangeApi.getName());
                orderVo.setFinishedAmount(new BigDecimal(StringUtils.isEmpty(orderInfo.getFilled_notional()) ? "0" : orderInfo.getFilled_notional()));
                orderVo.setFinishedFees(new BigDecimal(StringUtils.isEmpty(orderInfo.getFee()) ? "0" : orderInfo.getFee()).abs());
                orderVo.setFinishedVolume(new BigDecimal(StringUtils.isEmpty(orderInfo.getFilled_size()) ? "0" : orderInfo.getFilled_size()));
                orderVo.setSide(orderInfo.getSide());
                orderVo.setType(orderInfo.getType());
                orderVo.setState(orderInfo.getState());
                orderVo.setRebate(new BigDecimal(StringUtils.isEmpty(orderInfo.getRebate()) ? "0" : orderInfo.getRebate()));
                orderVo.setRebateCoin(orderInfo.getRebate_currency());
                if (orderInfo.getType().equals(OrderType.LIMIT.getName())) {
                    orderVo.setPrice(new BigDecimal(StringUtils.isEmpty(orderInfo.getPrice()) ? "0" : orderInfo.getPrice()));
                }
                if (orderInfo.getType().equals(OrderType.MARKET.getName())) {
                    orderVo.setPrice(new BigDecimal(StringUtils.isEmpty(orderInfo.getNotional()) ? "0" : orderInfo.getNotional()));
                }
            }
        } else {
            throw new IllegalStateException(HttpMethod.GET + url + "请求失败,statusCode:" + responseEntity.getStatusCode());
        }
        return orderVo;
    }

    private OrderVo getStopLimitOrder(RestTemplate restTemplate, Credentials credentials, String orderId, CoinPair coinPair) {

        String requestPath = "/api/spot/v3/algo";
        final String mapKey = exchangeApi.getName() + requestPath + credentials.getApiKey();
        rateLimit(mapKey, 5.0);

        String queryString = "instrument_id=" + exchangeApi.getCoinPairSymbolConverter().coinPairToSymbol(coinPair)
                + "&order_type=1&algo_id=" + orderId;

        HttpHeaders headers = OkexFeatures.getHeadersToApiKey(credentials, HttpMethod.GET.name(), requestPath, queryString, "");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath + "?" + queryString;

        log.info("okex订单号:{}的订单详情的请求路径:{}", orderId, url);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        log.info("okex订单号:{}的订单详情的请求结果:{}", orderId, responseEntity.getBody());
        OkexAlgoOrderVo orderVo = new OkexAlgoOrderVo();
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            if (!Objects.requireNonNull(responseEntity.getBody()).isEmpty()) {
                String key = coinPair.getBuyCoin().toLowerCase() + "_" + coinPair.getSellCoin().toLowerCase();
                JSONObject rst = JSONObject.parseObject(responseEntity.getBody());
                JSONArray dataArr = JSONArray.parseArray(String.valueOf(rst.get(key)));
                OrderAlgoInfo orderInfo = JSONObject.parseObject(String.valueOf(dataArr.get(0)), OrderAlgoInfo.class);
                if (!orderInfo.getOrder_id().isEmpty()) {
                    return getOkexOrder(credentials, orderInfo.getOrder_id(), coinPair, restTemplate);
                } else {
                    orderVo.setAmount(new BigDecimal(StringUtils.isEmpty(orderInfo.getSize()) ? "0" : orderInfo.getSize()));
                    orderVo.setCreatedAt(DateUtils.getTimeByUtc(orderInfo.getTimestamp()));
                    orderVo.setExchangeOrderId(String.valueOf(orderInfo.getOrder_id()));
                    orderVo.setMarket(Market.spotMarket(exchangeApi.getCoinPairSymbolConverter().symbolToCoinPair(orderInfo.getInstrument_id())));
                    orderVo.setExchangeName(exchangeApi.getName());
                    orderVo.setSide(orderInfo.getSide());
                    orderVo.setType(orderInfo.getOrder_type());
                    orderVo.setState(orderInfo.getStatus());
                    orderVo.setPrice(new BigDecimal(StringUtils.isEmpty(orderInfo.getAlgo_price()) ? "0" : orderInfo.getAlgo_price()));
                    orderVo.setStopPrice(new BigDecimal(StringUtils.isEmpty(orderInfo.getTrigger_price()) ? "0" : orderInfo.getTrigger_price()));
                }
            }
        } else {
            throw new IllegalStateException(HttpMethod.GET + url + "请求失败,statusCode:" + responseEntity.getStatusCode());
        }
        return orderVo;
    }

    @Override
    public boolean cancelOrder(Credentials credentials, String orderId, CoinPair coinPair, OrderType type) {
        RestTemplate restTemplate = exchangeApi.getRestTemplate();
        if (type.equals(OrderType.STOP_LIMIT)) {
            return cancelBatchAlgoOrder(restTemplate, credentials, orderId, coinPair);
        } else {
            final String key = exchangeApi.getName() + "/api/spot/v3/cancel_orders/" + credentials.getApiKey();
            rateLimit(key, 30.0);

            String requestPath = "/api/spot/v3/cancel_orders/" + orderId;
            Map<String, String> bodyMap = new HashMap<>();
            bodyMap.put("instrument_id", exchangeApi.getCoinPairSymbolConverter().coinPairToSymbol(coinPair));
            String body = JSONObject.toJSONString(bodyMap);

            HttpHeaders headers = OkexFeatures.getHeadersToApiKey(credentials, HttpMethod.POST.name(), requestPath, "", body);
            HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
            String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath;
            log.info("okex订单号:{},取消订单请求路径:{}", orderId, url);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            log.info("okex订单号:{},取消订单请求结果:{}", orderId, responseEntity.getBody());
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                if (!Objects.requireNonNull(responseEntity.getBody()).isEmpty()) {
                    OrderResult orderResult = JSON.parseObject(responseEntity.getBody(), OrderResult.class);
                    if (!orderResult.isResult()) {
                        throw new APIException("error_code:" + orderResult.getError_code() + ",error_message:" + orderResult.getError_message());
                    } else {
                        return true;
                    }
                }
                return false;
            } else {
                throw new IllegalStateException(HttpMethod.POST + url + "请求失败,statusCode:" + responseEntity.getStatusCode());
            }
        }
    }

    private boolean cancelBatchAlgoOrder(RestTemplate restTemplate, Credentials credentials, String orderId, CoinPair coinPair) {

        String requestPath = "/api/spot/v3/cancel_batch_algos";
        final String key = exchangeApi.getName() + requestPath + credentials.getApiKey();
        rateLimit(key, 5.0);

        OrderAlgoParam orderAlgoParam = new OrderAlgoParam();
        orderAlgoParam.setOrderType("1");
        orderAlgoParam.setInstrumentId(exchangeApi.getCoinPairSymbolConverter().coinPairToSymbol(coinPair));
        orderAlgoParam.setAlgoIds(new String[]{orderId});

        ObjectMapper objectMapper = new ObjectMapper();
        String body;
        try {
            body = objectMapper.writeValueAsString(orderAlgoParam);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("撤销委托单的json错误");
        }

        HttpHeaders headers = OkexFeatures.getHeadersToApiKey(credentials, HttpMethod.POST.name(), requestPath, "", body);
        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath;
        log.info("okex订单号:{},取消订单请求路径:{}", orderId, url);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        log.info("okex订单号:{},取消订单请求结果:{}", orderId, responseEntity.getBody());
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            if (!Objects.requireNonNull(responseEntity.getBody()).isEmpty()) {
                JSONObject rst = JSONObject.parseObject(responseEntity.getBody());
                boolean result = Boolean.parseBoolean(String.valueOf(rst.get("result")));
                if (!result) {
                    throw new APIException("error_code:" + rst.get("error_code") + ",error_message:" + rst.get("error_message"));
                } else {
                    return true;
                }
            }
            return false;
        } else {
            throw new IllegalStateException(HttpMethod.POST + url + "请求失败,statusCode:" + responseEntity.getStatusCode());
        }
    }

    @Override
    public TradesResult getTrades(Credentials credentials, OrderVo orderVo) {
        RestTemplate restTemplate = exchangeApi.getRestTemplate();

        //判断是否是委托单,如果是先拿到订单Id获取成交明细
        if (orderVo.getType().equals(OrderType.STOP_LIMIT)) {
            OrderVo okexOrderVo = getOrder(credentials, orderVo.getExchangeOrderId(), orderVo.getMarket().getCoinPair(), orderVo.getType());
            if (okexOrderVo != null && !okexOrderVo.getExchangeOrderId().isEmpty()) {
                orderVo = okexOrderVo;
            }
        }

        List<TradeVo> result = new ArrayList<>();//成交明细集合
        int count = 0;//计数器(当获取不到成交明细时)
        Long afterId = null;//查询起始点
        boolean flag = true;
        while (flag && count < 10) {
            List<Fills> fillsList = okexTradeList(restTemplate, credentials, orderVo, afterId);//正常情况下
            if (fillsList != null && fillsList.size() > 0) {
                result.addAll(tradesInfo(orderVo, fillsList));
                afterId = fillsList.get(fillsList.size() - 1).getLedger_id();
                while (true) {
                    fillsList = okexTradeList(restTemplate, credentials, orderVo, afterId);
                    if (fillsList.size() == 0) {
                        break;
                    }
                    result.addAll(tradesInfo(orderVo, fillsList));
                    afterId = fillsList.get(fillsList.size() - 1).getLedger_id();
                }
                flag = false;
            } else {
                try {
                    Thread.sleep(500);//睡500毫秒再次请求接口
                    count++;
                } catch (InterruptedException e) {
                    log.error("okex订单:{},获取成交明细错误信息:{}", orderVo.getExchangeOrderId(), e.getMessage());
                }
            }
        }
        if (count >= 10) {
            log.error("okex订单:{}多次获取成交明细无数据,请核查", orderVo.getExchangeOrderId());
        }

        //获取费率及费率币种
        OrderVo order = getOrder(credentials, orderVo.getExchangeOrderId(), orderVo.getMarket().getCoinPair(), orderVo.getType());

        TradesResult tradesResult = new TradesResult();
        tradesResult.setTradeVos(result);
        tradesResult.setRebate(new BigDecimal(String.valueOf(order.getRebate())));
        tradesResult.setRebateCoin(order.getRebateCoin());
        log.info("okex订单{}的成交明细:{}", order.getExchangeOrderId(), tradesResult);
        return tradesResult;
    }

    /**
     * 处理成交明细结果
     *
     * @param orderVo   订单信息
     * @param fillsList 成交明细
     * @return 内部成交明细信息
     */
    private List<TradeVo> tradesInfo(OrderVo orderVo, List<Fills> fillsList) {
        List<TradeVo> tradeVos = new ArrayList<>();
        Map<String, OkexTradeVo> tradeMap = new HashMap<>();
        if (fillsList != null && fillsList.size() > 0) {
            for (Fills fills : fillsList) {
                OkexTradeVo tradeVo = tradeMap.get(fills.getTrade_id());
                if (tradeVo == null) {
                    tradeVo = new OkexTradeVo();
                }
                if (orderVo.getSide().name().toLowerCase().equals(fills.getSide())) {
                    tradeVo.setCreatedAt(DateUtils.getTimeByUtc(fills.getTimestamp()));
                    tradeVo.setExchangeName(exchangeApi.getName());
                    tradeVo.setExchangeOrderId(String.valueOf(fills.getOrder_id()));
                    tradeVo.setExchangeTradeId(fills.getTrade_id());
                    tradeVo.setFilledAmount(new BigDecimal(StringUtils.isEmpty(fills.getSize()) ? "0" : fills.getSize()));
                    tradeVo.setPrice(new BigDecimal(StringUtils.isEmpty(fills.getPrice()) ? "0" : fills.getPrice()));
                    tradeVo.setRole(fills.getExec_type());

                }
                if (fills.getSide().equalsIgnoreCase(OrderSide.BUY.name())) {
                    tradeVo.setFeeDeductCoin(fills.getCurrency());
                    if (fills.getFee() != null && !fills.getFee().isEmpty()) {
                        BigDecimal fee = new BigDecimal(fills.getFee());
                        if (fee.compareTo(new BigDecimal("0")) < 0) {//如果是负数就去绝对值作为手续费，反之则为零（返佣从详情中返回）
                            tradeVo.setFilledFee(fee.abs());
                        } else {
                            tradeVo.setFilledFee(new BigDecimal("0"));
                        }
                    }

                }
                tradeMap.put(fills.getTrade_id(), tradeVo);
            }
            tradeVos.addAll(tradeMap.values());
        }
        return tradeVos;
    }

    /**
     * 请求okex成交明细接口
     *
     * @param credentials 账户信息
     * @param orderVo     订单信息
     * @param afterId     请求此id之前（更旧的数据）的分页内容，传的值为对应接口的ledger_id
     * @return 成交明细信息
     */
    private List<Fills> okexTradeList(RestTemplate restTemplate, Credentials credentials, OrderVo orderVo, Long afterId) {

        //获取成交明细
        String requestPath = "/api/spot/v3/fills";

        StringBuilder queryString = new StringBuilder().append("order_id=").append(orderVo.getExchangeOrderId())
                .append("&instrument_id=").append(exchangeApi.getCoinPairSymbolConverter().coinPairToSymbol(orderVo.getMarket().getCoinPair()));
        if (!StringUtils.isEmpty(afterId)) {
            queryString.append("&after=").append(afterId);
        }

        HttpHeaders headers = OkexFeatures.getHeadersToApiKey(credentials, HttpMethod.GET.name(), requestPath, queryString.toString(), "");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath + "?" + queryString;

        log.info("okex成交记录明细请求头信息:{}", url);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        log.info("okex成交记录明细请求结果数据:{}", responseEntity.getBody());
        List<Fills> fillsList = new ArrayList<>();
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            if (!Objects.requireNonNull(responseEntity.getBody()).isEmpty()) {
                fillsList = JSONArray.parseArray(responseEntity.getBody(), Fills.class);
            }
        } else {
            throw new IllegalStateException(HttpMethod.GET + url + "请求失败,statusCode:" + responseEntity.getStatusCode());
        }
        return fillsList;
    }

    @Override
    public HistoryOrderResult getHistoryOrder(Credentials credentials, CursorVo cursorVo, Market market) {
        RestTemplate restTemplate = exchangeApi.getRestTemplate();
        HistoryOrderResult filledAlgoOrderResult = getAlgoHistoryOrder(restTemplate, credentials, cursorVo, market, 2);//已生效
        HistoryOrderResult cancelledAlgoOrderResult = getAlgoHistoryOrder(restTemplate, credentials, cursorVo, market, 3);//已撤销
        HistoryOrderResult entrustOrderResult = getEntrustHistoryOrder(restTemplate, credentials, cursorVo, market);

        List<OrderVo> items = new ArrayList<>();
        items.addAll(filledAlgoOrderResult.getList());
        items.addAll(cancelledAlgoOrderResult.getList());
        items.addAll(entrustOrderResult.getList());

        HistoryOrderResult historyOrderResult = new HistoryOrderResult();
        historyOrderResult.setList(items);

        long filledAlgo = Long.parseLong(filledAlgoOrderResult.getCursorVo().getRecordId());
        long cancelledAlgo = Long.parseLong(cancelledAlgoOrderResult.getCursorVo().getRecordId());
        long entrustOrder = Long.parseLong(entrustOrderResult.getCursorVo().getRecordId());

        if (filledAlgo == 0) {
            filledAlgo = Long.MAX_VALUE;
        }
        if (cancelledAlgo == 0) {
            cancelledAlgo = Long.MAX_VALUE;
        }

        historyOrderResult.setCursorVo(CursorVo.builder().recordId(Math.min(filledAlgo, cancelledAlgo) + "," + entrustOrder).build());

        return historyOrderResult;
    }

    /**
     * 获取订单列表
     * 0：普通委托(现阶段只有)
     * 1：只做Maker（Post only）
     * 2：全部成交或立即取消（FOK）
     * 3：立即成交并取消剩余（IOC）
     *
     * @param credentials 用户信息
     * @param cursorVo    游标信息
     * @param market      市场信息
     * @return HistoryOrderResult
     */
    private HistoryOrderResult getEntrustHistoryOrder(RestTemplate restTemplate, Credentials credentials, CursorVo cursorVo, Market market) {

        List<OrderVo> items = new ArrayList<>();
        String recordId = "0";

        String requestPath = "/api/spot/v3/orders";
        final String key = exchangeApi.getName() + requestPath + credentials.getApiKey();
        rateLimit(key, 2.0);

        StringBuilder queryString = new StringBuilder().append("instrument_id=")
                .append(exchangeApi.getCoinPairSymbolConverter().coinPairToSymbol(market.getCoinPair()))
                .append("&state=7");
        if (cursorVo != null && !StringUtils.isEmpty(cursorVo.getRecordId())) {
            String[] before = cursorVo.getRecordId().split(",");
            if (before.length >= 2) {
                long beforeId = Long.parseLong(before[1]);
                if (beforeId != 0 && beforeId != Long.MAX_VALUE) {
                    queryString.append("&before=").append(beforeId);
                }
            }
        }
        HttpHeaders headers = OkexFeatures.getHeadersToApiKey(credentials, HttpMethod.GET.name(), requestPath, queryString.toString(), "");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath + "?" + queryString;

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        log.info("获取订单列表返回数据:{}", responseEntity.getBody());


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            if (!Objects.requireNonNull(responseEntity.getBody()).isEmpty()) {
                List<OrderInfo> orderInfoList = JSONArray.parseArray(responseEntity.getBody(), OrderInfo.class);
                if (orderInfoList != null && orderInfoList.size() > 0) {
                    orderInfoList.forEach(orderInfo -> {
                        OkexOrderVo orderVo = new OkexOrderVo();
                        orderVo.setAmount(new BigDecimal(StringUtils.isEmpty(orderInfo.getSize()) ? "0" : orderInfo.getSize()));
                        orderVo.setCreatedAt(DateUtils.getTimeByUtc(orderInfo.getTimestamp()));
                        orderVo.setExchangeOrderId(String.valueOf(orderInfo.getOrder_id()));
                        orderVo.setMarket(Market.spotMarket(exchangeApi.getCoinPairSymbolConverter().symbolToCoinPair(orderInfo.getInstrument_id())));
                        orderVo.setExchangeName(exchangeApi.getName());
                        orderVo.setFinishedAmount(new BigDecimal(StringUtils.isEmpty(orderInfo.getFilled_notional()) ? "0" : orderInfo.getFilled_notional()));
                        orderVo.setFinishedFees(new BigDecimal(StringUtils.isEmpty(orderInfo.getFee()) ? "0" : orderInfo.getFee()));
                        orderVo.setFinishedVolume(new BigDecimal(StringUtils.isEmpty(orderInfo.getFilled_size()) ? "0" : orderInfo.getFilled_size()));
                        orderVo.setSide(orderInfo.getSide());
                        orderVo.setType(orderInfo.getType());
                        orderVo.setState(orderInfo.getState());
                        if (orderInfo.getType().equals(OrderType.LIMIT.getName())) {
                            orderVo.setPrice(new BigDecimal(StringUtils.isEmpty(orderInfo.getPrice()) ? "0" : orderInfo.getPrice()));
                        }
                        if (orderInfo.getType().equals(OrderType.MARKET.getName())) {
                            orderVo.setPrice(new BigDecimal(StringUtils.isEmpty(orderInfo.getNotional()) ? "0" : orderInfo.getNotional()));
                        }
                        items.add(orderVo);
                    });
                    recordId = String.valueOf(orderInfoList.get(0).getOrder_id());
                }
            }
        } else {
            throw new IllegalStateException(HttpMethod.GET + url + "请求失败,statusCode:" + responseEntity.getStatusCode());
        }

        HistoryOrderResult historyOrderResult = new HistoryOrderResult();
        historyOrderResult.setList(items);
        historyOrderResult.setCursorVo(CursorVo.builder().recordId(recordId).build());
        return historyOrderResult;
    }

    /**
     * 获取委托单列表
     * 1：止盈止损(现阶段只有)
     * 2：跟踪委托
     * 3：冰山委托
     * 4：时间加权
     *
     * @param credentials 用户信息
     * @param cursorVo    游标信息
     * @param market      市场信息
     * @return HistoryOrderResult
     */
    private HistoryOrderResult getAlgoHistoryOrder(RestTemplate restTemplate, Credentials credentials, CursorVo cursorVo, Market market, int state) {

        List<OrderVo> items = new ArrayList<>();
        String recordId = "0";

        String requestPath = "/api/spot/v3/algo";
        final String key = exchangeApi.getName() + requestPath + credentials.getApiKey();
        rateLimit(key, 5.0);

        StringBuilder queryString = new StringBuilder().append("instrument_id=")
                .append(exchangeApi.getCoinPairSymbolConverter().coinPairToSymbol(market.getCoinPair()))
                .append("&order_type=1")
                .append("&state=").append(state);
        if (cursorVo != null && !StringUtils.isEmpty(cursorVo.getRecordId())) {
            String[] before = cursorVo.getRecordId().split(",");
            if (before.length >= 2) {
                long beforeId = Long.parseLong(before[0]);
                if (beforeId != 0 && beforeId != Long.MAX_VALUE) {
                    queryString.append("&before=").append(beforeId);
                }
            }
        }
        HttpHeaders headers = OkexFeatures.getHeadersToApiKey(credentials, HttpMethod.GET.name(), requestPath, queryString.toString(), "");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath + "?" + queryString;

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        log.info("获取委托单列表返回数据:{}", responseEntity.getBody());

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            if (!Objects.requireNonNull(responseEntity.getBody()).isEmpty()) {
                String coinPair = market.getCoinPair().getBuyCoin().toLowerCase() + "_" + market.getCoinPair().getSellCoin().toLowerCase();
                JSONObject rst = JSONObject.parseObject(responseEntity.getBody());
                JSONArray dataArr = JSONArray.parseArray(String.valueOf(rst.get(coinPair)));
                List<OrderAlgoInfo> orderInfoList = JSONArray.parseArray(String.valueOf(dataArr), OrderAlgoInfo.class);
                if (orderInfoList != null && orderInfoList.size() > 0) {
                    orderInfoList.forEach(orderInfo -> {
                        OkexAlgoOrderVo orderVo = new OkexAlgoOrderVo();
                        orderVo.setAmount(new BigDecimal(StringUtils.isEmpty(orderInfo.getSize()) ? "0" : orderInfo.getSize()));
                        orderVo.setCreatedAt(DateUtils.getTimeByUtc(orderInfo.getTimestamp()));
                        orderVo.setExchangeOrderId(String.valueOf(orderInfo.getOrder_id()));
                        orderVo.setMarket(Market.spotMarket(exchangeApi.getCoinPairSymbolConverter().symbolToCoinPair(orderInfo.getInstrument_id())));
                        orderVo.setExchangeName(exchangeApi.getName());
                        orderVo.setSide(orderInfo.getSide());
                        orderVo.setType(orderInfo.getOrder_type());
                        orderVo.setState(orderInfo.getStatus());
                        orderVo.setPrice(new BigDecimal(StringUtils.isEmpty(orderInfo.getAlgo_price()) ? "0" : orderInfo.getAlgo_price()));
                        orderVo.setStopPrice(new BigDecimal(StringUtils.isEmpty(orderInfo.getTrigger_price()) ? "0" : orderInfo.getTrigger_price()));
                        items.add(orderVo);
                    });
                    recordId = String.valueOf(orderInfoList.get(0).getOrder_id());
                }
            }
        } else {
            throw new IllegalStateException(HttpMethod.GET + url + "请求失败,statusCode:" + responseEntity.getStatusCode());
        }

        HistoryOrderResult historyOrderResult = new HistoryOrderResult();
        historyOrderResult.setList(items);
        historyOrderResult.setCursorVo(CursorVo.builder().recordId(recordId).build());
        return historyOrderResult;
    }

    private void rateLimit(String key, double permitsPerSecond) {
        RateLimiterManager limiterManager = exchangeApi.getRateLimiterManager();
        RateLimiter limiter = limiterManager.getRateLimiter(key, permitsPerSecond);
        limiter.acquire();
    }

    @Override
    public boolean checkOrderDefined(OrderRequest request) {
        OkexOrderLimit okexOrderLimit = (OkexOrderLimit) exchangeApi.getOrderLimitManager()
                .getOrderLimit(exchangeApi.getName() + request.getMarket().getCoinPair().toSymbol());

        //限价市价时
        if (request.getAmount() != null) {
            if (okexOrderLimit.getMinOrderAmount().compareTo(request.getAmount()) <= 0) {
                if (okexOrderLimit.getSizeIncrement().scale() < request.getAmount().stripTrailingZeros().scale()) {
                    throw new IllegalArgumentException("amount不符合OKEX的交易货币数量精度" + okexOrderLimit.getSizeIncrement().scale() + ",请注意");
                }
            } else {
                throw new IllegalArgumentException("amount小于OKEX的最小交易数量" + okexOrderLimit.getMinOrderAmount() + ",请注意");
            }
        } else {
            throw new IllegalArgumentException("amount为空,请注意");
        }

        //限价
        if (OrderType.LIMIT.equals(request.getType())) {
            if (request.getPrice() != null) {
                if (okexOrderLimit.getTickSize().scale() < request.getPrice().stripTrailingZeros().scale()) {
                    throw new IllegalArgumentException("price不符合okex的交易价格精度" + okexOrderLimit.getTickSize().scale() + ",请注意");
                }
            } else {
                throw new IllegalArgumentException("下单类型为" + request.getType().getName() + ",price为空,请注意");
            }
        }

        //止盈止损时
        if (OrderType.STOP_LIMIT.equals(request.getType())) {
            if (request.getStopPrice() != null) {
                if (okexOrderLimit.getTickSize().scale() < request.getStopPrice().stripTrailingZeros().scale()) {
                    throw new IllegalArgumentException("stopPrice不符合okex的交易价格精度" + okexOrderLimit.getTickSize().scale() + ",请注意");
                }
            } else {
                throw new IllegalArgumentException("下单类型为" + request.getType().getName() + ",stopPrice为空,请注意");
            }

        }
        return true;
    }

    @Override
    public void initOrderLimit(List<Market> markets) {
        OkexOrderLimit btc = OkexOrderLimit.builder()
                .sizeIncrement(new BigDecimal("0.00000001"))
                .tickSize(new BigDecimal("0.1")).build();
        btc.setSymbol(CoinPair.of("BTC", "USDT").toSymbol());
        btc.setMinOrderAmount(new BigDecimal("0.001"));

        OkexOrderLimit eth = OkexOrderLimit.builder()
                .sizeIncrement(new BigDecimal("0.000001"))
                .tickSize(new BigDecimal("0.01")).build();
        eth.setSymbol(CoinPair.of("ETH", "USDT").toSymbol());
        eth.setMinOrderAmount(new BigDecimal("0.001"));

        OrderLimitManager orderLimitManager = exchangeApi.getOrderLimitManager();
        orderLimitManager.addOrderLimit(exchangeApi.getName(), btc);
        orderLimitManager.addOrderLimit(exchangeApi.getName(), eth);
    }
}
