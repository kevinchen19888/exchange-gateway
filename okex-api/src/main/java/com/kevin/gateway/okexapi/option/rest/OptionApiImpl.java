package com.kevin.gateway.okexapi.option.rest;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.rest.*;
import com.kevin.gateway.okexapi.base.rest.impl.OkexAbstractImpl;
import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.base.util.OkexFeeCategory;
import com.kevin.gateway.okexapi.base.util.OkexPagination;
import com.kevin.gateway.okexapi.option.OptionMarketId;
import com.kevin.gateway.okexapi.option.domain.*;
import com.kevin.gateway.okexapi.option.util.OptionOrderStatus;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OptionApiImpl extends OkexAbstractImpl implements OptionApi {
    private static final PrivateGetTemplate GET_POSITION = PrivateGetTemplate.of("/api/option/v3/{underlying}/position",
            20L, Duration.ofSeconds(2));
    private static final PrivateGetTemplate GET_ACCOUNT = PrivateGetTemplate.of("/api/option/v3/accounts/{underlying}",
            20L, Duration.ofSeconds(2));
    private static final PrivateGetTemplate GET_ORDER_STATUS = PrivateGetTemplate.of("/api/option/v3/orders/{underlying}/{orderIdOrClientOid}",
            40L, Duration.ofSeconds(2));
    private static final PrivateGetTemplate GET_ORDERS = PrivateGetTemplate.of("/api/option/v3/orders/{underlying}",
            10L, Duration.ofSeconds(2));
    private static final PrivateGetTemplate GET_ORDERS_FILL_DETAILS = PrivateGetTemplate.of("/api/option/v3/fills/{underlying}",
            10L, Duration.ofSeconds(2));
    private static final PrivateGetTemplate GET_LEDGER_INFOS = PrivateGetTemplate.of("/api/option/v3/accounts/{underlying}/ledger",
            5L, Duration.ofSeconds(2));
    private static final PrivateGetTemplate GET_FEE_RATE = PrivateGetTemplate.of("/api/option/v3/trade_fee",
            20L, Duration.ofSeconds(2));

    private static final PublicGetTemplate GET_UNDERLYING = PublicGetTemplate.of("/api/option/v3/underlying", 20L, Duration.ofSeconds(2));
    private static final PublicGetTemplate GET_INSTRUMENTS = PublicGetTemplate.of("/api/option/v3/instruments/{underlying}", 20L, Duration.ofSeconds(2));
    private static final PublicGetTemplate GET_MARKET_DATAS = PublicGetTemplate.of("/api/option/v3/instruments/{underlying}/summary", 20L, Duration.ofSeconds(2));
    private static final PublicGetTemplate GET_MARKET_DATA = PublicGetTemplate.of("/api/option/v3/instruments/{underlying}/summary/{instrumentId}", 20L, Duration.ofSeconds(2));
    private static final PublicGetTemplate GET_ORDER_BOOK = PublicGetTemplate.of("/api/option/v3/instruments/{instrumentId}/book", 20L, Duration.ofSeconds(2));
    private static final PublicGetTemplate GET_FILLED_ORDERS = PublicGetTemplate.of("/api/option/v3/instruments/{instrumentId}/trades", 20L, Duration.ofSeconds(2));
    private static final PublicGetTemplate GET_TICKER = PublicGetTemplate.of("/api/option/v3/instruments/{instrumentId}/ticker", 20L, Duration.ofSeconds(2));
    private static final PublicGetTemplate GET_CANDLESTICKS = PublicGetTemplate.of("/api/option/v3/instruments/{instrumentId}/candles", 20L, Duration.ofSeconds(2));
    private static final PublicGetTemplate GET_SETTLEMENT_AND_EXERCISE_HISTORIES = PublicGetTemplate
            .of("/api/option/v3/settlement/history/{underlying}", 1L, Duration.ofSeconds(10));

    private static final PrivatePostTemplate ORDER = PrivatePostTemplate.of("/api/option/v3/order",
            20L, Duration.ofSeconds(1));
    private static final PrivatePostTemplate BATCH_ORDER = PrivatePostTemplate.of("/api/option/v3/orders",
            20L, Duration.ofSeconds(2));
    private static final PrivatePostTemplate CANCEL_ORDER = PrivatePostTemplate.of("/api/option/v3/cancel_order/{underlying}/{orderIdOrClientOid}",
            20L, Duration.ofSeconds(1));
    private static final PrivatePostTemplate BATCH_CANCEL_ORDER = PrivatePostTemplate.of("/api/option/v3/cancel_batch_orders/{underlying}",
            20L, Duration.ofSeconds(2));
    private static final PrivatePostTemplate AMEND_ORDER = PrivatePostTemplate.of("/api/option/v3/amend_order/{underlying}",
            20L, Duration.ofSeconds(1));
    private static final PrivatePostTemplate BATCH_AMEND_ORDER = PrivatePostTemplate.of("/api/option/v3/amend_batch_orders/{underlying}",
            20L, Duration.ofSeconds(2));

    private final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public OptionApiImpl(OkexEnvironment environment) {
        super(environment);
    }

    @Override
    public OptionPositionResponse getPosition(Credentials credentials, CoinPair underlying, OptionMarketId instrumentId) {
        PrivateGetTemplateClient client = GET_POSITION.bind(environment, credentials);
        if (instrumentId != null) {
            client.getUriComponentsBuilder().queryParam("instrument_id", instrumentId.getSymbol());
        }
        return client.getForObject(OptionPositionResponse.class, underlying.getSymbol());
    }

    @Override
    public OptionAccountsResponse getAccount(Credentials credentials, CoinPair underlying) {
        PrivateGetTemplateClient client = GET_ACCOUNT.bind(environment, credentials);
        return client.getForObject(OptionAccountsResponse.class, underlying.getSymbol());
    }

    @Override
    public OptionOrderResponse order(Credentials credentials, OptionOrderRequest req) {
        PrivatePostTemplateClient client = ORDER.bind(environment, credentials);
        return client.postForObject(req, OptionOrderResponse.class);
    }

    @Override
    public OptionOrderCancelResponse cancelOrder(Credentials credentials, OptionCancelOrderRequest req) {
        PrivatePostTemplateClient client = CANCEL_ORDER.bind(environment, credentials);
        if (req.getOrderId() != null) {
            return client.postForObject(null, OptionOrderCancelResponse.class, req.getUnderlying().getSymbol(), req.getOrderId());
        }
        if (req.getClientOid() != null) {
            return client.postForObject(null, OptionOrderCancelResponse.class, req.getUnderlying().getSymbol(), req.getClientOid());
        }
        throw new IllegalArgumentException(String.format("orderId 与 clientOid 必须且只能传一个,实际传值orderId:%s,clientOid:%s",
                req.getOrderId(), req.getClientOid()));
    }

    @Override
    public OptionAmendOrderResponse amendOrder(Credentials credentials, OptionAmendOrderRequest req) {
        PrivatePostTemplateClient client = AMEND_ORDER.bind(environment, credentials);
        return client.postForObject(req, OptionAmendOrderResponse.class, req.getUnderlying().getSymbol());
    }

    @Override
    public OptionBatchOrderResponse batchOrder(Credentials credentials, OptionBatchOrderRequest req) {
        PrivatePostTemplateClient client = BATCH_ORDER.bind(environment, credentials);
        return client.postForObject(req, OptionBatchOrderResponse.class);
    }

    @Override
    public OptionBatchOrderCancelResponse batchCancelOrder(Credentials credentials, OptionBatchCancelOrderRequest req) {
        PrivatePostTemplateClient client = BATCH_CANCEL_ORDER.bind(environment, credentials);
        if (!CollectionUtils.isEmpty(req.getOrderIds())) {
            return client.postForObject(req.getOrderIds(), OptionBatchOrderCancelResponse.class, req.getUnderlying().getSymbol());
        }
        if (!CollectionUtils.isEmpty(req.getClientOids())) {
            return client.postForObject(req.getClientOids(), OptionBatchOrderCancelResponse.class, req.getUnderlying().getSymbol());
        }
        throw new IllegalArgumentException("批量撤单参数 orderIds 与 clientOids 有且只能传一个");
    }

    @Override
    public OptionBatchAmendOrderResponse batchAmendOrder(Credentials credentials, OptionBatchAmendOrderRequest req) {
        PrivatePostTemplateClient client = BATCH_AMEND_ORDER.bind(environment, credentials);
        return client.postForObject(req.getAmendData(), OptionBatchAmendOrderResponse.class, req.getUnderlying().getSymbol());
    }

    @Override
    public OptionOrdersResponse getOrderStatus(Credentials credentials, CoinPair underlying,
                                               String orderId, String clientOid) {
        PrivateGetTemplateClient client = GET_ORDER_STATUS.bind(environment, credentials);
        if (StringUtils.hasText(orderId)) {
            return client.getForObject(OptionOrdersResponse.class, underlying.getSymbol(), orderId);
        }
        if (StringUtils.hasText(clientOid)) {
            return client.getForObject(OptionOrdersResponse.class, underlying.getSymbol(), clientOid);
        }
        throw new IllegalArgumentException("orderId与clientOid有且只能传一个");
    }

    @Override
    @SneakyThrows
    public OptionOrderListResponse getOrders(Credentials credentials, CoinPair underlying,
                                             OptionMarketId instrumentId, OkexPagination pagination, OptionOrderStatus state) {
        PrivateGetTemplateClient client = GET_ORDERS.bind(environment, credentials);
        if (instrumentId != null) {
            client.getUriComponentsBuilder().queryParam("instrument_id", instrumentId.getSymbol());
        }

        addPaginationParams(pagination, client);

        client.getUriComponentsBuilder().queryParam("state", state.getValue());

        return client.getForObject(OptionOrderListResponse.class, underlying.getSymbol());
    }

    @Override
    @SneakyThrows
    public OptionFillDetailListResponse getOrdersFillDetails(Credentials credentials, CoinPair underlying,
                                                             String orderId, OptionMarketId instrumentId, OkexPagination pagination) {
        PrivateGetTemplateClient client = GET_ORDERS_FILL_DETAILS.bind(environment, credentials);

        if (StringUtils.hasText(orderId)) {
            client.getUriComponentsBuilder().queryParam("order_id", orderId);
        }
        if (instrumentId != null) {
            client.getUriComponentsBuilder().queryParam("instrument_id", instrumentId.getSymbol());
        }

        addPaginationParams(pagination, client);

        OptionFillDetailListResponse resp = new OptionFillDetailListResponse();
        JsonNode respNode = client.getForObject(JsonNode.class, underlying.getSymbol());

        assert respNode != null;
        if (assertErrorInfo(resp, respNode))
            return resp;
        resp.setFillDetails(this.environment.getObjectMapper().treeToValue(respNode, OptionFillDetailResponse[].class));

        return resp;
    }

    @Override
    @SneakyThrows
    public OptionLedgerListResponse getLedgers(Credentials credentials, CoinPair underlying, OkexPagination pagination) {
        PrivateGetTemplateClient client = GET_LEDGER_INFOS.bind(environment, credentials);

        addPaginationParams(pagination, client);

        JsonNode respNode = client.getForObject(JsonNode.class, underlying.getSymbol());
        OptionLedgerListResponse resp = new OptionLedgerListResponse();
        assert respNode != null;
        if (assertErrorInfo(resp, respNode)) {
            return resp;
        }
        resp.setLedgers(this.environment.getObjectMapper().treeToValue(respNode, OptionLedgerResponse[].class));
        return resp;
    }

    @Override
    public OptionFeeRateResponse getFeeRate(Credentials credentials, OkexFeeCategory category, CoinPair underlying) {
        PrivateGetTemplateClient client = GET_FEE_RATE.bind(environment, credentials);
        if (category != null) {
            client.getUriComponentsBuilder().queryParam("category", category.getVal());
        }
        if (underlying != null) {
            client.getUriComponentsBuilder().queryParam("underlying", underlying.getSymbol());
        }
        if (category == null && underlying == null) {
            throw new IllegalArgumentException("category与underlying不能同时为null");
        }
        return client.getForObject(OptionFeeRateResponse.class);
    }

    @Override
    @SneakyThrows
    public UnderlyingListResponse getUnderlying() {
        PublicGetTemplateClient client = GET_UNDERLYING.bind(environment);

        JsonNode respNode = client.getForObject(JsonNode.class);

        UnderlyingListResponse resp = new UnderlyingListResponse();
        assert respNode != null;
        if (assertErrorInfo(resp, respNode)) {
            return resp;
        }
        resp.setUnderlings(this.environment.getObjectMapper().treeToValue(respNode, CoinPair[].class));
        return resp;
    }

    @Override
    @SneakyThrows
    public OptionInstrumentListResponse getInstruments(CoinPair underlying, LocalDate delivery,
                                                       OptionMarketId instrumentId) {
        PublicGetTemplateClient client = GET_INSTRUMENTS.bind(environment);
        if (delivery != null) {
            client.getUriComponentsBuilder().queryParam("delivery", delivery.format(DateTimeFormatter.BASIC_ISO_DATE).substring(2));
        }
        if (instrumentId != null) {
            client.getUriComponentsBuilder().queryParam("instrument_id", instrumentId.getSymbol());
        }
        JsonNode treeNode = client.getForObject(JsonNode.class, underlying.getSymbol());

        OptionInstrumentListResponse resp = new OptionInstrumentListResponse();
        assert treeNode != null;
        if (assertErrorInfo(resp, treeNode)) {
            return resp;
        }
        resp.setInstruments(this.environment.getObjectMapper().treeToValue(treeNode, OptionInstrumentResponse[].class));
        return resp;
    }

    @Override
    @SneakyThrows
    public OptionMarketDataListResponse getMarketDatas(CoinPair underlying, LocalDate delivery) {
        PublicGetTemplateClient client = GET_MARKET_DATAS.bind(environment);
        if (delivery != null) {
            client.getUriComponentsBuilder().queryParam("delivery", delivery.format(DateTimeFormatter.BASIC_ISO_DATE).substring(2));
        }
        JsonNode treeNode = client.getForObject(JsonNode.class, underlying.getSymbol());

        OptionMarketDataListResponse resp = new OptionMarketDataListResponse();
        assert treeNode != null;
        if (assertErrorInfo(resp, treeNode)) {
            return resp;
        }
        resp.setMarketDatas(this.environment.getObjectMapper().treeToValue(treeNode, OptionMarketDataResponse[].class));
        return resp;
    }

    @Override
    public OptionMarketDataResponse getMarketData(CoinPair underlying, OptionMarketId instrumentId) {
        PublicGetTemplateClient client = GET_MARKET_DATA.bind(environment);
        return client.getForObject(OptionMarketDataResponse.class, underlying.getSymbol(), instrumentId.getSymbol());
    }

    @Override
    public OptionOrderBookResponse getOrderBook(OptionMarketId instrumentId, Integer size) {
        PublicGetTemplateClient client = GET_ORDER_BOOK.bind(environment);
        if (size != null) {
            client.getUriComponentsBuilder().queryParam("size", size);
        }
        return client.getForObject(OptionOrderBookResponse.class, instrumentId.getSymbol());
    }

    @Override
    @SneakyThrows
    public OptionFilledOrderListResponse getFilledOrders(OptionMarketId instrumentId, OkexPagination pagination) {
        PublicGetTemplateClient client = GET_FILLED_ORDERS.bind(environment);

        addPaginationParams(pagination, client);

        JsonNode treeNode = client.getForObject(JsonNode.class, instrumentId.getSymbol());

        OptionFilledOrderListResponse resp = new OptionFilledOrderListResponse();
        assert treeNode != null;
        if (assertErrorInfo(resp, treeNode)) {
            return resp;
        }
        resp.setFilledOrders(this.environment.getObjectMapper().treeToValue(treeNode, OptionFilledOrderResponse[].class));
        return resp;
    }

    @Override
    public OptionTickerResponse getTicker(OptionMarketId instrumentId) {
        PublicGetTemplateClient client = GET_TICKER.bind(environment);
        return client.getForObject(OptionTickerResponse.class, instrumentId.getSymbol());
    }

    @SneakyThrows
    @Override
    public CandlestickListResponse getCandlesticks(OptionMarketId instrumentId,
                                                   LocalDateTime start, LocalDateTime end, CandleInterval granularity) {
        PublicGetTemplateClient client = GET_CANDLESTICKS.bind(environment);

        if (start != null) {
            client.getUriComponentsBuilder().queryParam("start", start.format(TIME_FORMATTER));
        }
        if (end != null) {
            client.getUriComponentsBuilder().queryParam("end", end.format(TIME_FORMATTER));
        }
        if (granularity != null) {
            client.getUriComponentsBuilder().queryParam("granularity", granularity.getSeconds());//.getVal());
        }

        JsonNode treeNode = client.getForObject(JsonNode.class, instrumentId.getSymbol());

        CandlestickListResponse resp = new CandlestickListResponse();
        assert treeNode != null;
        if (assertErrorInfo(resp, treeNode)) {
            return resp;
        }
        resp.setCandlesticks(this.environment.getObjectMapper().treeToValue(treeNode, CandlestickResponse[].class));
        return resp;
    }

    @SneakyThrows
    @Override
    public SettlementAndExerciseHistoryListResponse getSettlementAndExerciseHistories(CoinPair instrumentId,
                                                                                      LocalDateTime start, Integer limit, LocalDateTime end) {
        PublicGetTemplateClient client = GET_SETTLEMENT_AND_EXERCISE_HISTORIES.bind(environment);
        if (start != null) {
            client.getUriComponentsBuilder().queryParam("start", start.format(TIME_FORMATTER));
        }
        if (end != null) {
            client.getUriComponentsBuilder().queryParam("end", end.format(TIME_FORMATTER));
        }
        if (limit != null) {
            client.getUriComponentsBuilder().queryParam("limit", limit);
        }

        JsonNode treeNode = client.getForObject(JsonNode.class, instrumentId.getSymbol());

        SettlementAndExerciseHistoryListResponse resp = new SettlementAndExerciseHistoryListResponse();
        assert treeNode != null;
        if (assertErrorInfo(resp, treeNode)) {
            return resp;
        }
        resp.setHistories(this.environment.getObjectMapper().treeToValue(treeNode, SettlementAndExerciseHistoryResponse[].class));
        return resp;
    }

    private void addPaginationParams(OkexPagination pagination, AbstractTemplateClient client) {
        if (pagination != null) {
            if (pagination.getAfter() != null) {
                client.getUriComponentsBuilder().queryParam("after", pagination.getAfter());
            }
            if (pagination.getBefore() != null) {
                client.getUriComponentsBuilder().queryParam("before", pagination.getBefore());
            }
            if (pagination.getLimit() != null) {
                client.getUriComponentsBuilder().queryParam("limit", pagination.getLimit());
            }
        }
    }

    private <T extends OptionErrorResponse> boolean assertErrorInfo(T resp, JsonNode treeNode) {
        if (treeNode.has("error_message") || treeNode.has("error_code")) {
            resp.setErrorCode(treeNode.get("error_code").asText());
            resp.setErrorMessage(treeNode.get("error_message").asText());
            return true;
        }
        return false;
    }
}
