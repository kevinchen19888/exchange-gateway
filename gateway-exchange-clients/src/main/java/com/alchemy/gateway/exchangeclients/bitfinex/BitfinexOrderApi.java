package com.alchemy.gateway.exchangeclients.bitfinex;

import com.alchemy.gateway.core.GatewayException;
import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.common.Credentials;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.order.*;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alchemy.gateway.core.wallet.CursorVo;
import com.alchemy.gateway.exchangeclients.bitfinex.entity.BitfinexHistoryOrder;
import com.alchemy.gateway.exchangeclients.bitfinex.entity.BitfinexSubmitOrderDetail;
import com.alchemy.gateway.exchangeclients.bitfinex.entity.BitfinexTradeVo;
import com.alchemy.gateway.exchangeclients.bitfinex.entity.BitfinexTradesResponse;
import com.alchemy.gateway.exchangeclients.common.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;


@Slf4j
public class BitfinexOrderApi implements OrderApi {
    private final BitfinexExchangeApi exchangeApi;

    private final RateLimiter rateLimiter = RateLimiter.create(0.75);

    public BitfinexOrderApi(BitfinexExchangeApi exchangeApi) {
        this.exchangeApi = exchangeApi;
    }


    @Override
    public OrderVo placeOrder(Credentials credentials, OrderRequest request) {

        // 进行参数校验&订单交易规则校验
        verifyOrderParams(request);

        rateLimiter.acquire();

        String requestPath = "v2/auth/w/order/submit";

        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath;

        RestTemplate restTemplate = exchangeApi.getRestTemplate();
        Map<String, Object> params = new LinkedHashMap<>();

        switch (request.getType())
        {
            case MARKET:
                params.put("type", "EXCHANGE MARKET");
                break;
            case LIMIT:
                params.put("type", "EXCHANGE LIMIT");
                params.put("price", request.getPrice().toPlainString());
                break;
            case STOP_LIMIT:
                params.put("type", "EXCHANGE STOP LIMIT");
                if (request.getStopPrice()==null)
                {
                    throw new IllegalArgumentException(" bitfinex  止盈止损订单 触发价不能为null ");
                }

                params.put("price_aux_limit", request.getStopPrice().toPlainString());
                params.put("price", request.getPrice().toPlainString());
                break;
            default:
                throw  new IllegalArgumentException("不支持的 bitfinex 类型："+request.getType());
        }

        params.put("symbol", BiffinexFeatures.convertUsdt2Usd(request.getMarket().getCoinPair().toSymbol()));

        //  Invalid order: minimum size for BTCUSD is 0.0006"
        if (request.getOrderSide().equals(OrderSide.BUY)) {
            params.put("amount", request.getAmount().toPlainString());
        } else {
            params.put("amount", request.getAmount().negate().toPlainString());
        }

        params.put("flags", 0);

        // meta: {aff_code: "AFF_CODE_HERE"}
        JSONObject object = new JSONObject();
        object.put("aff_code", "AFF_CODE_HERE");
        params.put("meta", object);
        ObjectMapper mapper = new ObjectMapper();

        String json = null;
        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        log.info("bitfinex 下单参数： {}", json);

        HttpHeaders headers = BiffinexFeatures.getHeadersToApiKey(credentials, requestPath, json);

        HttpEntity<String> httpEntity = new HttpEntity<>(json, headers);
        ResponseEntity<String> exchangeResp = restTemplate.postForEntity(url,
                httpEntity, String.class);

        if (HttpUtil.isSuccessResp(exchangeResp)) {
            String body = exchangeResp.getBody();
            BitfinexOrder order = parseOrderResponse(body);
            order.setMineOrderId(request.getAlchemyId());
            return order;
        } else {

            throw new GatewayException("bitfinex下单请求失败,statusCode:" + exchangeResp.getStatusCode());
        }

    }

