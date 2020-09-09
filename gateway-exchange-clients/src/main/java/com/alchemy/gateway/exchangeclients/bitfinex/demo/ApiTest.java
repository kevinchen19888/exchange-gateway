package com.alchemy.gateway.exchangeclients.bitfinex.demo;
import com.alchemy.gateway.core.order.RoleType;

import com.alchemy.gateway.core.GatewayException;
import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.common.Credentials;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.marketdata.CandleTick;
import com.alchemy.gateway.core.order.*;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alchemy.gateway.core.wallet.AccountAssetResp;
import com.alchemy.gateway.exchangeclients.bitfinex.BiffinexFeatures;
import com.alchemy.gateway.exchangeclients.bitfinex.BitfinexExchangeApi;
import com.alchemy.gateway.exchangeclients.bitfinex.BitfinexOrder;
import com.alchemy.gateway.exchangeclients.bitfinex.entity.*;
import com.alchemy.gateway.exchangeclients.common.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ArrayType;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @Description desc
 * @Author allengent@163.com
 * @Date 2020/7/27 14:25
 * @Version 1.0
 */
public class ApiTest {


 //     public static Credentials credentials = Credentials.of("utnN8cgNIAKkw6C7uHfymn2DHLpkYx3TnOYjiSY9Wde", "rP0yMeqTS7jXkc4TcJO0pHNEIX265GztnWeLMzzlCeL", null);

    public static Credentials credentials = Credentials.of("yne9RTzvPE02rsqmFLEJjbdlMSFoQthK2o0kFNm1QPu", "DQzDdCWk6jdJaBvXJ6XDX7C4M0USXbwZ7QP7Q7Jb06e", null);


//    public static RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory() {{
//        setProxy(new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 1080)));
//    }});

    public static   RestTemplate restTemplate = new RestTemplate();

    private static final Pattern TIMESTAMP_REG = Pattern.compile("^[0-9]{1,20}$");

    public static String host = "https://api.bitfinex.com/";


    public static void main(String[] args) {


      //  getTransfer();
    //   testGetHistoryOrder();
      //  testGetTrade(49392896690L);
        //   testGetCandles();
        //    parseOrderResponse();
       //    getGetAsset();
        //    getGetRechareWithdrawList();
////
// String id= testPlaceOrder();
////
        //    getGetAsset();
////
       testPlaceOrder();
////
//       getGetAsset();
//
   //   testGetOrder("49438931133");
//
        //  testCancelOrder("49335265447");
//
//        getGetAsset();


    }

