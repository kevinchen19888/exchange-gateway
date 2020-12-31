package com.kevin.gateway.okexapi.swap.websocket;


import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.websocket.WebSocketStreamFilter;
import com.kevin.gateway.okexapi.base.websocket.WebSocketTemplate;
import com.kevin.gateway.okexapi.base.websocket.request.ChannelTrait;
import com.kevin.gateway.okexapi.base.websocket.request.SubscribeOpBuilder;
import com.kevin.gateway.okexapi.base.websocket.response.FailureResponse;
import com.kevin.gateway.okexapi.swap.websocket.event.SwapPrivateDataListenerContainer;
import com.kevin.gateway.okexapi.swap.websocket.response.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Slf4j
public final class SwapPrivateDataWsTemplate extends WebSocketTemplate {

    private final SwapPrivateDataListenerContainer swapPrivateDataListenerContainer;

    public SwapPrivateDataWsTemplate(
            SwapPrivateDataListenerContainer swapPrivateDataListenerContainer,
            SubscribeOpBuilder subscribeOpBuilder,
            OkexEnvironment okexEnvironment,
            WebSocketStreamFilter webSocketStreamFilter,
            @Nullable Credentials credentials) {
        super(okexEnvironment, webSocketStreamFilter, subscribeOpBuilder, credentials);
        this.swapPrivateDataListenerContainer = swapPrivateDataListenerContainer;
    }

    @Override
    protected void handleEventFailureResponse(WebSocketSession session, FailureResponse eventFailure) {
        swapPrivateDataListenerContainer.fireErrorListener(eventFailure);
    }

    @Override
    protected void handleDataResponse(JsonNode root) throws JsonProcessingException {
        ChannelTrait channelTrait = ChannelTrait.fromSymbol(root.get("table").asText());
        if(channelTrait instanceof SwapChannel){
            switch((SwapChannel)channelTrait){
                case ACCOUNT:
                    AccountResponse accountResponse = objectMapper.treeToValue(root, AccountResponse.class);
                    processAccount(accountResponse.getData());
                    break;
                case POSITION:
                    PositionResponse positionResponse = objectMapper.treeToValue(root, PositionResponse.class);
                    processPosition(positionResponse.getData());
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

    private void processPosition(List<PositionData> dataList) {
        for (PositionData data : dataList) {
            log.debug("processFuturePosition data:{}", data);
            swapPrivateDataListenerContainer.firePositionListener(data);
        }
    }

    private void processAccount(List<AccountData> dataList) {
        for (AccountData data : dataList) {
            log.debug("processFutureAccount data:{}", data);
            swapPrivateDataListenerContainer.fireAccountListener(data);
        }
    }

    private void processOrder(List<OrderData> dataList) {
        for (OrderData data : dataList) {
            log.debug("processFutureOrder data:{}", data);
            swapPrivateDataListenerContainer.fireOrderListener(data);
        }
    }

    private void processOrderAlgo(List<OrderAlgoData> dataList) {
        for (OrderAlgoData data : dataList) {
            log.debug("processFutureOrderAlgo data:{}", data);
            swapPrivateDataListenerContainer.fireOrderAlgoListener(data);
        }
    }

}
