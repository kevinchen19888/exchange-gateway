package com.kevin.gateway.okexapi.swap.service;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.rest.PrivateGetTemplate;
import com.kevin.gateway.okexapi.base.rest.PrivateGetTemplateClient;
import com.kevin.gateway.okexapi.base.rest.PrivatePostTemplate;
import com.kevin.gateway.okexapi.base.rest.PrivatePostTemplateClient;
import com.kevin.gateway.okexapi.base.rest.impl.OkexAbstractImpl;
import com.kevin.gateway.okexapi.future.model.*;
import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderStatus;
import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderType;
import com.kevin.gateway.okexapi.spot.model.SpotOrderInfoState;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import com.kevin.gateway.okexapi.swap.model.*;

import com.kevin.gateway.okexapi.swap.type.SwapFlowType;


import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class SwapApiImpl extends OkexAbstractImpl implements SwapApi {

    private static final PrivateGetTemplate GET_SWAP_ACCOUNT = PrivateGetTemplate
            .of("/api/swap/v3/accounts", 1, Duration.ofSeconds(10L));
    private static final PrivateGetTemplate GET_SWAP_POSITION = PrivateGetTemplate
            .of("/api/swap/v3/position", 5, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate GET_SWAP_POSITION_BYID = PrivateGetTemplate
            .of("/api/swap/v3/{instrument_id}/position", 20, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate GET_SWAP_ACCOUNT_BY_ID = PrivateGetTemplate
            .of("/api/swap/v3/{instrument_id}/accounts", 20, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate GET_SWAP_ACCOUNT_SETTINGS = PrivateGetTemplate
            .of("/api/swap/v3/accounts/{instrument_id}/settings", 5, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate POST_SWAP_ACCOUNT_LEVERAGE = PrivatePostTemplate
            .of("/api/swap/v3/accounts/{instrument_id}/leverage", 5, Duration.ofSeconds(2L));


    private static final PrivateGetTemplate GET_SWAP_ACCOUNT_FLOW = PrivateGetTemplate
            .of("/api/swap/v3/accounts/{instrument_id}/ledger", 5, Duration.ofSeconds(2L));


    private static final PrivatePostTemplate POST_SWAP_PLACE_ORDER = PrivatePostTemplate
            .of("/api/swap/v3/order", 40, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate POST_SWAP_BATCH_PLACE_ORDER = PrivatePostTemplate
            .of("/api/swap/v3/orders", 20, Duration.ofSeconds(2L));


    private static final PrivatePostTemplate POST_SWAP_CANCEL_ORDER = PrivatePostTemplate
            .of("/api/swap/v3/cancel_order/{instrument_id}/{order_id}", 40, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate POST_SWAP_CANCEL_ORDERS = PrivatePostTemplate
            .of("/api/swap/v3/cancel_batch_orders/{instrument_id}", 20, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate POST_SWAP_AMEND_ORDER = PrivatePostTemplate
            .of("/api/swap/v3/amend_order/{instrument_id}", 40, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate POST_SWAP_AMEND_ORDERS = PrivatePostTemplate
            .of("/api/swap/v3/amend_batch_orders/{instrument_id}", 20, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate GET_SWAP_ORDERS = PrivateGetTemplate
            .of("/api/swap/v3/orders/{instrument_id}", 10, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate GET_SWAP_ORDER_DETAIL = PrivateGetTemplate
            .of("/api/swap/v3/orders/{instrument_id}/{order_id}", 10, Duration.ofSeconds(2L));


    private static final PrivateGetTemplate GET_SWAP_FILLS = PrivateGetTemplate
            .of("/api/swap/v3/fills", 10, Duration.ofSeconds(2L));


    private static final PrivatePostTemplate POST_SWAP_CLOSE_ALL = PrivatePostTemplate
            .of("/api/swap/v3/close_position", 2, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate POST_SWAP_CANCEL_ALL = PrivatePostTemplate
            .of("/api/swap/v3/cancel_all", 5, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate GET_SWAP_ACCOUNT_HOLDS = PrivateGetTemplate
            .of("/api/swap/v3/accounts/{instrument_id}/holds", 5, Duration.ofSeconds(2L));

    private static final PrivateGetTemplate GET_SWAP_ORDERS_BY_INSTRUMENTID = PrivateGetTemplate
            .of("/api/swap/v3/order_algo/{instrument_id}", 20, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate POST_SWAP_ALGO_ORDER = PrivatePostTemplate
            .of("/api/swap/v3/order_algo", 40, Duration.ofSeconds(2L));

    private static final PrivatePostTemplate POST_SWAP_CANCEL_POLICY_ORDER = PrivatePostTemplate
            .of("/api/swap/v3/cancel_algos", 20, Duration.ofSeconds(2L));


    private static final PrivateGetTemplate GET_SWAP_TRADE_FEE = PrivateGetTemplate
            .of("/api/swap/v3/trade_fee", 20, Duration.ofSeconds(2L));


    public SwapApiImpl(OkexEnvironment environment) {
        super(environment);
    }

    @Override
    public GetSwapContractPositionResponse[] getAllSwapPosition(Credentials c) {
        return GET_SWAP_POSITION.bind(environment, c).getForObject(GetSwapContractPositionResponse[].class);
    }

    @Override
    public GetSwapContractPositionResponse getSwapPositionByInstrumentId(Credentials c, SwapMarketId instrumentId) {
        PrivateGetTemplateClient client = GET_SWAP_POSITION_BYID.bind(environment, c);

        if (instrumentId != null) {
            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        return client.getForObject(GetSwapContractPositionResponse.class);
    }

    @Override
    public AllCoinSwapAccountResponse getAllSwapAccounts(Credentials c) {
        PrivateGetTemplateClient client = GET_SWAP_ACCOUNT.bind(environment, c);

        return client.getForObject(AllCoinSwapAccountResponse.class);
    }

    @Override
    public SingleCoinSwapAccountResponse getSwapAccountByInstrumentId(Credentials c, SwapMarketId instrumentId) {
        PrivateGetTemplateClient client = GET_SWAP_ACCOUNT_BY_ID.bind(environment, c);
        if (instrumentId != null) {
            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }
        return client.getForObject(SingleCoinSwapAccountResponse.class);
    }

    @Override
    public SwapSettingsResponse getSwapAccountsSettings(Credentials c, SwapMarketId instrumentId) {
        PrivateGetTemplateClient client = GET_SWAP_ACCOUNT_SETTINGS.bind(environment, c);
        if (instrumentId != null) {
            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }
        return client.getForObject(SwapSettingsResponse.class);
    }

    @Override
    public SwapSettingsResponse setSwapAccountsLeverage(Credentials c, SwapMarketId instrumentId, SetSwapBodyRequest body) {
        PrivatePostTemplateClient client = POST_SWAP_ACCOUNT_LEVERAGE.bind(environment, c);

        if (instrumentId != null) {
            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        return client.postForObject(body, SwapSettingsResponse.class);
    }

    @Override
    public SwapAccountFlowResponse[] getSwapAccountsFlows(Credentials c, SwapMarketId instrumentId, Long after, Long before, Integer limit, SwapFlowType type) {
        PrivateGetTemplateClient client = GET_SWAP_ACCOUNT_FLOW.bind(environment, c);

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
        if (type != null) {
            client.getUriComponentsBuilder().queryParam("type", type.getValue());
        }

        return client.getForObject(SwapAccountFlowResponse[].class);

    }

    @Override
    public PlaceOrderResponse placeSwapOrder(Credentials c, SwapPlaceOrderRequest body) {
        PrivatePostTemplateClient client = POST_SWAP_PLACE_ORDER.bind(environment, c);
        return client.postForObject(body, PlaceOrderResponse.class);
    }

    @Override
    public CancelSwapOrderResponse cancelSwapOrder(Credentials c, SwapMarketId instrumentId, String orderId) {
        PrivatePostTemplateClient client = POST_SWAP_CANCEL_ORDER.bind(environment, c);
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
        return client.postForObject(null, CancelSwapOrderResponse.class);
    }

    @Override
    public BatchCancelSwapOrderResponse cancelSwapOrders(Credentials c, SwapMarketId instrumentId, BatchCancelOrderRequest cancelOrders) {
        PrivatePostTemplateClient client = POST_SWAP_CANCEL_ORDERS.bind(environment, c);
        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }
        return client.postForObject(cancelOrders, BatchCancelSwapOrderResponse.class);
    }

    @Override
    public BatchPlaceOrderResponse batchPlaceSwapOrders(Credentials c, SwapBatchPlaceOrderRequest body) {
        PrivatePostTemplateClient client = POST_SWAP_BATCH_PLACE_ORDER.bind(environment, c);
        return client.postForObject(body, BatchPlaceOrderResponse.class);
    }

    @Override
    public AmendOrderResponse amendSwapOrder(Credentials c, SwapMarketId instrumentId, AmendOrderRequest body) {
        PrivatePostTemplateClient client = POST_SWAP_AMEND_ORDER.bind(environment, c);
        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }
        return client.postForObject(body, AmendOrderResponse.class);
    }

    @Override
    public BatchAmendOrderResponse batchAmendSwapOrders(Credentials c, SwapMarketId instrumentId, BatchAmendOrderRequest body) {
        PrivatePostTemplateClient client = POST_SWAP_AMEND_ORDERS.bind(environment, c);
        if (instrumentId != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }
        return client.postForObject(body, BatchAmendOrderResponse.class);
    }

    @Override
    public SwapOrderDetailListResponse getSwapOrders(Credentials c, SwapMarketId instrumentId, Long after, Long before, Integer limit, SpotOrderInfoState state) {
        PrivateGetTemplateClient client = GET_SWAP_ORDERS.bind(environment, c);

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

        return client.getForObject(SwapOrderDetailListResponse.class);
    }

    @Override
    public SwapOrderDetailResponse getSwapOrderDetail(Credentials c, SwapMarketId instrumentId, String orderId) {
        PrivateGetTemplateClient client = GET_SWAP_ORDER_DETAIL.bind(environment, c);
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
        return client.getForObject(SwapOrderDetailResponse.class);
    }

    @Override
    public SwapFillsDetailResponse[] getSwapFills(Credentials c, SwapMarketId instrumentId, String orderId, Long after, Long before, Integer limit) {
        PrivateGetTemplateClient client = GET_SWAP_FILLS.bind(environment, c);

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
        if (orderId != null) {
            client.getUriComponentsBuilder().queryParam("order_id", orderId);
        }

        return client.getForObject(SwapFillsDetailResponse[].class);
    }

    @Override
    public SwapHoldResponse getSwapAccountsHolds(Credentials c, SwapMarketId instrumentId) {
        PrivateGetTemplateClient client = GET_SWAP_ACCOUNT_HOLDS.bind(environment, c);
        if (instrumentId != null) {
            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("instrument_id", instrumentId.getSymbol());
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        return client.getForObject(SwapHoldResponse.class);
    }

    @Override
    public SwapPlaceAlgoOrderResponse placePolicyOrder(Credentials c, SwapPlaceAlgoOrderRequest body) {
        PrivatePostTemplateClient client = POST_SWAP_ALGO_ORDER.bind(environment, c);
        return client.postForObject(body, SwapPlaceAlgoOrderResponse.class);
    }

    @Override
    public SwapCancelPolicyOrderResponse cancelPolicyOrder(Credentials c, SwapCancelAlgoOrderRequest body) {
        PrivatePostTemplateClient client = POST_SWAP_CANCEL_POLICY_ORDER.bind(environment, c);
        return client.postForObject(body, SwapCancelPolicyOrderResponse.class);
    }

    @Override
    public GetSwapOrdersListResponse getSwapOrders(Credentials c, SwapMarketId instrumentId, SpotAlgoOrderType orderType, SpotAlgoOrderStatus status, String algoId, Long after, Long before, Integer limit) {
        PrivateGetTemplateClient client = GET_SWAP_ORDERS_BY_INSTRUMENTID.bind(environment, c);

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
        if (orderType != null) {
            client.getUriComponentsBuilder().queryParam("order_type", orderType.getIntValue());
        }
        if (status != null) {
            client.getUriComponentsBuilder().queryParam("status", status.getIntValue());
        }

        return client.getForObject(GetSwapOrdersListResponse.class);
    }

    @Override
    public SwapTradeFeeResponse getSwapTradeFee(Credentials c, Integer category, SwapMarketId instrumentId) {
        PrivateGetTemplateClient client = GET_SWAP_TRADE_FEE.bind(environment, c);

        if (instrumentId != null) {
            client.getUriComponentsBuilder().queryParam("instrument_id", instrumentId.getSymbol());
        }
        if (category != null) {
            client.getUriComponentsBuilder().queryParam("category", category);
        }
        return client.getForObject(SwapTradeFeeResponse.class);
    }

    @Override
    public SetSwapPositionResponse closeAllSwapPosition(Credentials c, SetSwapClosePositionRequest body) {
        PrivatePostTemplateClient client = POST_SWAP_CLOSE_ALL.bind(environment, c);
        return client.postForObject(body, SetSwapPositionResponse.class);
    }

    @Override
    public SetSwapPositionResponse cancelSwapAll(Credentials c, SetSwapClosePositionRequest body) {
        PrivatePostTemplateClient client = POST_SWAP_CANCEL_ALL.bind(environment, c);
        return client.postForObject(body, SetSwapPositionResponse.class);
    }
}
