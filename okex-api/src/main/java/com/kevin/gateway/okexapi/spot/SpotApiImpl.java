package com.kevin.gateway.okexapi.spot;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.rest.*;
import com.kevin.gateway.okexapi.base.rest.impl.OkexAbstractImpl;
import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.spot.model.*;
import com.kevin.gateway.okexapi.spot.request.*;
import com.kevin.gateway.okexapi.spot.response.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SpotApiImpl extends OkexAbstractImpl implements SpotApi {

    private static final PrivateGetTemplate SEARCH_ACCOUNT_ASSETS = PrivateGetTemplate.
            of("/api/spot/v3/accounts", 20, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate SEARCH_ACCOUNT_ASSET = PrivateGetTemplate.
            of("/api/spot/v3/accounts/", 20, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate SEARCH_BILLS_DETAILS = PrivateGetTemplate.
            of("/api/spot/v3/accounts/{currency}/ledger", 10, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate ADD_ORDER = PrivatePostTemplate.
            of("/api/spot/v3/orders", 100, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate ADD_ORDER_LIST = PrivatePostTemplate.
            of("/api/spot/v3/batch_orders", 50, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate CANCEL_ORDER = PrivatePostTemplate.
            of("/api/spot/v3/cancel_orders/{order_id}", 100, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate CANCEL_ORDER_LIST = PrivatePostTemplate.
            of("/api/spot/v3/cancel_batch_orders", 50, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate MODIFY_ORDER = PrivatePostTemplate.
            of("/api/spot/v3/amend_order/{instrument_id}", 20, Duration.ofSeconds(1L));

    private static final PrivatePostTemplate MULTIPLE_MODIFY_ORDER = PrivatePostTemplate.
            of("/api/spot/v3/amend_batch_orders", 20, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate SEARCH_ORDER_INFO_LIST = PrivateGetTemplate.
            of("/api/spot/v3/orders", 10, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate SEARCH_ORDERS_PENDING_LIST = PrivateGetTemplate.
            of("/api/spot/v3/orders_pending", 20, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate SEARCH_ORDER_INFO = PrivateGetTemplate.
            of("/api/spot/v3/orders/{order_id}", 20, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate SEARCH_TRANSACTION_DETAILS = PrivateGetTemplate.
            of("/api/spot/v3/fills", 10, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate ADD_ENTRUST_ORDER = PrivatePostTemplate.
            of("/api/spot/v3/order_algo", 40, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate CANCEL_ENTRUST_ORDER = PrivatePostTemplate.
            of("/api/spot/v3/cancel_batch_algos", 20, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate SEARCH_TRADE_FEE = PrivateGetTemplate.
            of("/api/spot/v3/trade_fee", 20, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate SEARCH_ALGO_ORDER_INFO_LIST = PrivateGetTemplate.
            of("/api/spot/v3/algo", 20, Duration.ofSeconds(2L));

    private static final PublicGetTemplate SEARCH_COIN_PAIR_INFO = PublicGetTemplate.
            of("/api/spot/v3/instruments", 20, Duration.ofSeconds(2L));

    private static final PublicGetTemplate ORDER_BOOKS = PublicGetTemplate.
            of("/api/spot/v3/instruments/{instrument_id}/book", 20, Duration.ofSeconds(2L));

    private static final PublicGetTemplate TICKERS = PublicGetTemplate.
            of("/api/spot/v3/instruments/ticker", 20, Duration.ofSeconds(2L));

    private static final PublicGetTemplate TICKER_BY_COIN_PAIR = PublicGetTemplate.
            of("/api/spot/v3/instruments/{instrument_id}/ticker", 20, Duration.ofSeconds(2L));

    private static final PublicGetTemplate TRADES = PublicGetTemplate.
            of("/api/spot/v3/instruments/{instrument_id}/trades", 20, Duration.ofSeconds(2L));

    private static final PublicGetTemplate CANDLES_DATA = PublicGetTemplate.
            of("/api/spot/v3/instruments/{instrument_id}/candles", 20, Duration.ofSeconds(2L));

    private static final PublicGetTemplate HISTORY_CANDLES_DATA = PublicGetTemplate.
            of("/api/spot/v3/instruments/{instrument_id}/history/candles", 5, Duration.ofSeconds(2L));

    public SpotApiImpl(OkexEnvironment environment) {
        super(environment);
    }

    @Override
    public SpotAccountInfoResponse[] searchAccountAssets(Credentials credentials) {
        PrivateGetTemplateClient client = SEARCH_ACCOUNT_ASSETS.bind(environment, credentials);
        return client.getForObject(SpotAccountInfoResponse[].class);
    }

    @Override
    public SpotAccountInfoResponse searchAccountAsset(Credentials credentials, Coin coin) {
        PrivateGetTemplateClient client = SEARCH_ACCOUNT_ASSET.bind(environment, credentials);
        client.getUriComponentsBuilder().path(coin.getSymbol());
        return client.getForObject(SpotAccountInfoResponse.class);
    }

    @Override
    public SpotBillsDetailsResponse[] searchBillsDetails(Credentials credentials, Coin coin, SpotApiWindow spotApiWindow, SpotBillsType type) {
        PrivateGetTemplateClient client = SEARCH_BILLS_DETAILS.bind(environment, credentials);
        checkOkexApiWindow(spotApiWindow, client);
        if (type != null) {
            client.getUriComponentsBuilder().queryParam("type", type.getIntValue());
        }
        return client.getForObject(SpotBillsDetailsResponse[].class, coin.getSymbol());
    }

    @Override
    public SpotPlaceOrderResponse addOrder(Credentials credentials, SpotPlaceOrderRequest item) {
        PrivatePostTemplateClient client = ADD_ORDER.bind(environment, credentials);
        return client.postForObject(item, SpotPlaceOrderResponse.class);
    }

    @Override
    public SpotPlaceOrderMapResponse addOrderList(Credentials credentials, List<SpotPlaceOrderRequest> items) {
        Map<String, Integer> countMap = new HashMap<>();
        items.forEach(item -> {
            if (countMap.containsKey(item.getInstrumentId().getSymbol())) {
                countMap.put(item.getInstrumentId().getSymbol(), countMap.get(item.getInstrumentId().getSymbol()) + 1);
            } else {
                countMap.put(item.getInstrumentId().getSymbol(), 1);
            }
        });

        checkOrderListData(countMap);

        PrivatePostTemplateClient client = ADD_ORDER_LIST.bind(environment, credentials);
        return client.postForObject(items, SpotPlaceOrderMapResponse.class);
    }

    @Override
    public SpotPlaceOrderResponse cancelOrder(Credentials credentials, String orderId, String clientOid, SpotCancelOrdersRequest request) {
        PrivatePostTemplateClient client = CANCEL_ORDER.bind(environment, credentials);
        String id = checkOrderIdOrClientOid(orderId, clientOid);
        return client.postForObject(request, SpotPlaceOrderResponse.class, id);
    }

    @Override
    public SpotPlaceOrderMapResponse cancelOrderList(Credentials credentials, List<SpotCancelMultipleOrdersRequest> items) {
        Map<String, Integer> countMap = new HashMap<>();
        items.forEach(item -> {
            List<String> id;
            if ((item.getClientOids() == null || item.getClientOids().size() == 0) && (item.getOrderIds() != null && item.getOrderIds().size() > 0)) {
                id = item.getOrderIds();
            } else if ((item.getOrderIds() == null || item.getOrderIds().size() == 0) && (item.getClientOids() != null && item.getClientOids().size() > 0)) {
                id = item.getClientOids();
            } else {
                throw new IllegalArgumentException("订单ID和定制订单ID必须且只能选一个填写");
            }

            if (countMap.containsKey(item.getInstrumentId().getSymbol())) {
                countMap.put(item.getInstrumentId().getSymbol(), countMap.get(item.getInstrumentId().getSymbol()) + id.size());
            } else {
                countMap.put(item.getInstrumentId().getSymbol(), id.size());
            }
        });

        checkOrderListData(countMap);

        PrivatePostTemplateClient client = CANCEL_ORDER_LIST.bind(environment, credentials);
        return client.postForObject(items, SpotPlaceOrderMapResponse.class);
    }

    @Override
    public SpotModifyOrderResponse modifyOrder(Credentials credentials, CoinPair coinPair, SpotModifyOrderRequest request) {
        if (request.getNewPrice() == null && request.getNewSize() == null) {
            throw new IllegalArgumentException("新数量和新价格至少要传入一个。");
        }
        checkOrderIdOrClientOid(request.getOrderId(), request.getClientOid());

        PrivatePostTemplateClient client = MODIFY_ORDER.bind(environment, credentials);
        return client.postForObject(request, SpotModifyOrderResponse.class, coinPair.getSymbol());
    }

    @Override
    public SpotModifyOrderMapResponse multipleModifyOrder(Credentials credentials, List<SpotModifyMultipleOrderRequest> request) {

        if (request == null) {
            throw new IllegalArgumentException("参数不能为空,必填参数不能为空。");
        }
        if (request.size() > 10) {
            throw new IllegalArgumentException("最多可批量修改10个订单。");
        }
        request.forEach(item -> checkOrderIdOrClientOid(item.getOrderId(), item.getClientOid()));

        PrivatePostTemplateClient client = MULTIPLE_MODIFY_ORDER.bind(environment, credentials);
        return client.postForObject(request, SpotModifyOrderMapResponse.class);
    }

    @Override
    public SpotOrderInfoResponse[] searchOrderInfoList(Credentials credentials, CoinPair coinPair, SpotOrderInfoState state, SpotApiWindow spotApiWindow) {
        PrivateGetTemplateClient client = SEARCH_ORDER_INFO_LIST.bind(environment, credentials);
        client.getUriComponentsBuilder().queryParam("instrument_id", coinPair.getSymbol()).queryParam("state", state.getIntValue());
        checkOkexApiWindow(spotApiWindow, client);
        return client.getForObject(SpotOrderInfoResponse[].class);
    }

    @Override
    public SpotOrderInfoResponse[] searchOrdersPendingList(Credentials credentials, CoinPair coinPair, SpotApiWindow spotApiWindow) {
        PrivateGetTemplateClient client = SEARCH_ORDERS_PENDING_LIST.bind(environment, credentials);
        client.getUriComponentsBuilder().queryParam("instrument_id", coinPair.getSymbol());
        checkOkexApiWindow(spotApiWindow, client);

        return client.getForObject(SpotOrderInfoResponse[].class);

    }

    @Override
    public SpotOrderInfoResponse searchOrderInfo(Credentials credentials, CoinPair coinPair, String orderId, String clientOid) {
        PrivateGetTemplateClient client = SEARCH_ORDER_INFO.bind(environment, credentials);
        client.getUriComponentsBuilder().queryParam("instrument_id", coinPair.getSymbol());
        String id = checkOrderIdOrClientOid(orderId, clientOid);
        return client.getForObject(SpotOrderInfoResponse.class, id);
    }

    @Override
    public SpotTransactionDetailsResponse[] searchTransactionDetails(Credentials credentials, CoinPair coinPair, String orderId, SpotApiWindow spotApiWindow) {
        PrivateGetTemplateClient client = SEARCH_TRANSACTION_DETAILS.bind(environment, credentials);
        client.getUriComponentsBuilder().queryParam("instrument_id", coinPair.getSymbol());
        if (orderId != null && !orderId.equals("")) {
            client.getUriComponentsBuilder().queryParam("order_id", orderId);
        }
        checkOkexApiWindow(spotApiWindow, client);

        return client.getForObject(SpotTransactionDetailsResponse[].class);

    }

    @Override
    public SpotPlaceAlgoOrderResponse addEntrustOrder(Credentials credentials, SpotPlaceAlgoOrderRequest request) {
        PrivatePostTemplateClient client = ADD_ENTRUST_ORDER.bind(environment, credentials);
        return client.postForObject(request, SpotPlaceAlgoOrderResponse.class);
    }

    @Override
    public SpotCancelAlgoOrderResponse cancelEntrustOrder(Credentials credentials, SpotCancelAlgoOrderRequest request) {
        PrivatePostTemplateClient client = CANCEL_ENTRUST_ORDER.bind(environment, credentials);
        return client.postForObject(request, SpotCancelAlgoOrderResponse.class);
    }

    @Override
    public SpotAccountTradeFeeResponse searchTradeFee(Credentials credentials, SpotAccountTradeFeeCategory category, CoinPair coinPair) {
        if ((category == null && coinPair == null) || (category != null && coinPair != null)) {
            throw new IllegalArgumentException("手续费档位和币对仅选填写一个");
        }
        PrivateGetTemplateClient client = SEARCH_TRADE_FEE.bind(environment, credentials);
        if (coinPair != null) {
            client.getUriComponentsBuilder().queryParam("instrument_id", coinPair.getSymbol());
        }

        if (category != null) {
            client.getUriComponentsBuilder().queryParam("category", category.getIntValue());
        }
        return client.getForObject(SpotAccountTradeFeeResponse.class);
    }

    @Override
    public SpotAlgoOrderInfoMapResponse searchAlgoOrderInfoList(Credentials credentials, CoinPair coinPair, SpotAlgoOrderType orderType, SpotAlgoOrderStatus status, String algoIds, SpotApiWindow window) {
        boolean flag = true;
        if (status == null && (algoIds == null || algoIds.equals(""))) {
            flag = false;
        }
        if (status != null && (algoIds != null && !algoIds.equals(""))) {
            flag = false;
        }
        if (!flag) {
            throw new IllegalArgumentException("订单状态和委托单Id必填且只能填其一");
        }

        PrivateGetTemplateClient client = SEARCH_ALGO_ORDER_INFO_LIST.bind(environment, credentials);
        client.getUriComponentsBuilder().queryParam("instrument_id", coinPair.getSymbol());
        client.getUriComponentsBuilder().queryParam("order_type", orderType.getIntValue());
        if (status != null) {
            client.getUriComponentsBuilder().queryParam("status", status.getIntValue());
        }
        if (algoIds != null && !algoIds.equals("")) {
            client.getUriComponentsBuilder().queryParam("algo_ids", algoIds);
        }
        return client.getForObject(SpotAlgoOrderInfoMapResponse.class);
    }

    @Override
    public SpotMarketInfoResponse[] getMarketInfoList() {
        PublicGetTemplateClient client = SEARCH_COIN_PAIR_INFO.bind(environment);
        return client.getForObject(SpotMarketInfoResponse[].class);
    }

    @Override
    public SpotOrderBooksResponse orderBooks(int size, BigDecimal depth, CoinPair coinPair) {
        PublicGetTemplateClient client = ORDER_BOOKS.bind(environment);
        client.getUriComponentsBuilder().queryParam("size", size);
        client.getUriComponentsBuilder().queryParam("depth", depth);
        return client.getForObject(SpotOrderBooksResponse.class, coinPair.getSymbol());
    }

    @Override
    public SpotTickerResponse[] tickers() {
        PublicGetTemplateClient client = TICKERS.bind(environment);
        return client.getForObject(SpotTickerResponse[].class);

    }

    @Override
    public SpotTickerResponse tickerByCoinPair(CoinPair coinPair) {
        PublicGetTemplateClient client = TICKER_BY_COIN_PAIR.bind(environment);
        return client.getForObject(SpotTickerResponse.class, coinPair.getSymbol());
    }

    @Override
    public SpotFilledOrdersResponse[] trades(CoinPair coinPair, Integer limit) {
        PublicGetTemplateClient client = TRADES.bind(environment);
        if (limit != null && limit>0) {
            client.getUriComponentsBuilder().queryParam("limit", limit);
        }
        return client.getForObject(SpotFilledOrdersResponse[].class, coinPair.getSymbol());
    }

    @Override
    public List<SpotMarketDataResponse> candlesData(CoinPair coinPair, CandleInterval interval, LocalDateTime start, LocalDateTime end) {
        PublicGetTemplateClient client = CANDLES_DATA.bind(environment);
        return getOkexSpotMarketDataResponses(coinPair, interval, start, end, client);
    }

    @Override
    public List<SpotMarketDataResponse> historyCandlesData(CoinPair coinPair, CandleInterval interval, LocalDateTime start, LocalDateTime end) {
        PublicGetTemplateClient client = HISTORY_CANDLES_DATA.bind(environment);
        return getOkexSpotMarketDataResponses(coinPair, interval, start, end, client);
    }

    /**
     * 判断列表窗口是否为空
     *
     * @param spotApiWindow 窗口信息
     * @param client        请求模板
     */
    private void checkOkexApiWindow(SpotApiWindow spotApiWindow, AbstractTemplateClient client) {
        if (spotApiWindow != null) {
            if (spotApiWindow.getAfter() != null && !spotApiWindow.getAfter().equals("")) {
                client.getUriComponentsBuilder().queryParam("after", spotApiWindow.getAfter());
            }
            if (spotApiWindow.getBefore() != null && !spotApiWindow.getBefore().equals("")) {
                client.getUriComponentsBuilder().queryParam("before", spotApiWindow.getBefore());
            }
            if (spotApiWindow.getLimit() != null && !spotApiWindow.getLimit().equals("")) {
                client.getUriComponentsBuilder().queryParam("limit", spotApiWindow.getLimit());
            }
        }
    }

    /**
     * 检验orderId或者clientOid是个符合规则,规则如下:
     * 订单ID和定制订单ID必须且只能选一个填写
     *
     * @param orderId   订单ID
     * @param clientOid 定制订单ID
     */
    private String checkOrderIdOrClientOid(String orderId, String clientOid) {
        if ((orderId == null || orderId.isEmpty()) && (clientOid != null && !clientOid.equals(""))) {
            return clientOid;
        } else if ((clientOid == null || clientOid.isEmpty()) && (orderId != null && !orderId.equals(""))) {
            return orderId;
        } else {
            throw new IllegalArgumentException("订单ID和定制订单ID必须且只能选一个填写");
        }
    }

    /**
     * 检验币对,以及订单数量
     *
     * @param countMap 订单数量信息
     */
    private void checkOrderListData(Map<String, Integer> countMap) {
        if (countMap.size() > 4) {
            throw new IllegalArgumentException("每次只能下最多4个币对");
        }
        for (String key : countMap.keySet()) {
            Integer value = countMap.get(key);
            if (value > 10) {
                throw new IllegalArgumentException("每个币对可批量下10个单");
            }
        }
    }

    /**
     * 转换成2019-03-20T16:00:00.000Z格式的时间
     *
     * @param time 时间
     * @return yyyy-MM-ddTHH:mm:ssZ
     */
    public String getUtCTime(LocalDateTime time) {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(time, ZoneOffset.UTC);
        return zonedDateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    /**
     * 获取K线数据
     *
     * @param coinPair 币对
     * @param interval 时间周期
     * @param start    开始时间
     * @param end      结束时间
     * @param client   api请求
     * @return K线数据
     */
    private List<SpotMarketDataResponse> getOkexSpotMarketDataResponses(CoinPair coinPair, CandleInterval interval, LocalDateTime start, LocalDateTime end, PublicGetTemplateClient client) {
        client.getUriComponentsBuilder().queryParam("granularity", interval.getSeconds());
        if(start!=null) {
            client.getUriComponentsBuilder().queryParam("start", getUtCTime(start));
        }
        if(end!=null) {
            client.getUriComponentsBuilder().queryParam("end", getUtCTime(end));
        }
        Object[][] responses = client.getForObject(Object[][].class, coinPair.getSymbol());
        List<SpotMarketDataResponse> items = new ArrayList<>();
        if (responses != null) {
            for (Object[] data : responses) {
                SpotMarketDataResponse item = new SpotMarketDataResponse();
                item.setTime(LocalDateTime.parse(data[0].toString(), DateTimeFormatter.ISO_ZONED_DATE_TIME));
                item.setOpen(new BigDecimal(data[1].toString()));
                item.setHigh(new BigDecimal(data[2].toString()));
                item.setLow(new BigDecimal(data[3].toString()));
                item.setClose(new BigDecimal(data[4].toString()));
                item.setVolume(new BigDecimal(data[5].toString()));
                items.add(item);
            }
        }
        return items;
    }
}