    public BitfinexOrder parseOrderResponse(String body) {
        JSONArray obj = JSON.parseArray(body);

        JSONArray detail = obj.getJSONArray(4);
        JSONArray jsonObject = detail.getJSONArray(0);

        BitfinexOrder order = new BitfinexOrder();
//
        order.setSymbol(BiffinexFeatures.convertUsd2Usdt(jsonObject.getString(3)));
        String orderSide = "sell";
        if (BigDecimal.ZERO.compareTo(jsonObject.getBigDecimal(6)) < 0) {
            orderSide = "buy";
        }
        order.setOrderSide(orderSide);
        order.setOrderId("" + jsonObject.getLong(0));


        String type;
        if (jsonObject.getString(8).indexOf("LIMIT") > 0) {
            type = "limit";
        } else {
            type = "market";
        }
        order.setOrderType(type);
        order.setOrderCreatedAt(jsonObject.getString(2));
        order.setOrderPrice(jsonObject.getBigDecimal(16).toPlainString());
        return order;
    }


    @Override
    public OrderVo getOrder(Credentials credentials, String exchangeOrderId, CoinPair coinPair, OrderType type) {
        rateLimiter.acquire();

        String requestPath = "v2/auth/r/orders/" + BiffinexFeatures.convert2Usd(coinPair);

        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath;

        String json = null;
        if (exchangeOrderId != null) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", Collections.singletonList(Long.valueOf(exchangeOrderId)));
            ObjectMapper mapper = new ObjectMapper();

            try {
                json = mapper.writeValueAsString(params);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("bitfinex get order error!");
            }

        }
        log.info("bitfinex getOrder : url={},json={}",url,json);
        HttpHeaders headers = BiffinexFeatures.getHeadersToApiKey(credentials, requestPath, json);

        HttpEntity<String> httpEntity = new HttpEntity<>(json, headers);

