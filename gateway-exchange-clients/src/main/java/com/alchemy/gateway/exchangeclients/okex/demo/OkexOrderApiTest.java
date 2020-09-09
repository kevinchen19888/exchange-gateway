package com.alchemy.gateway.exchangeclients.okex.demo;

import com.alchemy.gateway.core.ExchangeApi;
import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.common.Credentials;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.order.*;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alchemy.gateway.exchangeclients.okex.OkexExchangeApi;
import com.alchemy.gateway.exchangeclients.okex.exception.APIException;
import com.alchemy.gateway.exchangeclients.okex.impl.OkexAlgoOrderVo;
import com.alchemy.gateway.exchangeclients.okex.impl.OkexFeatures;
import com.alchemy.gateway.exchangeclients.okex.impl.OkexOrderVo;
import com.alchemy.gateway.exchangeclients.okex.impl.OkexTradeVo;
import com.alchemy.gateway.exchangeclients.okex.param.OrderAlgoParam;
import com.alchemy.gateway.exchangeclients.okex.param.PlaceOrderParam;
import com.alchemy.gateway.exchangeclients.okex.resultModel.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
public class OkexOrderApiTest {

    private static final ExchangeApi exchangeApi = new OkexExchangeApi();

    private static final RestTemplate restTemplate = new RestTemplate();

    private static final Credentials credentials = Credentials.of("64a13f1f-7322-4d63-b75b-18e0972197dc",
            "02858F17E96454ED149CDB17C7B692E4", "7nNBEV0WvhHI3uAk");

    public static void main(String[] args) {
        //textOkexPlaceOrder();
        //placeStopLimitOrder();
        //cancelOrder(credentials,"",CoinPair.of("BTC","USDT"),OrderType.STOP_LIMIT);
        OkexOrderVo orderVo = new OkexOrderVo();
        orderVo.setExchangeOrderId("5489601706020864");
        orderVo.setType(OrderType.LIMIT.getName().toLowerCase());
        orderVo.setMarket(Market.spotMarket(CoinPair.of("ETH", "USDT")));
        orderVo.setSide(OrderSide.BUY.name());
        tradesInfo(credentials, orderVo);
        /*String fees = null;
        if (fees != null && !fees.isEmpty()) {
            BigDecimal fee = new BigDecimal(fees);
            if (fee.compareTo(new BigDecimal("0")) < 0) {//如果是负数就去绝对值作为手续费，反之则为零（返佣从详情中返回）
                System.out.println(fee.abs());
            } else {
                System.out.println(new BigDecimal("0"));
            }
        } else {
            System.out.println("空");
        }*/
    }


