package com.kevin.gateway.okexapi.swap.service;

import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.rest.PublicGetTemplate;
import com.kevin.gateway.okexapi.base.rest.PublicGetTemplateClient;
import com.kevin.gateway.okexapi.base.rest.impl.OkexAbstractImpl;

import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.future.common.model.FiatRateResponse;
import com.kevin.gateway.okexapi.future.common.model.FutureKLineResponse;
import com.kevin.gateway.okexapi.future.common.model.HistoryKLineResponse;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import com.kevin.gateway.okexapi.swap.common.model.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class SwapCommonApiImpl extends OkexAbstractImpl implements SwapCommonApi {


    private static final PublicGetTemplate GET_SWAP_CONTRACT_INFO = PublicGetTemplate
            .of("/api/swap/v3/instruments", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_SWAP_ORDER_BOOK = PublicGetTemplate
            .of("/api/swap/v3/instruments/{instrument_id}/depth", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_SWAP_ALL_TICKER = PublicGetTemplate
            .of("/api/swap/v3/instruments/ticker", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_SWAP_TICKER_BY_ID = PublicGetTemplate
            .of("/api/swap/v3/instruments/{instrument_id}/ticker", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_SWAP_NEW_DEAL = PublicGetTemplate
            .of("/api/swap/v3/instruments/{instrument_id}/trades", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_SWAP_CANDLES = PublicGetTemplate
            .of("/api/swap/v3/instruments/{instrument_id}/candles", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_SWAP_INDEX = PublicGetTemplate
            .of("/api/swap/v3/instruments/{instrument_id}/index", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_SWAP_FIAT_RATE = PublicGetTemplate
            .of("/api/swap/v3/rate", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_SWAP_PLATFORM_HOLDS = PublicGetTemplate
            .of("/api/swap/v3/instruments/{instrument_id}/open_interest", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_SWAP_PRICE_LIMIT = PublicGetTemplate
            .of("/api/swap/v3/instruments/{instrument_id}/price_limit", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_SWAP_MARK_PRICE = PublicGetTemplate
            .of("/api/swap/v3/instruments/{instrument_id}/mark_price", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_SWAP_LIQUIDATION_ORDERS = PublicGetTemplate
            .of("/api/swap/v3/instruments/{instrument_id}/liquidation", 20, Duration.ofSeconds(2L));
    private static final PublicGetTemplate GET_SWAP_HISTORY_CANDLES = PublicGetTemplate
            .of("/api/swap/v3/instruments/{instrument_id}/history/candles", 5, Duration.ofSeconds(2L));


    private static final PublicGetTemplate GET_SWAP_CAPITAL_RATE = PublicGetTemplate
            .of("/api/swap/v3/instruments/{instrument_id}/funding_time", 20, Duration.ofSeconds(2L));

    private static final PublicGetTemplate GET_SWAP_HISOTRY_CAPITAL_RATE = PublicGetTemplate
            .of("/api/swap/v3/instruments/{instrument_id}/historical_funding_rate", 20, Duration.ofSeconds(2L));


    public SwapCommonApiImpl(OkexEnvironment environment) {
        super(environment);
    }

    @Override
    public CommonSwapContractInfo[] getContractInfo() {

        return GET_SWAP_CONTRACT_INFO.bind(environment)
                .getForObject(CommonSwapContractInfo[].class);
    }

    @Override
    public SwapDepthInfoResponse getSwapDepth(SwapMarketId instrumentId, Integer size, BigDecimal depth) {

        PublicGetTemplateClient client = GET_SWAP_ORDER_BOOK.bind(environment);

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

        return client.getForObject(SwapDepthInfoResponse.class);
    }

    @Override
    public SwapTickerResponse[] getAllTicker() {
        return GET_SWAP_ALL_TICKER.bind(environment)
                .getForObject(SwapTickerResponse[].class);
    }

    @Override
    public SwapTickerResponse getTickerByInstrumentId(SwapMarketId instrumentId) {

        PublicGetTemplateClient client = GET_SWAP_TICKER_BY_ID.bind(environment);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        return client.getForObject(SwapTickerResponse.class);
    }

    @Override
    public SwapNewDealResponse[] getSwapNewDeal(SwapMarketId instrumentId, Long after, Long before, Integer limit) {

        PublicGetTemplateClient client = GET_SWAP_NEW_DEAL.bind(environment);

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

        return client.getForObject(SwapNewDealResponse[].class);
    }

    @Override

    public FutureKLineResponse[] getCandles(SwapMarketId instrumentId, LocalDateTime start, LocalDateTime end, CandleInterval granularity) {
        PublicGetTemplateClient client = GET_SWAP_CANDLES.bind(environment);

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
            client.getUriComponentsBuilder().queryParam("granularity", granularity.getSeconds());//.getValue());
        }

        return client.getForObject(FutureKLineResponse[].class);

    }

    @Override
    public SwapIndexResponse getSwapIndex(SwapMarketId instrumentId) {
        PublicGetTemplateClient client = GET_SWAP_INDEX.bind(environment);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }
        return client.getForObject(SwapIndexResponse.class);
    }

    @Override
    public FiatRateResponse getFiatRate() {
        PublicGetTemplateClient client = GET_SWAP_FIAT_RATE.bind(environment);
        return client.getForObject(FiatRateResponse.class);

    }

    @Override
    public SwapPlatformHoldsResponse getPlatformHolds(SwapMarketId instrumentId) {
        PublicGetTemplateClient client = GET_SWAP_PLATFORM_HOLDS.bind(environment);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }
        return client.getForObject(SwapPlatformHoldsResponse.class);
    }

    @Override
    public SwapPriceLimitResponse getSwapPriceLimit(SwapMarketId instrumentId) {
        PublicGetTemplateClient client = GET_SWAP_PRICE_LIMIT.bind(environment);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }
        return client.getForObject(SwapPriceLimitResponse.class);
    }

    @Override
    public SwapMarkPriceResponse getSwapMarkPrice(SwapMarketId instrumentId) {
        PublicGetTemplateClient client = GET_SWAP_MARK_PRICE.bind(environment);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }
        return client.getForObject(SwapMarkPriceResponse.class);
    }

    @Override
    public SwapCapitalRateResponse getSwapCapitalRate(SwapMarketId instrumentId) {
        PublicGetTemplateClient client = GET_SWAP_CAPITAL_RATE.bind(environment);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }
        return client.getForObject(SwapCapitalRateResponse.class);
    }

    @Override
    public SwapHistoryCapitalRateResponse[] getSwapHistoryCapitalRate(SwapMarketId instrumentId, Integer limit) {
        PublicGetTemplateClient client = GET_SWAP_HISOTRY_CAPITAL_RATE.bind(environment);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        if (limit != null) {
            client.getUriComponentsBuilder().queryParam("limit", limit);
        }

        return client.getForObject(SwapHistoryCapitalRateResponse[].class);


    }

    @Override
    public SwapLiquidationOrderResponse[] getLiquidationOrders(SwapMarketId instrumentId, Integer status, Integer from, Integer to, Integer limit) {
        PublicGetTemplateClient client = GET_SWAP_LIQUIDATION_ORDERS.bind(environment);

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

        return client.getForObject(SwapLiquidationOrderResponse[].class);
    }

    @Override
    public HistoryKLineResponse[] getHistoryKlines(SwapMarketId instrumentId, LocalDateTime start, LocalDateTime end, CandleInterval granularity, Integer limit) {
        PublicGetTemplateClient client = GET_SWAP_HISTORY_CANDLES.bind(environment);

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