    public static void getGetAsset() {

        String requestPath = "v2/auth/r/wallets";

        try {
            requestPath = URLDecoder.decode(requestPath, "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = BiffinexFeatures.getHeadersToApiKey(credentials, requestPath, "");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String url = host + requestPath;

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        System.out.println("头:" + responseEntity.getHeaders() + "  data:" + responseEntity.getBody());

        List<AccountAssetResp> assetRespList = new ArrayList<>();
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            Objects.requireNonNull(responseEntity.getBody());

            System.out.println("body=" + responseEntity.getBody());

            JSONArray array = JSONArray.parseArray(responseEntity.getBody());
            for (int i = 0; i < array.size(); i++) {
                BitfinexWallet wallet = jsonArrayToWallet(array.getJSONArray(i));
                System.out.println(" asset : " + wallet);
            }


        }

    }


    public static void getTransfer() {

        String requestPath = "v2/auth/r/audit/hist";

        try {
            requestPath = URLDecoder.decode(requestPath, "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = BiffinexFeatures.getHeadersToApiKey(credentials, requestPath, "");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String url = host + requestPath;

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        System.out.println("头:" + responseEntity.getHeaders() + "  data:" + responseEntity.getBody());

        List<AccountAssetResp> assetRespList = new ArrayList<>();
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            Objects.requireNonNull(responseEntity.getBody());

            System.out.println("body=" + responseEntity.getBody());

            JSONArray array = JSONArray.parseArray(responseEntity.getBody());
            for (int i = 0; i < array.size(); i++) {
                BitfinexWallet wallet = jsonArrayToWallet(array.getJSONArray(i));
                System.out.println(" asset : " + wallet);
            }


        }

    }


    public static void getGetRechareWithdrawList() {

        String requestPath = "v2/auth/r/movements/hist";

        try {
            requestPath = URLDecoder.decode(requestPath, "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = BiffinexFeatures.getHeadersToApiKey(credentials, requestPath, "");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String url = host + requestPath;

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        System.out.println("头:" + responseEntity.getHeaders() + "  data:" + responseEntity.getBody());
        System.out.println("body:" + responseEntity.getBody());


        ObjectMapper mapper = new ObjectMapper();
        ArrayType constructCollectionType = mapper.getTypeFactory().constructArrayType(Movement.class);

        try {
            Movement[] movements = mapper.readValue(responseEntity.getBody(), constructCollectionType);

            for (Movement mov : movements) {
                System.out.println(mov);
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    private static BitfinexWallet jsonArrayToWallet(final JSONArray json) {
        final String walletType = json.getString(0);
        final String currency = json.getString(1);
        final BigDecimal balance = json.getBigDecimal(2);
        final BigDecimal unsettledInterest = json.getBigDecimal(3);
        final BigDecimal balanceAvailable = json.getBigDecimal(4);

        return new BitfinexWallet(walletType, currency, balance, unsettledInterest, balanceAvailable);
    }


    public static String testPlaceOrder() {


        String requestPath = "v2/auth/w/order/submit";


        long timeStamp = System.currentTimeMillis();

        OrderRequest request = new OrderRequest(1L, 1L,
                Market.spotMarket(new CoinPair("BTC", "USDT")),
                OrderType.MARKET,
                null,
                null,
                new BigDecimal("0.0009"),
                OrderSide.SELL,"huobi",null);

        Map<String, Object> params = new LinkedHashMap<>();
        //  params.put("cid", System.currentTimeMillis());
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

                params.put("price_aux_limit", request.getStopPrice().toPlainString());
                break;
        }


        //把mine平台的USDT 转换成 bitfinex的USD
        if ("USDT".equals(request.getMarket().getCoinPair().getBuyCoin())) {
            params.put("symbol", "tBTCUSD");
        } else {
            params.put("symbol", "t" + request.getMarket().getCoinPair().getSellCoin() + request.getMarket().getCoinPair().getBuyCoin());
        }



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

        System.out.println("下单参数 ： " + json);
        HttpHeaders headers = BiffinexFeatures.getHeadersToApiKey(credentials, requestPath, json);

        HttpEntity<String> httpEntity = new HttpEntity<>(json, headers);
        ResponseEntity<String> exchangeResp = restTemplate.postForEntity(host + requestPath,
                httpEntity, String.class);

        if (HttpUtil.isSuccessResp(exchangeResp)) {

            String body = exchangeResp.getBody();
            System.out.println(body);

//            JSONArray array = JSONArray.parseArray(body);
//            JSONArray detail= array.getJSONArray(4);
//            String ret= detail.getJSONArray(0).getLong(0).toString();
//            System.out.println("submited order id="+ret);

            try {
                BitfinexSubmitOrderResponse response = mapper.readValue(Objects.requireNonNull(body), BitfinexSubmitOrderResponse.class);
                System.out.println(requestPath);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            return "";

            // [1595918197,"on-req",null,null,[[48151781732,null,1595918197623,"tBTCUSD",1595918197623,1595918197623,-0.0007,-0.0007,"EXCHANGE LIMIT",null,null,null,0,"ACTIVE",null,null,10800,0,0,0,null,null,null,0,0,null,null,null,"API>BFX",null,null,{"aff_code":"AFF_CODE_HERE"}]],null,"SUCCESS","Submitting 1 orders."]


        } else {

            throw new GatewayException("下单请求失败,statusCode:" + exchangeResp.getStatusCode());
        }
    }

    public static void testGetOrder() {

        String requestPath = "v2/auth/r/orders/" + "tBTCUSD";

        String url = host + requestPath;

        HttpHeaders headers = BiffinexFeatures.getHeadersToApiKey(credentials, requestPath, "");


        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

        if (HttpUtil.isSuccessResp(responseEntity)) {
            JSONArray array = JSONArray.parseArray(responseEntity.getBody());

            for (int i = 0; i < array.size(); i++) {
                JSONArray ob = array.getJSONArray(i);
                System.out.println(ob);

            }


        } else {

            throw new GatewayException("bitfinex  get order 请求失败,statusCode:" + responseEntity.getStatusCode());
        }
    }


    public static void parseOrderResponse() {

        String body = "[1597747966,\"on-req\",null,null,[[49335265447,null,1597747966435,\"tBTCUSD\",1597747966435,1597747966435,-0.00071,-0.00071,\"EXCHANGE LIMIT\",null,null,null,0,\"ACTIVE\",null,null,13000,0,0,0,null,null,null,0,0,null,null,null,\"API>BFX\",null,null,{\"aff_code\":\"AFF_CODE_HERE\"}]],null,\"SUCCESS\",\"Submitting 1 orders.\"]";

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


    }

    public static BitfinexOrder testGetOrder(String orderId) {
        String requestPath = "v2/auth/r/orders/" + "tBTCUSD";

        String url = host + requestPath;

        String json = null;
        if (orderId != null) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", Arrays.asList(new Long[]{Long.valueOf(orderId)}));
            ObjectMapper mapper = new ObjectMapper();

            try {
                json = mapper.writeValueAsString(params);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("bitfinex get order error!");
            }

        }
        HttpHeaders headers = BiffinexFeatures.getHeadersToApiKey(credentials, requestPath, json);

        HttpEntity<String> httpEntity = new HttpEntity<>(json, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);

        if (HttpUtil.isSuccessResp(responseEntity)) {
            String body = responseEntity.getBody();

            ObjectMapper mapper = new ObjectMapper();
            List<BitfinexSubmitOrderDetail> orderList;

            try {
                orderList = mapper.readValue(body, new TypeReference<List<BitfinexSubmitOrderDetail>>() {});

            } catch (JsonProcessingException e) {
                throw  new IllegalArgumentException("解析报文出错：报文 内容 "+body);
            }

            if (orderList==null || orderList.size()==0)
            {
                throw  new IllegalArgumentException("无此订单 "+body);
            }

            BitfinexSubmitOrderDetail detail=orderList.get(0);

            BitfinexOrder order = new BitfinexOrder();
            order.setMineOrderId(0L);
            //此处setOrderId要填写 交易所里面的orderId，用于撤单等逻辑
            order.setOrderId(detail.getId().toString());
            order.setExchangeName("bitfinex");
            order.setState(detail.getOrderStatus());

            BigDecimal finished=detail.getAmountOrigin().abs().subtract(detail.getAmount().abs());

            order.setFinishedVolume(finished);
            order.setFinishedAmount(finished.multiply(detail.getPriceAvg()));
            order.setFinishedFees(new BigDecimal("0"));
            order.setRebateCoin(null);
            order.setRebate(null);

            //state:  NEW  PARTIALLY_FILLED CANCELED PENDING_CANCEL REJECTED FILLED
            if (detail.getAmount().compareTo(BigDecimal.ZERO)==0)
            {
                order.setState("FILLED");
            }else
            {
                order.setState(detail.getOrderStatus());
            }

            //LIMIT  MARKET  STOP_LOSS_LIMIT
            String type ="LIMIT";
            if (detail.getType().indexOf("LIMIT")>0)
            {
                type="LIMIT";
            }else      if (detail.getType().indexOf("MARKET")>0)
            {
                type="MARKET";
            }else      if (detail.getType().indexOf("STOP")>0)
            {
                type="STOP_LOSS_LIMIT";
            }
            order.setOrderType(type);

            //BUY SELL
            order.setOrderSide(detail.getAmountOrigin().compareTo(BigDecimal.ZERO)>0?"BUY":"SELL");

            return order;


        } else {

            throw new GatewayException("bitfinex  get order 请求失败,statusCode:" + responseEntity.getStatusCode());
        }
    }


    /**
     * https://api.bitfinex.com/v2/auth/r/orders/Symbol/hist
     * @return
     */
    public static BitfinexOrder testGetHistoryOrder() {
        //
        String requestPath = "v2/auth/r/orders/" + "tBTCUSD"+"/hist";

        String url = host + requestPath;

        HttpHeaders headers = BiffinexFeatures.getHeadersToApiKey(credentials, requestPath, "");

        HttpEntity<String> httpEntity = new HttpEntity<>( headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);

        if (HttpUtil.isSuccessResp(responseEntity)) {
            String body = responseEntity.getBody();

            ObjectMapper mapper = new ObjectMapper();
            List<BitfinexHistoryOrder> orderList;

            try {
                orderList = mapper.readValue(body, new TypeReference<List<BitfinexHistoryOrder>>() {});

            } catch (JsonProcessingException e) {
                throw  new IllegalArgumentException("解析报文出错：报文 内容 "+body);
            }

            if (orderList==null || orderList.size()==0)
            {
                throw  new IllegalArgumentException("无此订单 "+body);
            }


            for(BitfinexHistoryOrder detail:orderList) {

                BitfinexOrder order = new BitfinexOrder();
                order.setMineOrderId(0L);
                //此处setOrderId要填写 交易所里面的orderId，用于撤单等逻辑
                order.setOrderId(detail.getId().toString());
                order.setExchangeName("bitfinex");
                order.setState(detail.getOrderStatus());

                BigDecimal finished = detail.getAmountOrigin().abs().subtract(detail.getAmount().abs());

                order.setFinishedVolume(finished);
                order.setFinishedAmount(finished.multiply(detail.getPriceAvg()));
                order.setFinishedFees(new BigDecimal("0"));
                order.setRebateCoin(null);
                order.setRebate(null);

                //state:  NEW  PARTIALLY_FILLED CANCELED PENDING_CANCEL REJECTED FILLED
                if (detail.getAmount().compareTo(BigDecimal.ZERO) == 0) {
                    order.setState("FILLED");
                } else {
                    order.setState(detail.getOrderStatus());
                }

                //LIMIT  MARKET  STOP_LOSS_LIMIT
                String type = "LIMIT";
                if (detail.getType().indexOf("LIMIT") > 0) {
                    type = "LIMIT";
                } else if (detail.getType().indexOf("MARKET") > 0) {
                    type = "MARKET";
                } else if (detail.getType().indexOf("STOP") > 0) {
                    type = "STOP_LOSS_LIMIT";
                }
                order.setOrderType(type);

                //BUY SELL
                order.setOrderSide(detail.getAmountOrigin().compareTo(BigDecimal.ZERO) > 0 ? "BUY" : "SELL");

                System.out.println("order:" + order);
            }

            return null;


        } else {

            throw new GatewayException("bitfinex  get order 请求失败,statusCode:" + responseEntity.getStatusCode());
        }
    }



    public static TradesResult testGetTrade(Long orderId) {

        List<TradeVo> tradeVos = new ArrayList<>();
        TradesResult result = new TradesResult();
        result.setTradeVos(tradeVos);

        //${url}v2/auth/r/order/tBTCUSD:12345/trades`,
        String requestPath = "v2/auth/r/order/" + "tBTCUSD"+":"+orderId+"/trades";

        String url = host + requestPath;

        HttpHeaders headers = BiffinexFeatures.getHeadersToApiKey(credentials, requestPath, "");

        HttpEntity<String> httpEntity = new HttpEntity<>( headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);

        if (HttpUtil.isSuccessResp(responseEntity)) {
            String body = responseEntity.getBody();

            ObjectMapper mapper = new ObjectMapper();
            List<BitfinexTradesResponse> tradeList;

            try {
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

            throw new GatewayException("bitfinex  get order 请求失败,statusCode:" + responseEntity.getStatusCode());
        }

        return result;
    }



    public static boolean testCancelOrder(String orderId) {


        String requestPath = "v2/auth/w/order/cancel";

        String url = host + requestPath;

        Map<String, Object> params = new LinkedHashMap<>();

        params.put("id", Long.parseLong(orderId));

        ObjectMapper mapper = new ObjectMapper();

        String json = null;
        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = BiffinexFeatures.getHeadersToApiKey(credentials, requestPath, json);

        HttpEntity<String> httpEntity = new HttpEntity<>(json, headers);
        ResponseEntity<String> exchangeResp = restTemplate.postForEntity(url,
                httpEntity, String.class);

        if (HttpUtil.isSuccessResp(exchangeResp)) {

            String body = exchangeResp.getBody();
            System.out.println(body);

            JSONArray array = JSONArray.parseArray(body);
            System.out.println(array);
            String ret = array.getString(6);
            if ("SUCCESS".equals(ret)) {
                return true;
            }


        } else {

            throw new GatewayException("bitfinex 取消订单失败!" + exchangeResp.getStatusCode());
        }
        return false;

    }


    public static void testGetCandles() {

        // candles/trade:30m:tBTCUSD/hist

//        2001-06-11T03:00,结束时间2020-08-10T03:00


        String testStart = "2001-06-11T03:00";

        String testEnd = "2020-08-10T03:00";

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        LocalDateTime start = LocalDateTime.parse(testStart, df);
        LocalDateTime end = LocalDateTime.parse(testEnd, df);


        StringBuilder uri = new StringBuilder("candles/trade:14D:tBTCUSD/hist?limit=1000&startTime=153590400000&endTime=1363190400000");

//        long startTimeStamp = LocalDateTime.now().plusDays(10000).toInstant(ZoneOffset.of("+8")).toEpochMilli();
//
//        long startTimeStamp = LocalDateTime.now().minusDays(100).toEpochSecond(ZoneOffset.UTC);
//        uri.append("&start=").append(startTimeStamp);


//        if (start != null) {
//            long startTimeStamp = start.toInstant(ZoneOffset.of("+8")).toEpochMilli();
//            // 如果不符合bitfinex开始时间格式要求,则默认传0(1970年时间)
//            if (!TIMESTAMP_REG.matcher(String.valueOf(startTimeStamp)).find()) {
//                startTimeStamp = 0;
//            }
//            uri.append("&startTime=").append(startTimeStamp);
//        }
//        if (end != null) {
//            long endTimeStamp = end.toInstant(ZoneOffset.of("+8")).toEpochMilli();
//            if (!TIMESTAMP_REG.matcher(String.valueOf(endTimeStamp)).find()) {
//                endTimeStamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
//            }
//            uri.append("&endTime=").append(endTimeStamp);
//        }


//            StringBuilder uri=new StringBuilder("candles/trade:7D:tBTCUSD/hist?limit=1000");

        ResponseEntity<String> exchangeResp = restTemplate.getForEntity(BitfinexExchangeApi.RESTFUL_QUATATION
                + uri.toString(), String.class);

        if (HttpUtil.isSuccessResp(exchangeResp)) {
            JSONArray klineArray = JSONArray.parseArray(exchangeResp.getBody());
            List<CandleTick> klines = new ArrayList<>();
            if (klineArray != null) {
                for (int i = 0; i < klineArray.size(); i++) {
                    JSONArray kline = klineArray.getJSONArray(i);

                    CandleTick candle = new CandleTick();
                    candle.setTimeStamp(DateUtils.getEpochMilliByTime(kline.getLong(0)));
                    candle.setHigh(kline.getBigDecimal(3));
                    candle.setOpen(kline.getBigDecimal(1));
                    candle.setLow(kline.getBigDecimal(4));
                    candle.setClose(kline.getBigDecimal(2));
                    candle.setVolume(kline.getBigDecimal(5));


                    klines.add(candle);
                }
            }


        }
    }

}
