package com.kevin.gateway.okexapi.spot.websocket;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.websocket.WebSocketStreamFilter;
import com.kevin.gateway.okexapi.base.websocket.WebSocketTemplate;
import com.kevin.gateway.okexapi.base.websocket.request.ChannelTrait;
import com.kevin.gateway.okexapi.base.websocket.request.SubscribeOpBuilder;
import com.kevin.gateway.okexapi.base.websocket.response.FailureResponse;
import com.kevin.gateway.okexapi.spot.websocket.event.SpotPrivateDataListenerContainer;
import com.kevin.gateway.okexapi.spot.websocket.response.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Slf4j
public final class SpotPrivateDataWsTemplate extends WebSocketTemplate {

    private final SpotPrivateDataListenerContainer spotPrivateDataListenerContainer;

    public SpotPrivateDataWsTemplate(
            SpotPrivateDataListenerContainer spotPrivateDataListenerContainer,
            SubscribeOpBuilder subscribeOpBuilder,
            OkexEnvironment okexEnvironment,
            WebSocketStreamFilter webSocketStreamFilter,
            @Nullable Credentials credentials) {
        super(okexEnvironment, webSocketStreamFilter, subscribeOpBuilder, credentials);
        this.spotPrivateDataListenerContainer = spotPrivateDataListenerContainer;

    }

    @Override
    protected void handleEventFailureResponse(WebSocketSession session, FailureResponse eventFailure) {
        spotPrivateDataListenerContainer.fireErrorListener(eventFailure);
    }

    @Override
    protected void handleDataResponse(JsonNode root) throws JsonProcessingException {
        ChannelTrait channelTrait = ChannelTrait.fromSymbol(root.get("table").asText());
        if(channelTrait instanceof SpotChannel) {
            switch ((SpotChannel)channelTrait) {
                case ACCOUNT:
                    AccountResponse accountResponse = objectMapper.treeToValue(root, AccountResponse.class);
                    processAccount(accountResponse.getData());
                    break;
                case MARGIN_ACCOUNT:
                    MarginAccountResponse marginAccountResponse = objectMapper.treeToValue(root, MarginAccountResponse.class);
                    processMarginAccount(marginAccountResponse.getData());
                    break;
                case ORDER:
                    OrderResponse orderResponse = objectMapper.treeToValue(root, OrderResponse.class);
                    processOrder(orderResponse.getData());
                    break;
                case ORDER_ALGO:
                    OrderAlgoResponse orderAlgoResponse = objectMapper.treeToValue(root, OrderAlgoResponse.class);
                    processOrderAlgo(orderAlgoResponse.getData());
                    break;
            }
        }else{
            log.error("handleDataResponse 收到错误的消息频道：{}", root.get("table").asText());
        }
    }

    private void processAccount(List<AccountData> dataList) {
        for (AccountData data : dataList) {
            log.debug("processSpotAccount data:{}", data);
            spotPrivateDataListenerContainer.fireAccountListener(data);
        }
    }

    private void processMarginAccount(List<MarginAccountData> dataList) {
        for (MarginAccountData data : dataList) {
            log.debug("processSpotMarginAccount data:{}", data);
            spotPrivateDataListenerContainer.fireMarginAccountListener(data);
        }
    }

    private void processOrder(List<OrderData> dataList) {
        for (OrderData data : dataList) {
            log.debug("processSpotOrder data:{}", data);
            spotPrivateDataListenerContainer.fireOrderListener(data);
        }
    }

    private void processOrderAlgo(List<OrderAlgoData> dataList) {
        for (OrderAlgoData data : dataList) {
            log.debug("processSpotOrderAlgo data:{}", data);
            spotPrivateDataListenerContainer.fireOrderAlgoListener(data);
        }
    }

}