        RestTemplate restTemplate = exchangeApi.getRestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);

        if (HttpUtil.isSuccessResp(responseEntity)) {
            String body = responseEntity.getBody();

            ObjectMapper mapper = new ObjectMapper();
            List<BitfinexSubmitOrderDetail> orderList;

            try {
                assert body != null;
                orderList = mapper.readValue(body, new TypeReference<List<BitfinexSubmitOrderDetail>>() {
                });

            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("解析报文出错：报文 内容 " + body);
            }


            Market market=Market.spotMarket(coinPair);

            if (orderList == null || orderList.size() == 0) {
                //说明这个订单已经成交，不在当前订单里面，需要查询历史订单 （坑爹呀。。。 有这么坑的设计吗？？？）
                assert exchangeOrderId != null;
                HistoryOrderResult result= getHistoryOrderById( credentials, market ,Long.valueOf(exchangeOrderId));

                List<OrderVo> resultList=result.getList();
                if(resultList!=null && resultList.size()>0)
                {
                    for (OrderVo vo:resultList)
                    {
                        if (exchangeOrderId.equalsIgnoreCase(vo.getExchangeOrderId()))
                        {
                            return vo;
                        }
                    }

                }
                throw new IllegalArgumentException("bitfinex无法找到 订单"+exchangeOrderId);
            }

            BitfinexSubmitOrderDetail detail = orderList.get(0);

            BitfinexOrder order = parseActiveOrder(detail);

            order.setSymbol(coinPair.toSymbol());
            order.setMarket(market);
            return order;


        } else {

            throw new GatewayException("bitfinex  get order 请求失败,statusCode:" + responseEntity.getStatusCode());
        }


    }

    @Override
    public boolean cancelOrder(Credentials credentials, String orderId, CoinPair coinPair, OrderType type) {

        rateLimiter.acquire();
        String requestPath = "v2/auth/w/order/cancel";

        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath;

        RestTemplate restTemplate = exchangeApi.getRestTemplate();
        Map<String, Object> params = new LinkedHashMap<>();

        params.put("id", Long.parseLong(orderId));

        ObjectMapper mapper = new ObjectMapper();

        String json;
        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("bitfinex 下单 json错误!");
        }
        log.info("bitfinex 取消订单参数:{}", json);

        HttpHeaders headers = BiffinexFeatures.getHeadersToApiKey(credentials, requestPath, json);

        HttpEntity<String> httpEntity = new HttpEntity<>(json, headers);
        ResponseEntity<String> exchangeResp = restTemplate.postForEntity(url,
                httpEntity, String.class);

        if (HttpUtil.isSuccessResp(exchangeResp)) {

            String body = exchangeResp.getBody();

            JSONArray array = JSONArray.parseArray(body);
            assert array != null;
            String ret = array.getString(6);
            return "SUCCESS".equals(ret);


        } else {

            throw new GatewayException("bitfinex 取消订单失败!" + exchangeResp.getStatusCode());
        }

    }

    @Override
    public TradesResult getTrades(Credentials credentials, OrderVo orderVo) {

        List<TradeVo> tradeVos = new ArrayList<>();
        TradesResult result = new TradesResult();
        result.setTradeVos(tradeVos);

        rateLimiter.acquire();

        //${url}v2/auth/r/order/tBTCUSD:12345/trades`,
        String requestPath = "v2/auth/r/order/" + BiffinexFeatures.convert2Usd(orderVo.getMarket().getCoinPair()) +":"+orderVo.getExchangeOrderId()+"/trades";

        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath;


        log.info("bitfinex getTrades  url={}",url);

        HttpHeaders headers = BiffinexFeatures.getHeadersToApiKey(credentials, requestPath, "");

        HttpEntity<String> httpEntity = new HttpEntity<>( headers);
        RestTemplate restTemplate = exchangeApi.getRestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);

        if (HttpUtil.isSuccessResp(responseEntity)) {
            String body = responseEntity.getBody();

            ObjectMapper mapper = new ObjectMapper();
            List<BitfinexTradesResponse> tradeList;

            try {
                assert body != null;
                tradeList = mapper.readValue(body, new TypeReference<List<BitfinexTradesResponse>>() {});

            } catch (JsonProcessingException e) {
                throw  new IllegalArgumentException("解析报文出错：报文 内容 "+body);
            }

            if (tradeList==null || tradeList.size()==0)
            {
                return result;
            }

            for(BitfinexTradesResponse trade: tradeList)
            {
                BitfinexTradeVo vo=new BitfinexTradeVo();
                vo.setExchangeOrderId(trade.getOrderId().toString());
                vo.setExchangeName("bitfinex");
                vo.setPrice(trade.getExecPrice());
                vo.setType(trade.getMaker()==1?RoleType.maker:RoleType.taker);
                vo.setCreatedAt(DateUtils.getEpochMilliByTime(trade.getMtsCreate()));
                vo.setOrderId(trade.getOrderId());
                vo.setExchangeTradeId(trade.getId().toString());
                vo.setFeeDeductCoin(trade.getFeeCoin().equalsIgnoreCase("USD")?"USDT":trade.getFeeCoin());
                vo.setFeeDeductAmount(trade.getFee().abs());
                vo.setFilledAmount(trade.getExecAmount().abs());
                vo.setFilledFee(trade.getFee().abs());
                tradeVos.add(vo);
            }

        } else {

            throw new GatewayException("bitfinex  getTrades 请求失败,statusCode:" + responseEntity.getStatusCode());
        }

        return result;
    }

    @Override
    public HistoryOrderResult getHistoryOrder(Credentials credentials, CursorVo cursorVo, Market market) {

        return getHistoryOrderById(credentials, market,null);
    }


    private HistoryOrderResult getHistoryOrderById(Credentials credentials, Market market, Long orderId) {

        List<OrderVo> list = new ArrayList<>();
        HistoryOrderResult result = new HistoryOrderResult();
        result.setList(list);

        rateLimiter.acquire();

        String requestPath = "v2/auth/r/orders/" + BiffinexFeatures.convert2Usd(market.getCoinPair()) + "/hist";

        HttpHeaders headers = BiffinexFeatures.getHeadersToApiKey(credentials, requestPath, "");

//        if (orderId!=null)
//        {
//            requestPath=requestPath+"?id=["+orderId+"]";
//        }

        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath;

        log.info("getHistoryOrderById  url={}",url);



        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = exchangeApi.getRestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);

        if (HttpUtil.isSuccessResp(responseEntity)) {
            String body = responseEntity.getBody();

            ObjectMapper mapper = new ObjectMapper();
            List<BitfinexHistoryOrder> orderList;

            try {
                assert body != null;
                orderList = mapper.readValue(body, new TypeReference<List<BitfinexHistoryOrder>>() {
                });

            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("解析报文出错：报文 内容 " + body);
            }

            if (orderList == null || orderList.size() == 0) {
                return result;
            }
            for (BitfinexHistoryOrder detail : orderList)
            {
                if (detail.getId().equals(orderId)){
                    BitfinexOrder order = parseHistoryOrder(detail);
                    order.setSymbol(market.getCoinPair().toSymbol());
                    order.setMarket(market);
                    list.add(order);
                    break;

                }else
                {
                    BitfinexOrder order = parseHistoryOrder(detail);
                    order.setSymbol(market.getCoinPair().toSymbol());
                    order.setMarket(market);
                    list.add(order);
                }

            }

        }


        return result;
    }

    private static BitfinexOrder parseHistoryOrder(BitfinexHistoryOrder historyOrder)
    {
        BitfinexOrder order = new BitfinexOrder();
        order.setMineOrderId(0L);
        //此处setOrderId要填写 交易所里面的orderId，用于撤单等逻辑
        order.setOrderId(historyOrder.getId().toString());
        order.setExchangeName("bitfinex");
        order.setState(historyOrder.getOrderStatus().toUpperCase());

        BigDecimal finished = historyOrder.getAmountOrigin().abs().subtract(historyOrder.getAmount().abs());

        order.setFinishedVolume(finished);
        order.setFinishedAmount(finished.multiply(historyOrder.getPriceAvg()));
        order.setFinishedFees(new BigDecimal("0"));
        order.setRebateCoin(null);
        order.setRebate(null);

        //state:  NEW  PARTIALLY_FILLED CANCELED PENDING_CANCEL REJECTED FILLED

        if (historyOrder.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            order.setState("filled");
        } else {
            order.setState(historyOrder.getOrderStatus().toLowerCase());
        }

        //LIMIT  MARKET  STOP_LOSS_LIMIT
        String orderType = "LIMIT";
        if (historyOrder.getType().indexOf("LIMIT") > 0) {
            orderType = "LIMIT";
        } else if (historyOrder.getType().indexOf("MARKET") > 0) {
            orderType = "MARKET";
        } else if (historyOrder.getType().indexOf("STOP") > 0) {
            orderType = "STOP_LOSS_LIMIT";
        }
        order.setOrderType(orderType);

        //BUY SELL
        order.setOrderSide(historyOrder.getAmountOrigin().compareTo(BigDecimal.ZERO) > 0 ? "BUY" : "SELL");

        return order;

    }

    private static BitfinexOrder parseActiveOrder( BitfinexSubmitOrderDetail detail)
    {
        BitfinexOrder order = new BitfinexOrder();
        order.setMineOrderId(0L);
        //此处setOrderId要填写 交易所里面的orderId，用于撤单等逻辑
        order.setOrderId(detail.getId().toString());
        order.setExchangeName("bitfinex");
        order.setState(detail.getOrderStatus().toUpperCase());

        BigDecimal finished = detail.getAmountOrigin().abs().subtract(detail.getAmount().abs());

        order.setFinishedVolume(finished);
        order.setFinishedAmount(finished.multiply(detail.getPriceAvg()));
        order.setFinishedFees(new BigDecimal("0"));
        order.setRebateCoin(null);
        order.setRebate(null);

        //state:  NEW  PARTIALLY_FILLED CANCELED PENDING_CANCEL REJECTED FILLED
        if (detail.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            order.setState("filled");
        } else {
            order.setState(detail.getOrderStatus().toLowerCase());
        }

        //LIMIT  MARKET  STOP_LOSS_LIMIT
        String orderType = "LIMIT";
        if (detail.getType().indexOf("LIMIT") > 0) {
            orderType = "LIMIT";
        } else if (detail.getType().indexOf("MARKET") > 0) {
            orderType = "MARKET";
        } else if (detail.getType().indexOf("STOP") > 0) {
            orderType = "STOP_LOSS_LIMIT";
        }
        order.setOrderType(orderType);

        //BUY SELL
        order.setOrderSide(detail.getAmountOrigin().compareTo(BigDecimal.ZERO) > 0 ? "BUY" : "SELL");

        return order;
    }

    @Override
    public boolean checkOrderDefined(OrderRequest request) {
        return true;
    }


    @Override
    public void initOrderLimit(List<Market> markets) {

    }

    private void verifyOrderParams(@NonNull OrderRequest request) {

    }

}
