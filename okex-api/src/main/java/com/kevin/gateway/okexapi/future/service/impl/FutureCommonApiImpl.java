package com.kevin.gateway.okexapi.future.service.impl;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.rest.PublicGetTemplate;
import com.kevin.gateway.okexapi.base.rest.PublicGetTemplateClient;
import com.kevin.gateway.okexapi.base.rest.impl.OkexAbstractImpl;
import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.common.model.*;
import com.kevin.gateway.okexapi.future.service.FutureCommonApi;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FutureCommonApiImpl extends OkexAbstractImpl implements FutureCommonApi {


    private static final PublicGetTemplate GET_FUTURE_CONTRACT_INFO = PublicGetTemplate
            .of("/api/futures/v3/instruments", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_FUTURE_ORDER_BOOK = PublicGetTemplate
            .of("/api/futures/v3/instruments/{instrument_id}/book", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_FUTURE_ALL_TICKER = PublicGetTemplate
            .of("/api/futures/v3/instruments/ticker", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_FUTURE_TICKER_BY_ID = PublicGetTemplate
            .of("/api/futures/v3/instruments/{instrument_id}/ticker", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_FUTURE_NEW_DEAL = PublicGetTemplate
            .of("/api/futures/v3/instruments/{instrument_id}/trades", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_FUTURE_CANDLES = PublicGetTemplate
            .of("/api/futures/v3/instruments/{instrument_id}/candles", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_FUTURE_INDEX = PublicGetTemplate
            .of("/api/futures/v3/instruments/{instrument_id}/index", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_FUTURE_FIAT_RATE = PublicGetTemplate
            .of("/api/futures/v3/rate", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_FUTURE_ESTIMATE_DELIVERY_PRICE = PublicGetTemplate
            .of("/api/futures/v3/instruments/{instrument_id}/estimated_price", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_FUTURE_PLATFORM_HOLDS = PublicGetTemplate
            .of("/api/futures/v3/instruments/{instrument_id}/open_interest", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_FUTURE_PRICE_LIMIT = PublicGetTemplate
            .of("/api/futures/v3/instruments/{instrument_id}/price_limit", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_FUTURE_MARK_PRICE = PublicGetTemplate
            .of("/api/futures/v3/instruments/{instrument_id}/mark_price", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_FUTURE_LIQUIDATION_PRICE = PublicGetTemplate
            .of("/api/futures/v3/instruments/{instrument_id}/liquidation", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_FUTURE_SETTLEMENT_HISTORY = PublicGetTemplate
            .of("/api/futures/v3/settlement/history", 1, Duration.ofSeconds(60L));
    private static final PublicGetTemplate GET_FUTURE_HISTORY_CANDLES = PublicGetTemplate
            .of("/api/futures/v3/instruments/{instrument_id}/history/candles", 5, Duration.ofSeconds(2L));


    public FutureCommonApiImpl(OkexEnvironment environment) {
        super(environment);
    }

    @Override
    public CommonFutureContractInfoResponse[] getContractInfo() {

        return GET_FUTURE_CONTRACT_INFO.bind(environment)
                .getForObject(CommonFutureContractInfoResponse[].class);
    }

    @Override
    public FutureDepthInfoResponse getFutureDepth(FutureMarketId instrumentId, Integer size, BigDecimal depth) {

        PublicGetTemplateClient client = GET_FUTURE_ORDER_BOOK.bind(environment);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        if (size != null) {
            client.getUriComponentsBuilder().queryParam("size", size);
        }
        if (depth != null) {
            client.getUriComponentsBuilder().queryParam("depth", depth.toPlainString());
        }

        return client.getForObject(FutureDepthInfoResponse.class);
    }

    @Override
    public FutureTickerInfoResponse[] getAllTicker() {
        return GET_FUTURE_ALL_TICKER.bind(environment)
                .getForObject(FutureTickerInfoResponse[].class);
    }

    @Override
    public FutureTickerInfoResponse getTickerByInstrumentId(FutureMarketId instrumentId) {

        PublicGetTemplateClient client = GET_FUTURE_TICKER_BY_ID.bind(environment);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        return client.getForObject(FutureTickerInfoResponse.class);
    }

    @Override
    public FutureNewDealResponse[] getFuturesNewDeal(FutureMarketId instrumentId, Long after, Long before, Integer limit) {

        PublicGetTemplateClient client = GET_FUTURE_NEW_DEAL.bind(environment);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }


        if (after != null) {
            client.getUriComponentsBuilder().queryParam("after", after);
        }
        if (before != null) {
            client.getUriComponentsBuilder().queryParam("before", before);
        }
        if (limit != null) {
            client.getUriComponentsBuilder().queryParam("limit", limit);
        }

        return client.getForObject(FutureNewDealResponse[].class);
    }

    @Override
    public FutureKLineResponse[] getFuturesCandles(FutureMarketId instrumentId, LocalDateTime start, LocalDateTime end, CandleInterval granularity) {
        PublicGetTemplateClient client = GET_FUTURE_CANDLES.bind(environment);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        if (start != null) {
            client.getUriComponentsBuilder().queryParam("start", start.toString());
        }
        if (end != null) {
            client.getUriComponentsBuilder().queryParam("end", end.toString());
        }
        if (granularity != null) {
            client.getUriComponentsBuilder().queryParam("granularity", granularity.getSeconds());
        }

        return client.getForObject(FutureKLineResponse[].class);

    }

    @Override
    public FutureIndexResponse getFutureIndex(FutureMarketId instrumentId) {
        PublicGetTemplateClient client = GET_FUTURE_INDEX.bind(environment);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }
        return client.getForObject(FutureIndexResponse.class);
    }

    @Override
    public FiatRateResponse getFiatRate() {
        PublicGetTemplateClient client = GET_FUTURE_FIAT_RATE.bind(environment);
        return client.getForObject(FiatRateResponse.class);

    }

    @Override
    public EstimatedDeliveryPriceResponse getEstimatedDeliveryPrice(FutureMarketId instrumentId) {
        PublicGetTemplateClient client = GET_FUTURE_ESTIMATE_DELIVERY_PRICE.bind(environment);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }
        return client.getForObject(EstimatedDeliveryPriceResponse.class);
    }

    @Override
    public FuturePlatformHoldsResponse getPlatformHolds(FutureMarketId instrumentId) {
        PublicGetTemplateClient client = GET_FUTURE_PLATFORM_HOLDS.bind(environment);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }
        return client.getForObject(FuturePlatformHoldsResponse.class);
    }

    @Override
    public FuturePriceLimitResponse getFuturePriceLimit(FutureMarketId instrumentId) {
        PublicGetTemplateClient client = GET_FUTURE_PRICE_LIMIT.bind(environment);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }
        return client.getForObject(FuturePriceLimitResponse.class);
    }

    @Override
    public FutureMarkPriceResponse getFutureMarkPrice(FutureMarketId instrumentId) {
        PublicGetTemplateClient client = GET_FUTURE_MARK_PRICE.bind(environment);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }
        return client.getForObject(FutureMarkPriceResponse.class);
    }

    @Override
    public FutureLiquidationOrderResponse[] getLiquidationOrders(FutureMarketId instrumentId, Integer status, Integer from, Integer to, Integer limit) {
        PublicGetTemplateClient client = GET_FUTURE_LIQUIDATION_PRICE.bind(environment);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        if (status != null) {
            client.getUriComponentsBuilder().queryParam("status", status);
        }
        if (from != null) {
            client.getUriComponentsBuilder().queryParam("from", from);
        }
        if (to != null) {
            client.getUriComponentsBuilder().queryParam("to", to);
        }
        if (limit != null) {
            client.getUriComponentsBuilder().queryParam("limit", limit);
        }

        return client.getForObject(FutureLiquidationOrderResponse[].class);
    }

    @Override
    public SettlementHistoryResponse[] getSettlementHistory(FutureMarketId instrumentId, CoinPair underlying, LocalDateTime start, LocalDateTime end, Integer limit) {
        PublicGetTemplateClient client = GET_FUTURE_SETTLEMENT_HISTORY.bind(environment);

        if (instrumentId != null) {
            client.getUriComponentsBuilder().queryParam("instrument_id", instrumentId.getSymbol());
        }

        if (underlying != null) {
            client.getUriComponentsBuilder().queryParam("underlying", underlying.getSymbol());
        }
        if (start != null) {
            client.getUriComponentsBuilder().queryParam("start", start.toString());
        }
        if (end != null) {
            client.getUriComponentsBuilder().queryParam("end", end.toString());
        }
        if (limit != null) {
            client.getUriComponentsBuilder().queryParam("limit", limit);
        }

        return client.getForObject(SettlementHistoryResponse[].class);
    }

    @Override
    public HistoryKLineResponse[] getFuturesHistoryKlines(FutureMarketId instrumentId, LocalDateTime start, LocalDateTime end, CandleInterval granularity, Integer limit) {
        PublicGetTemplateClient client = GET_FUTURE_HISTORY_CANDLES.bind(environment);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol().toUpperCase());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        if (start != null) {
            client.getUriComponentsBuilder().queryParam("start", start.toString());
        }
        if (end != null) {
            client.getUriComponentsBuilder().queryParam("end", end.toString());
        }
        if (granularity != null) {
            client.getUriComponentsBuilder().queryParam("granularity", granularity.getSeconds());
        }

        return client.getForObject(HistoryKLineResponse[].class);
    }
}
