package com.kevin.gateway.okexapi.future.service.impl;


import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.core.Credentials;


import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.rest.PrivateGetTemplate;
import com.kevin.gateway.okexapi.base.rest.PrivateGetTemplateClient;
import com.kevin.gateway.okexapi.base.rest.PrivatePostTemplate;
import com.kevin.gateway.okexapi.base.rest.PrivatePostTemplateClient;
import com.kevin.gateway.okexapi.base.rest.impl.OkexAbstractImpl;
import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.model.*;
import com.kevin.gateway.okexapi.future.service.FuturesApi;
import com.kevin.gateway.okexapi.future.type.AccountFlowType;
import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderStatus;
import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderType;
import com.kevin.gateway.okexapi.spot.model.SpotOrderInfoState;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class FuturesApiImpl extends OkexAbstractImpl implements FuturesApi {

    private static final PrivateGetTemplate GET_FUTURE_ACCOUNT = PrivateGetTemplate
            .of("/api/futures/v3/accounts", 1, Duration.ofSeconds(10L));
    private static final PrivateGetTemplate GET_FUTURE_POSITION = PrivateGetTemplate
            .of("/api/futures/v3/position", 5, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate GET_FUTURE_POSITION_BYID = PrivateGetTemplate
            .of("/api/futures/v3/{instrument_id}/position", 20, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate GET_FUTURE_ACCOUNT_BY_UNDERLYING = PrivateGetTemplate
            .of("/api/futures/v3/accounts/{underlying}", 20, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate GET_FUTURE_ACCOUNT_LEVERAGE = PrivateGetTemplate
            .of("/api/futures/v3/accounts/{underlying}/leverage", 5, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate POST_FUTURE_ACCOUNT_LEVERAGE = PrivatePostTemplate
            .of("/api/futures/v3/accounts/{underlying}/leverage", 5, Duration.ofSeconds(2L));


    private static final PrivateGetTemplate GET_FUTURE_ACCOUNT_FLOW = PrivateGetTemplate
            .of("/api/futures/v3/accounts/{underlying}/ledger", 5, Duration.ofSeconds(2L));


    private static final PrivatePostTemplate POST_FUTURE_PLACE_ORDER = PrivatePostTemplate
            .of("/api/futures/v3/order", 60, Duration.ofSeconds(2L));


    private static final PrivatePostTemplate POST_BATCH_FUTURE_PLACE_ORDER = PrivatePostTemplate
            .of("/api/futures/v3/orders", 30, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate POST_FUTURE_CANCEL_ORDER = PrivatePostTemplate
            .of("/api/futures/v3/cancel_order/{instrument_id}/{order_id}", 60, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate POST_BATCH_FUTURE_CANCEL_ORDER = PrivatePostTemplate
            .of("/api/futures/v3/cancel_batch_orders/{instrument_id}", 30, Duration.ofSeconds(2L));


    private static final PrivatePostTemplate POST_FUTURE_AMEND_ORDER = PrivatePostTemplate
            .of("/api/futures/v3/amend_order/{instrument_id}", 40, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate POST_BATCH_FUTURE_AMEND_ORDER = PrivatePostTemplate
            .of("/api/futures/v3/amend_batch_orders/{instrument_id}", 20, Duration.ofSeconds(2L));


    private static final PrivateGetTemplate GET_FUTURE_ORDERS = PrivateGetTemplate
            .of("/api/futures/v3/orders/{instrument_id}", 10, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate GET_FUTURE_ORDER_DETAIL = PrivateGetTemplate
            .of("/api/futures/v3/orders/{instrument_id}/{order_id}", 60, Duration.ofSeconds(2L));


    private static final PrivateGetTemplate GET_FUTURE_FILLS = PrivateGetTemplate
            .of("/api/futures/v3/fills", 10, Duration.ofSeconds(2L));


    private static final PrivatePostTemplate POST_FUTURE_MARGIN_MODE = PrivatePostTemplate
            .of("/api/futures/v3/accounts/margin_mode", 5, Duration.ofSeconds(2L));


    private static final PrivatePostTemplate POST_FUTURE_CLOSE_ALL = PrivatePostTemplate
            .of("/api/futures/v3/close_position", 2, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate POST_FUTURE_CANCEL_ALL = PrivatePostTemplate
            .of("/api/futures/v3/cancel_all", 5, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate GET_FUTURE_ACCOUNT_HOLDS = PrivateGetTemplate
            .of("/api/futures/v3/accounts/{instrument_id}/holds", 5, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate GET_FUTURE_ORDERS_BY_INSTRUMENTID = PrivateGetTemplate
            .of("/api/futures/v3/order_algo/{instrument_id}", 20, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate POST_POLICY_FUTURE_ORDER = PrivatePostTemplate
            .of("/api/futures/v3/order_algo", 40, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate POST_CANCEL_POLICY_FUTURE_ORDER = PrivatePostTemplate
            .of("/api/futures/v3/cancel_algos", 20, Duration.ofSeconds(2L));


    private static final PrivateGetTemplate GET_FUTURE_TRADE_FEE = PrivateGetTemplate
            .of("/api/futures/v3/trade_fee", 20, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate POST_FUTURE_AUTO_MARGIN = PrivatePostTemplate
            .of("/api/futures/v3/accounts/auto_margin", 5, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate POST_FUTURE_UPDATE_MARGIN = PrivatePostTemplate
            .of("/api/futures/v3/position/margin", 5, Duration.ofSeconds(2L));


    public FuturesApiImpl(OkexEnvironment environment) {
        super(environment);
    }

    public FuturesApiImpl() {
        super(null);
    }

    @Override
    public AllContractPositionResponse getFuturesPosition(Credentials c) {

        return GET_FUTURE_POSITION.bind(environment, c)
                .getForObject(AllContractPositionResponse.class);
    }


    @Override
    public GetSingleContractPositionResponse getFuturesPositionByInstrumentId(Credentials c, FutureMarketId instrumentId) {
        PrivateGetTemplateClient client = GET_FUTURE_POSITION_BYID.bind(environment, c);

        if (instrumentId != null) {
            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        return client.getForObject(GetSingleContractPositionResponse.class);
    }


    @Override
    public CoinContractAccountResponse getFuturesAccounts(Credentials c) {
        return GET_FUTURE_ACCOUNT.bind(environment, c)
                .getForObject(CoinContractAccountResponse.class);

    }

    @Override
    public CoinContractAccountResponseBase getFuturesAccountsByUnderlying(Credentials c, CoinPair underlying) {
        PrivateGetTemplateClient client = GET_FUTURE_ACCOUNT_BY_UNDERLYING.bind(environment, c);

        if (underlying != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("underlying", underlying.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        return client.getForObject(CoinContractAccountResponseBase.class);
    }


    @Override
    public LeverageBaseItem getFuturesAccountsLeverageByUnderlying(Credentials c, CoinPair underlying) {
        PrivateGetTemplateClient client = GET_FUTURE_ACCOUNT_LEVERAGE.bind(environment, c);

        if (underlying != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("underlying", underlying.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        return client.getForObject(LeverageBaseItem.class);
    }


    @Override
    public SetLeverageBodyResponseBase setFuturesAccountsUnderlyingLeverage(Credentials c, CoinPair underlying, SetLeverageBody body) {
        PrivatePostTemplateClient client = POST_FUTURE_ACCOUNT_LEVERAGE.bind(environment, c);

        if (underlying != null) {
            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("underlying", underlying.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        return client.postForObject(body, SetLeverageBodyResponseBase.class);
    }

    @Override
    public AccountFlowResponse[] getFuturesAccountsFlow(Credentials c, CoinPair underlying, @Nullable Long after, @Nullable Long before, @Nullable Integer limit, @Nullable AccountFlowType type) {

        PrivateGetTemplateClient client = GET_FUTURE_ACCOUNT_FLOW.bind(environment, c);

        if (underlying != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("underlying", underlying.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }
        if (after != null) {
            client.getUriComponentsBuilder().queryParam("after", after);
        }
        if (before != null) {
            client.getUriComponentsBuilder().queryParam("before", after);
        }
        if (limit != null) {
            client.getUriComponentsBuilder().queryParam("limit", limit);
        }
        if (type != null) {
            client.getUriComponentsBuilder().queryParam("type", type.getValue());
        }

        return client.getForObject(AccountFlowResponse[].class);
    }


    @Override
    public PlaceOrderResponse placeFuturesOrder(Credentials c, FuturePlaceOrderRequest body) {
        PrivatePostTemplateClient client = POST_FUTURE_PLACE_ORDER.bind(environment, c);
        return client.postForObject(body, PlaceOrderResponse.class);
    }

    @Override
    public BatchPlaceOrderResponse batchPlaceFuturesOrder(Credentials c, FutureBatchPlaceOrderRequest body) {
        PrivatePostTemplateClient client = POST_BATCH_FUTURE_PLACE_ORDER.bind(environment, c);
        return client.postForObject(body, BatchPlaceOrderResponse.class);
    }


    @Override
    public CancelOrderResponse cancelFuturesOrderByInstrumentId(Credentials c, FutureMarketId instrumentId, String orderId) {
        PrivatePostTemplateClient client = POST_FUTURE_CANCEL_ORDER.bind(environment, c);

        if (instrumentId != null) {
            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        if (orderId != null) {
            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("order_id", orderId);
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        return client.postForObject(null, CancelOrderResponse.class);
    }

    @Override
    public BatchCancelFutureOrderResponse cancelFutureOrders(Credentials c, FutureMarketId instrumentId, BatchCancelOrderRequest body) {
        PrivatePostTemplateClient client = POST_BATCH_FUTURE_CANCEL_ORDER.bind(environment, c);

        if (instrumentId != null) {
            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        return client.postForObject(body, BatchCancelFutureOrderResponse.class);
    }


    @Override
    public AmendOrderResponse amendFutureOrder(Credentials c, FutureMarketId instrumentId, AmendOrderRequest body) {
        PrivatePostTemplateClient client = POST_FUTURE_AMEND_ORDER.bind(environment, c);

        if (instrumentId != null) {
            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }


        return client.postForObject(body, AmendOrderResponse.class);
    }

    @Override
    public BatchAmendOrderResponse batchAmendFutureOrders(Credentials c, FutureMarketId instrumentId, BatchAmendOrderRequest body) {
        PrivatePostTemplateClient client = POST_BATCH_FUTURE_AMEND_ORDER.bind(environment, c);

        if (instrumentId != null) {
            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }


        return client.postForObject(body, BatchAmendOrderResponse.class);
    }


    @Override
    public OrderListResponse getFutureOrdersByState(Credentials c, FutureMarketId instrumentId, String after, String before, Integer limit, SpotOrderInfoState state) {
        PrivateGetTemplateClient client = GET_FUTURE_ORDERS.bind(environment, c);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }
        if (after != null) {
            client.getUriComponentsBuilder().queryParam("after", after);
        }
        if (before != null) {
            client.getUriComponentsBuilder().queryParam("before", after);
        }
        if (limit != null) {
            client.getUriComponentsBuilder().queryParam("limit", limit);
        }
        if (state != null) {
            client.getUriComponentsBuilder().queryParam("state", state.getIntValue());
        }


        return client.getForObject(OrderListResponse.class);

    }


    @Override
    public OrderDetailResponse getFutureOrderDetail(Credentials c, FutureMarketId instrumentId, String orderId) {
        PrivateGetTemplateClient client = GET_FUTURE_ORDER_DETAIL.bind(environment, c);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        if (orderId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("order_id", orderId);
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        return client.getForObject(OrderDetailResponse.class);
    }


    @Override
    public FillsDetailResponse[] getFutureFills(Credentials c, FutureMarketId instrumentId, @Nullable String orderId, @Nullable Integer after, @Nullable Integer before, @Nullable Integer limit) {
        PrivateGetTemplateClient client = GET_FUTURE_FILLS.bind(environment, c);

        if (instrumentId != null) {
            client.getUriComponentsBuilder().queryParam("instrument_id", instrumentId.getSymbol());
        }
        if (after != null) {
            client.getUriComponentsBuilder().queryParam("after", after);
        }
        if (before != null) {
            client.getUriComponentsBuilder().queryParam("before", after);
        }
        if (limit != null) {
            client.getUriComponentsBuilder().queryParam("limit", limit);
        }

        return client.getForObject(FillsDetailResponse[].class);
    }


    @Override
    public SetMarginModeResponse setFuturesAccountsMarginMode(Credentials c, SetMarginModeRequest body) {
        PrivatePostTemplateClient client = POST_FUTURE_MARGIN_MODE.bind(environment, c);

        return client.postForObject(body, SetMarginModeResponse.class);
    }


    @Override
    public SetClosePositionResponse closeAllFuturesPosition(Credentials c, SetClosePositionRequest body) {
        PrivatePostTemplateClient client = POST_FUTURE_CLOSE_ALL.bind(environment, c);

        return client.postForObject(body, SetClosePositionResponse.class);
    }


    @Override
    public CancelAllResponse cancelFuturesAll(Credentials c, CancelAllRequest body) {
        PrivatePostTemplateClient client = POST_FUTURE_CANCEL_ALL.bind(environment, c);

        return client.postForObject(body, CancelAllResponse.class);
    }


    @Override
    public HoldResponse getFuturesAccountsHolds(Credentials c, FutureMarketId instrumentId) {
        PrivateGetTemplateClient client = GET_FUTURE_ACCOUNT_HOLDS.bind(environment, c);

        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        return client.getForObject(HoldResponse.class);

    }

    @Override
    public FuturePlacePolicyOrderResponse placePolicyOrder(Credentials c, FuturePlaceAlgoOrderRequest body) {
        PrivatePostTemplateClient client = POST_POLICY_FUTURE_ORDER.bind(environment, c);

        return client.postForObject(body, FuturePlacePolicyOrderResponse.class);
    }

    @Override
    public CancelFuturePolicyOrderResponse cancelPolicyOrder(Credentials c, FutureCancelAlgoOrderRequest body) {
        PrivatePostTemplateClient client = POST_CANCEL_POLICY_FUTURE_ORDER.bind(environment, c);

        return client.postForObject(body, CancelFuturePolicyOrderResponse.class);
    }

    @Override
    public OrdersBaseItem[] getFuturesOrderByAlgoInstrumentId(Credentials c, FutureMarketId instrumentId, SpotAlgoOrderType orderType, SpotAlgoOrderStatus status, String algoId, @Nullable Integer after, @Nullable Integer before, @Nullable Integer limit) {
        PrivateGetTemplateClient client = GET_FUTURE_ORDERS_BY_INSTRUMENTID.bind(environment, c);

        if (instrumentId != null) {
            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }
        if (after != null) {
            client.getUriComponentsBuilder().queryParam("after", after);
        }
        if (before != null) {
            client.getUriComponentsBuilder().queryParam("before", after);
        }
        if (limit != null) {
            client.getUriComponentsBuilder().queryParam("limit", limit);
        }
        if (algoId != null) {
            client.getUriComponentsBuilder().queryParam("algo_id", algoId);
        }

        if (orderType != null) {
            client.getUriComponentsBuilder().queryParam("order_type", orderType.getIntValue());
        }
        if (status != null) {
            client.getUriComponentsBuilder().queryParam("status", status.getIntValue());
        }

        return client.getForObject(OrdersBaseItem[].class);
    }

    @Override
    public TradeFeeResponse getFuturesTradeFee(Credentials c, String category, CoinPair underlying) {
        PrivateGetTemplateClient client = GET_FUTURE_TRADE_FEE.bind(environment, c);

        if (category != null) {
            client.getUriComponentsBuilder().queryParam("category", category);
        }
        if (underlying != null) {
            client.getUriComponentsBuilder().queryParam("underlying", underlying.getSymbol());
        }
        return client.getForObject(TradeFeeResponse.class);

    }


    @Override
    public SetAutoMarginResponse setFuturesAccountsAutoMargin(Credentials c, SetAutoMarginRequest body) {
        PrivatePostTemplateClient client = POST_FUTURE_AUTO_MARGIN.bind(environment, c);

        return client.postForObject(body, SetAutoMarginResponse.class);
    }


    @Override
    public UpdateMarginResponse updateFuturesPositionMargin(Credentials c, UpdateMarginRequest body) {
        PrivatePostTemplateClient client = POST_FUTURE_UPDATE_MARGIN.bind(environment, c);

        return client.postForObject(body, UpdateMarginResponse.class);
    }


}
