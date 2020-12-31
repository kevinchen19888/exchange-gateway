package com.kevin.gateway.okexapi.margin;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.rest.*;
import com.kevin.gateway.okexapi.base.rest.impl.OkexAbstractImpl;
import com.kevin.gateway.okexapi.base.util.OkexPagination;
import com.kevin.gateway.okexapi.margin.model.MarginBillsType;
import com.kevin.gateway.okexapi.margin.model.MarginLoanStatus;
import com.kevin.gateway.okexapi.margin.model.MarginOrderInfoState;
import com.kevin.gateway.okexapi.margin.request.*;
import com.kevin.gateway.okexapi.margin.response.*;
import com.kevin.gateway.okexapi.spot.request.SpotCancelOrdersRequest;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarginApiIml extends OkexAbstractImpl implements MarginApi {

    public MarginApiIml(OkexEnvironment environment) {
        super(environment);
    }

    private static final PrivateGetTemplate SEARCH_MARGIN_ACCOUNTS = PrivateGetTemplate.
            of("/api/margin/v3/accounts", 20, Duration.ofSeconds(2L));

    @Override
    public MarginAccountsResponse[] searchMarginAccounts(Credentials credentials) {
        PrivateGetTemplateClient client = SEARCH_MARGIN_ACCOUNTS.bind(environment, credentials);
        return client.getForObject(MarginAccountsResponse[].class);
    }

    private static final PrivateGetTemplate SEARCH_MARGIN_ACCOUNT = PrivateGetTemplate.
            of("/api/margin/v3/accounts/{instrument_id}", 20, Duration.ofSeconds(2L));

    @Override
    public MarginAccountsResponse searchMarginAccount(Credentials credentials, MarginMarketId marketId) {
        PrivateGetTemplateClient client = SEARCH_MARGIN_ACCOUNT.bind(environment, credentials);
        return client.getForObject(MarginAccountsResponse.class, marketId.getSymbol());
    }

    private static final PrivateGetTemplate SEARCH_BILLS_DETAILS = PrivateGetTemplate.
            of("/api/margin/v3/accounts/{instrument_id}/ledger", 10, Duration.ofSeconds(2L));

    @Override
    public MarginBillsDetailsResponse[] searchBillsDetails(Credentials credentials, MarginMarketId marketId, OkexPagination pagination, MarginBillsType type) {
        PrivateGetTemplateClient client = SEARCH_BILLS_DETAILS.bind(environment, credentials);
        checkOkexApiWindow(pagination, client);
        if (type != null) {
            client.getUriComponentsBuilder().queryParam("type", type.getIntValue());
        }
        return client.getForObject(MarginBillsDetailsResponse[].class, marketId.getSymbol());
    }

    private static final PrivateGetTemplate SEARCH_MARGIN_ACCOUNT_SETTINGS = PrivateGetTemplate.
            of("/api/margin/v3/accounts/availability", 20, Duration.ofSeconds(2L));

    @Override
    public MarginAccountSettingsResponse[] searchMarginAccountSettings(Credentials credentials) {
        PrivateGetTemplateClient client = SEARCH_MARGIN_ACCOUNT_SETTINGS.bind(environment, credentials);
        return client.getForObject(MarginAccountSettingsResponse[].class);
    }

    private static final PrivateGetTemplate SEARCH_MARGIN_ACCOUNT_SETTING = PrivateGetTemplate.
            of("/api/margin/v3/accounts/{instrument_id}/availability", 20, Duration.ofSeconds(2L));

    @Override
    public MarginAccountSettingsResponse[] searchMarginAccountSetting(Credentials credentials, MarginMarketId marketId) {
        PrivateGetTemplateClient client = SEARCH_MARGIN_ACCOUNT_SETTING.bind(environment, credentials);
        return client.getForObject(MarginAccountSettingsResponse[].class, marketId.getSymbol());
    }

    private static final PrivateGetTemplate SEARCH_LOAN_HISTORY = PrivateGetTemplate.
            of("/api/margin/v3/accounts/borrowed", 20, Duration.ofSeconds(2L));

    @Override
    public MarginGetLoanHistoryResponse[] searchLoanHistory(Credentials credentials, OkexPagination pagination, MarginLoanStatus status) {
        PrivateGetTemplateClient client = SEARCH_LOAN_HISTORY.bind(environment, credentials);
        checkOkexApiWindow(pagination, client);
        if (status != null) {
            client.getUriComponentsBuilder().queryParam("status", status.getIntValue());
        }
        return client.getForObject(MarginGetLoanHistoryResponse[].class);
    }

    private static final PrivateGetTemplate SEARCH_LOAN_HISTORY_BY_MARKET_ID = PrivateGetTemplate.
            of("/api/margin/v3/accounts/{instrument_id}/borrowed", 20, Duration.ofSeconds(2L));

    @Override
    public MarginGetLoanHistoryResponse[] searchLoanHistory(Credentials credentials, MarginMarketId marketId, OkexPagination pagination, MarginLoanStatus status) {
        PrivateGetTemplateClient client = SEARCH_LOAN_HISTORY_BY_MARKET_ID.bind(environment, credentials);
        checkOkexApiWindow(pagination, client);
        if (status != null) {
            client.getUriComponentsBuilder().queryParam("status", status.getIntValue());
        }
        return client.getForObject(MarginGetLoanHistoryResponse[].class, marketId.getSymbol());
    }

    private static final PrivatePostTemplate LOAN = PrivatePostTemplate.
            of("/api/margin/v3/accounts/borrow", 100, Duration.ofSeconds(2L));

    @Override
    public MarginLoanResponse loan(Credentials credentials, MarginLoanRequest request) {
        PrivatePostTemplateClient client = LOAN.bind(environment, credentials);
        return client.postForObject(request, MarginLoanResponse.class);
    }

    private static final PrivatePostTemplate REPAYMENT = PrivatePostTemplate.
            of("/api/margin/v3/accounts/repayment", 100, Duration.ofSeconds(2L));

    @Override
    public MarginRepaymentResponse repayment(Credentials credentials, MarginRepaymentRequest request) {
        PrivatePostTemplateClient client = REPAYMENT.bind(environment, credentials);
        return client.postForObject(request, MarginRepaymentResponse.class);
    }

    private static final PrivatePostTemplate PLACE_ORDER = PrivatePostTemplate.
            of("/api/margin/v3/orders", 100, Duration.ofSeconds(2L));

    @Override
    public MarginPlaceOrderResponse placeOrder(Credentials credentials, MarginPlaceOrderRequest request) {
        PrivatePostTemplateClient client = PLACE_ORDER.bind(environment, credentials);
        return client.postForObject(request, MarginPlaceOrderResponse.class);
    }

    private static final PrivatePostTemplate PLACE_ORDER_list = PrivatePostTemplate.
            of("/api/margin/v3/batch_orders", 50, Duration.ofSeconds(2L));

    @Override
    public MarginPlaceOrderMapResponse addOrderList(Credentials credentials, List<MarginPlaceOrderRequest> items) {
        Map<String, Integer> countMap = new HashMap<>();
        items.forEach(item -> {
            if (countMap.containsKey(item.getInstrumentId().getSymbol())) {
                countMap.put(item.getInstrumentId().getSymbol(), countMap.get(item.getInstrumentId().getSymbol()) + 1);
            } else {
                countMap.put(item.getInstrumentId().getSymbol(), 1);
            }
        });
        checkOrderListData(countMap);

        PrivatePostTemplateClient client = PLACE_ORDER_list.bind(environment, credentials);
        return client.postForObject(items, MarginPlaceOrderMapResponse.class);
    }

    private static final PrivatePostTemplate CANCEL_ORDER = PrivatePostTemplate.
            of("/api/margin/v3/cancel_orders/{order_id}", 100, Duration.ofSeconds(2L));

    @Override
    public MarginPlaceOrderResponse cancelOrder(Credentials credentials, String orderId, String clientOid, MarginMarketId marketId) {
        SpotCancelOrdersRequest request = new SpotCancelOrdersRequest();
        request.setInstrumentId(marketId.getCoinPair());

        PrivatePostTemplateClient client = CANCEL_ORDER.bind(environment, credentials);
        String id = checkOrderIdOrClientOid(orderId, clientOid);
        return client.postForObject(request, MarginPlaceOrderResponse.class, id);
    }

    private static final PrivatePostTemplate CANCEL_ORDER_list = PrivatePostTemplate.
            of("/api/margin/v3/cancel_batch_orders", 50, Duration.ofSeconds(2L));

    @Override
    public MarginPlaceOrderMapResponse cancelOrderList(Credentials credentials, List<MarginCancelOrderListRequest> items) {
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

        PrivatePostTemplateClient client = CANCEL_ORDER_list.bind(environment, credentials);
        return client.postForObject(items, MarginPlaceOrderMapResponse.class);
    }

    private static final PrivateGetTemplate SEARCH_ORDER_LIST = PrivateGetTemplate.
            of("/api/margin/v3/orders", 10, Duration.ofSeconds(2L));

    @Override
    public MarginOrderInfoResponse[] searchOrderList(Credentials credentials, MarginMarketId marketId, OkexPagination pagination, MarginOrderInfoState state) {
        PrivateGetTemplateClient client = SEARCH_ORDER_LIST.bind(environment, credentials);
        client.getUriComponentsBuilder().queryParam("instrument_id", marketId.getSymbol()).queryParam("state", state.getIntValue());
        checkOkexApiWindow(pagination, client);
        return client.getForObject(MarginOrderInfoResponse[].class);
    }

    private static final PrivateGetTemplate SEARCH_ORDER_INFO = PrivateGetTemplate.
            of("/api/margin/v3/orders/{order_id}", 20, Duration.ofSeconds(2L));

    @Override
    public MarginOrderInfoResponse searchOrderInfo(Credentials credentials, String orderId, String clientOid, MarginMarketId marketId) {
        PrivateGetTemplateClient client = SEARCH_ORDER_INFO.bind(environment, credentials);
        String id = checkOrderIdOrClientOid(orderId, clientOid);
        client.getUriComponentsBuilder().queryParam("instrument_id", marketId.getSymbol());
        return client.getForObject(MarginOrderInfoResponse.class, id);
    }

    private static final PrivateGetTemplate SEARCH_ORDERS_PENDING_LIST = PrivateGetTemplate.
            of("/api/margin/v3/orders_pending", 20, Duration.ofSeconds(2L));

    @Override
    public MarginOrderInfoResponse[] searchOrdersPendingList(Credentials credentials, MarginMarketId marketId, OkexPagination pagination) {
        PrivateGetTemplateClient client = SEARCH_ORDERS_PENDING_LIST.bind(environment, credentials);
        client.getUriComponentsBuilder().queryParam("instrument_id", marketId.getSymbol());
        checkOkexApiWindow(pagination, client);
        return client.getForObject(MarginOrderInfoResponse[].class);
    }

    private static final PrivateGetTemplate SEARCH_TRANSACTION_DETAILS = PrivateGetTemplate.
            of("/api/margin/v3/fills", 10, Duration.ofSeconds(2L));

    @Override
    public MarginTransactionDetailsResponse[] searchTransactionDetails(Credentials credentials, MarginMarketId marketId, String orderId, OkexPagination pagination) {
        PrivateGetTemplateClient client = SEARCH_TRANSACTION_DETAILS.bind(environment, credentials);
        client.getUriComponentsBuilder().queryParam("instrument_id", marketId.getSymbol());
        checkOkexApiWindow(pagination, client);
        return client.getForObject(MarginTransactionDetailsResponse[].class);
    }

    private static final PrivateGetTemplate SEARCH_LEVERAGE = PrivateGetTemplate.
            of("/api/margin/v3/accounts/{instrument_id}/leverage", 100, Duration.ofSeconds(2L));

    @Override
    public MarginLeverageResponse searchLeverage(Credentials credentials, MarginMarketId marketId) {
        PrivateGetTemplateClient client = SEARCH_LEVERAGE.bind(environment, credentials);
        return client.getForObject(MarginLeverageResponse.class, marketId.getSymbol());
    }

    private static final PrivatePostTemplate SET_LEVERAGE = PrivatePostTemplate.
            of("/api/margin/v3/accounts/{instrument_id}/leverage", 100, Duration.ofSeconds(2L));

    @Override
    public MarginLeverageResponse setLeverage(Credentials credentials, BigDecimal leverage, MarginMarketId marketId) {
        PrivatePostTemplateClient client = SET_LEVERAGE.bind(environment, credentials);
        MarginLeverageRequest request = new MarginLeverageRequest();
        request.setLeverage(leverage);
        return client.postForObject(request, MarginLeverageResponse.class, marketId.getSymbol());
    }

    private static final PublicGetTemplate SEARCH_MARK_PRICE = PublicGetTemplate.
            of("/api/margin/v3/instruments/{instrument_id}/mark_price", 20, Duration.ofSeconds(2L));

    @Override
    public MarginMarkPriceResponse searchMarkPrice(MarginMarketId marketId) {
        PublicGetTemplateClient client = SEARCH_MARK_PRICE.bind(environment);
        return client.getForObject(MarginMarkPriceResponse.class, marketId.getSymbol());
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
     * 判断列表窗口是否为空
     *
     * @param spotApiWindow 窗口信息
     * @param client        请求模板
     */
    private void checkOkexApiWindow(OkexPagination spotApiWindow, AbstractTemplateClient client) {
        if (spotApiWindow != null) {
            if (spotApiWindow.getAfter() != null) {
                client.getUriComponentsBuilder().queryParam("after", spotApiWindow.getAfter());
            }
            if (spotApiWindow.getBefore() != null) {
                client.getUriComponentsBuilder().queryParam("before", spotApiWindow.getBefore());
            }
            if (spotApiWindow.getLimit() != null) {
                client.getUriComponentsBuilder().queryParam("limit", spotApiWindow.getLimit());
            }
        }
    }
}