    /**
     * 市价下单集成测试
     */
    private static void textOkexPlaceOrder() {

        OrderRequest request = new OrderRequest(1232141234L, 1232141234L,
                Market.spotMarket(CoinPair.of("ETH", "USDT")), OrderType.LIMIT, new BigDecimal("300.00000000"),
                null, new BigDecimal("0.05000000"), OrderSide.BUY, "Okex", null);

        exchangeApi.getOrderApi().initOrderLimit(null);

        if (exchangeApi.getOrderApi().checkOrderDefined(request)) {
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

            String requestPath = "/api/spot/v3/orders";

            ObjectMapper objectMapper = new ObjectMapper();
            String body;
            try {
                body = objectMapper.writeValueAsString(orderParam);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("cancelBatchAlgoOrder的json解析错误");
            }

            HttpHeaders headers = OkexFeatures.getHeadersToApiKey(credentials, HttpMethod.POST.name(), requestPath, "", body);
            HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
            String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath;

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            log.info("请求数据:{}", responseEntity.getBody());
            log.info("{}", responseEntity.getHeaders());

            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                if (!Objects.requireNonNull(responseEntity.getBody()).isEmpty()) {
                    OrderResult orderResult = JSON.parseObject(responseEntity.getBody(), OrderResult.class);
                    if (!orderResult.getError_code().equals("0")) {
                        throw new APIException("error_code:" + orderResult.getError_code() + ",error_message:" + orderResult.getError_message());
                    } else {
                        log.info("订单id：{}", orderResult.getOrder_id());
                    }
                }
            } else {
                throw new IllegalStateException(HttpMethod.POST + url + "请求失败,statusCode:" + responseEntity.getStatusCode());
            }
        }
    }

    /**
     * 止盈止损下单
     */
    private static void placeStopLimitOrder() {

        OrderRequest request = new OrderRequest(1232141234L, 1232141234L,
                Market.spotMarket(CoinPair.of("BTC", "USDT")), OrderType.STOP_LIMIT, new BigDecimal("0.01000000"),
                new BigDecimal("0.1"), new BigDecimal("12"), OrderSide.SELL, "Okex", null);

        exchangeApi.getOrderApi().initOrderLimit(null);

        if (exchangeApi.getOrderApi().checkOrderDefined(request)) {
            OrderAlgoParam orderParam = new OrderAlgoParam();
            orderParam.setInstrumentId(exchangeApi.getCoinPairSymbolConverter().coinPairToSymbol(request.getMarket().getCoinPair()));
            orderParam.setMode("1");
            orderParam.setOrderType("1");
            orderParam.setSide(request.getOrderSide().name().toLowerCase());
            orderParam.setSize(request.getAmount().stripTrailingZeros().toPlainString());
            orderParam.setAlgoPrice(request.getPrice().stripTrailingZeros().toPlainString());
            orderParam.setAlgoType(request.getType());
            orderParam.setTriggerPrice(request.getStopPrice().stripTrailingZeros().toPlainString());

            String requestPath = "/api/spot/v3/order_algo";

            ObjectMapper objectMapper = new ObjectMapper();
            String body;
            try {
                body = objectMapper.writeValueAsString(orderParam);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("cancelBatchAlgoOrder的json解析错误");
            }

            HttpHeaders headers = OkexFeatures.getHeadersToApiKey(credentials, HttpMethod.POST.name(), requestPath, "", body);
            HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
            String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath;

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            log.info("请求数据:{}", responseEntity.getBody());
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                if (!Objects.requireNonNull(responseEntity.getBody()).isEmpty()) {
                    OrderAlgoResult orderResult = JSON.parseObject(responseEntity.getBody(), OrderAlgoResult.class);
                    if (!orderResult.getError_code().equals("0")) {
                        throw new APIException("error_code:" + orderResult.getError_code() + ",error_message:" + orderResult.getError_message());
                    } else {
                        log.info("订单id：{}", orderResult.getAlgo_id());
                    }
                }
            } else {
                throw new IllegalStateException(HttpMethod.POST + url + "请求失败,statusCode:" + responseEntity.getStatusCode());
            }
        }
    }

    /**
     * 撤销市价限价下单
     *
     * @param credentials 账户信息
     * @param orderId     订单id
     * @param coinPair    币对信息
     * @param type        下单类型
     * @return 是否成功
     */
    private static boolean cancelOrder(Credentials credentials, String orderId, CoinPair coinPair, OrderType type) {
        String requestPath = "/api/spot/v3/cancel_orders/" + orderId;
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("instrument_id", exchangeApi.getCoinPairSymbolConverter().coinPairToSymbol(coinPair));
        String body = JSONObject.toJSONString(bodyMap);

        HttpHeaders headers = OkexFeatures.getHeadersToApiKey(credentials, HttpMethod.POST.name(), requestPath, "", body);
        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath;

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        log.info("请求数据:{}", responseEntity.getBody());
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

    private static void tradesInfo(Credentials credentials, OrderVo orderVo) {

        //判断是否是委托单,如果是先拿到订单Id获取成交明细
        if (orderVo.getType().equals(OrderType.STOP_LIMIT)) {
            OrderVo okexOrderVo = getOrder(credentials, orderVo.getExchangeOrderId(), orderVo.getMarket().getCoinPair(), orderVo.getType());
            if (okexOrderVo != null && !okexOrderVo.getExchangeOrderId().isEmpty()) {
                orderVo = okexOrderVo;
            }
        }

        List<TradeVo> result = new ArrayList<>();
        int count = 0;//计数器(当获取不到成交明细时)
        Long afterId = null;
        boolean flag = true;
        while (flag && count < 10) {
            List<Fills> fillsList = okexTradeList(credentials, orderVo, afterId);//正常情况下
            //List<Fills> fillsList = null;//获取不到数据时查询10次结束任务
            if (fillsList != null && fillsList.size() > 0) {
                result.addAll(tradesInfo(orderVo, fillsList));
                afterId = fillsList.get(fillsList.size() - 1).getLedger_id();
                while (true) {
                    fillsList = okexTradeList(credentials, orderVo, afterId);
                    if (fillsList.size() == 0) {
                        break;
                    }
                    result.addAll(tradesInfo(orderVo, fillsList));
                    afterId = fillsList.get(fillsList.size() - 1).getLedger_id();
                }
                flag = false;
            } else {
                try {
                    Thread.sleep(300);
                    count++;
                } catch (InterruptedException e) {
                    log.error("okex订单:{},获取成交明细错误信息:{}", orderVo.getExchangeOrderId(), e.getMessage());
                }
            }
        }
        if (count >= 10) {
            log.error("okex订单:{}多次获取成交明细无数据,请核查", orderVo.getExchangeOrderId());
        }
        log.info("{}", result.toString());
    }

    /**
     * 处理成交明细结果
     *
     * @param orderVo   订单信息
     * @param fillsList 成交明细
     * @return 内部成交明细信息
     */
    private static List<TradeVo> tradesInfo(OrderVo orderVo, List<Fills> fillsList) {
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
    private static List<Fills> okexTradeList(Credentials credentials, OrderVo orderVo, Long afterId) {
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


    /*private static Long afterId = null;//成交明细游标Id
    private static final List<TradeVo> items = new ArrayList<>();//按成交时间倒序排序和存储，最新的排在最前面
    private static int count = 0;//获取成交明细次数*/

    /**
     * 成交明细
     *
     * @param credentials 账户信息
     * @param orderVo     订单信息
     */
    private static void getTrades(Credentials credentials, OrderVo orderVo) {

       /* TradesResult tradesResult = new TradesResult();

        //判断是否是委托单,如果是拿到订单Id获取成交明细
        String orderId = orderVo.getExchangeOrderId();
        OrderVo okexOrderVo;
        if (orderVo.getType().equals(OrderType.STOP_LIMIT)) {
            okexOrderVo = getOrder(credentials, orderId, orderVo.getMarket().getCoinPair(), orderVo.getType());
            if (!okexOrderVo.getExchangeOrderId().isEmpty()) {
                orderId = okexOrderVo.getExchangeOrderId();
            }
        }

        //获取成交明细
        String requestPath = "/api/spot/v3/fills";

        StringBuilder queryString = new StringBuilder().append("order_id=").append(orderId)
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
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            if (!Objects.requireNonNull(responseEntity.getBody()).isEmpty()) {
                List<Fills> fillsList = JSONArray.parseArray(responseEntity.getBody(), Fills.class);
                Map<String, OkexTradeVo> tradeVoMap = new HashMap<>();
                if (fillsList != null && fillsList.size() > 0) {
                    fillsList.forEach(fills -> {
                        OkexTradeVo tradeVo = tradeVoMap.get(fills.getTrade_id());
                        if (tradeVo == null) {
                            tradeVo = new OkexTradeVo();
                        }
                        if (orderVo.getSide().name().toLowerCase().equals(fills.getSide())) {
                            tradeVo.setCreatedAt(DateUtils.getTimeByUtc(fills.getTimestamp()));
                            tradeVo.setExchangeName(exchangeApi.getName());
                            tradeVo.setExchangeOrderId(String.valueOf(fills.getOrder_id()));
                            tradeVo.setExchangeTradeId(fills.getTrade_id());
                            tradeVo.setFeeDeductCoin(fills.getCurrency());
                            tradeVo.setFilledAmount(new BigDecimal(StringUtils.isEmpty(fills.getSize()) ? "0" : fills.getSize()));
                            tradeVo.setFilledFee(new BigDecimal(StringUtils.isEmpty(fills.getFee()) ? "0" : fills.getFee()));
                            tradeVo.setPrice(new BigDecimal(StringUtils.isEmpty(fills.getPrice()) ? "0" : fills.getPrice()));
                            tradeVo.setRole(fills.getExec_type());
                            afterId = fills.getLedger_id();
                            tradeVoMap.put(tradeVo.getExchangeTradeId(), tradeVo);
                        } else {
                            tradeVo = tradeVoMap.get(fills.getTrade_id());
                            tradeVo.setFeeDeductCoin(fills.getCurrency());
                            tradeVo.setFilledFee(new BigDecimal(StringUtils.isEmpty(fills.getFee()) ? "0" : fills.getFee()));
                            items.add(tradeVo);
                        }
                    });
                    if (fillsList.size() >= 100) {
                        getTrades(credentials, orderVo);
                    }
                    count = 0;
                } else {
                    if (count < 10) {
                        try {
                            Thread.sleep(300);
                            getTrades(credentials, orderVo);
                            log.info("订单:{},第{}次获取成交明细:{}", orderId, count, tradesResult);
                            count++;
                        } catch (InterruptedException e) {
                            log.error("okex订单:{},获取成交明细错误信息:{}", orderId, e.getMessage());
                        }
                    } else {
                        log.error("okex订单:{}多次获取成交明细无数据,请核查", orderId);
                    }
                }
            }
        } else {
            throw new IllegalStateException(HttpMethod.GET + url + "请求失败,statusCode:" + responseEntity.getStatusCode());
        }

        //获取费率及费率币种
        OrderVo order = getOrder(credentials, orderVo.getExchangeOrderId(), orderVo.getMarket().getCoinPair(), orderVo.getType());

        tradesResult.setTradeVos(items);
        tradesResult.setRebate(new BigDecimal(String.valueOf(order.getRebate())));
        tradesResult.setRebateCoin(order.getRebateCoin());
        log.info("订单{}成交明细:{}", orderId, tradesResult);*/
    }

    /**
     * 订单详情
     */
    private static OrderVo getOrder(Credentials credentials, String orderId, CoinPair coinPair, OrderType type) {

        if (type.equals(OrderType.STOP_LIMIT)) {
            return getStopLimitOrder(credentials, orderId, coinPair);
        } else {
            return getOkexOrder(credentials, orderId, coinPair);
        }
    }

    /**
     * 限价市价订单详情
     */
    private static OkexOrderVo getOkexOrder(Credentials credentials, String orderId, CoinPair coinPair) {
        String requestPath = "/api/spot/v3/orders/" + orderId;
        String queryString = "instrument_id=" + exchangeApi.getCoinPairSymbolConverter().coinPairToSymbol(coinPair);

        HttpHeaders headers = OkexFeatures.getHeadersToApiKey(credentials, HttpMethod.GET.name(), requestPath, queryString, "");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath + "?" + queryString;

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        log.info("请求数据:{}", responseEntity.getBody());
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

    /**
     * 根据订单id获取止盈止损订单
     */
    private static OrderVo getStopLimitOrder(Credentials credentials, String orderId, CoinPair coinPair) {

        String requestPath = "/api/spot/v3/algo";
        String queryString = "instrument_id=" + exchangeApi.getCoinPairSymbolConverter().coinPairToSymbol(coinPair)
                + "&order_type=1&algo_id=" + orderId;

        HttpHeaders headers = OkexFeatures.getHeadersToApiKey(credentials, HttpMethod.GET.name(), requestPath, queryString, "");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath + "?" + queryString;

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        log.info("请求数据:{}", responseEntity.getBody());
        OkexAlgoOrderVo orderVo = new OkexAlgoOrderVo();
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            if (!Objects.requireNonNull(responseEntity.getBody()).isEmpty()) {
                String key = coinPair.getBuyCoin().toLowerCase() + "_" + coinPair.getSellCoin().toLowerCase();
                JSONObject rst = JSONObject.parseObject(responseEntity.getBody());
                JSONArray dataArr = JSONArray.parseArray(String.valueOf(rst.get(key)));
                OrderAlgoInfo orderInfo = JSONObject.parseObject(String.valueOf(dataArr.get(0)), OrderAlgoInfo.class);
                if (!orderInfo.getOrder_id().isEmpty()) {
                    return getOkexOrder(credentials, orderInfo.getOrder_id(), coinPair);
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
}
