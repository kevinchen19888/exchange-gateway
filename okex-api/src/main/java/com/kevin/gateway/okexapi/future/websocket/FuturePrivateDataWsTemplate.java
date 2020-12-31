package com.kevin.gateway.okexapi.future.websocket;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.websocket.WebSocketStreamFilter;
import com.kevin.gateway.okexapi.base.websocket.WebSocketTemplate;
import com.kevin.gateway.okexapi.base.websocket.request.ChannelTrait;
import com.kevin.gateway.okexapi.base.websocket.request.SubscribeOpBuilder;
import com.kevin.gateway.okexapi.base.websocket.response.FailureResponse;
import com.kevin.gateway.okexapi.future.websocket.event.FuturePrivateDataListenerContainer;
import com.kevin.gateway.okexapi.future.websocket.response.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;


@Slf4j
public final class FuturePrivateDataWsTemplate extends WebSocketTemplate {

    private final FuturePrivateDataListenerContainer futurePrivateDataListenerContainer;

    public FuturePrivateDataWsTemplate(
            FuturePrivateDataListenerContainer futurePrivateDataListenerContainer,
            SubscribeOpBuilder subscribeOpBuilder,
            OkexEnvironment okexEnvironment,
            WebSocketStreamFilter webSocketStreamFilter,
            @Nullable Credentials credentials) {
        super(okexEnvironment, webSocketStreamFilter, subscribeOpBuilder, credentials);
        this.futurePrivateDataListenerContainer = futurePrivateDataListenerContainer;
    }

    @Override
    protected void handleEventFailureResponse(WebSocketSession session, FailureResponse eventFailure) {
        futurePrivateDataListenerContainer.fireErrorListener(eventFailure);
    }

    @Override
    protected void handleDataResponse(JsonNode root) throws JsonProcessingException {
        ChannelTrait channelTrait = ChannelTrait.fromSymbol(root.get("table").asText());
        if(channelTrait instanceof FutureChannel){
            switch((FutureChannel)channelTrait){
                case POSITION:
                    PositionResponse positionResponse = objectMapper.treeToValue(root, PositionResponse.class);
                    processPosition(positionResponse.getData());
                    break;
                case ACCOUNT:
                    AccountResponse accountResponse = objectMapper.treeToValue(root, AccountResponse.class);
                    processAccount(accountResponse.getData());
                    break;
                case ORDER_ALGO:
                    OrderAlgoResponse orderAlgoResponse = objectMapper.treeToValue(root, OrderAlgoResponse.class);
                    processOrderAlgo(orderAlgoResponse.getData());
                    break;
                case ORDER:
                    OrderResponse orderResponse = objectMapper.treeToValue(root, OrderResponse.class);
                    processOrder(orderResponse.getData());
                    break;
            }
        }else{
            log.error("handleDataResponse 收到错误的消息频道：{}", root.get("table").asText());
        }
    }

    private void processPosition(List<PositionData> dataList) {
        for (PositionData data : dataList) {
            log.debug("processFuturePosition data:{}", data);
            futurePrivateDataListenerContainer.firePositionListener(data);
        }
    }

    private void processAccount(List<AccountMapData> dataList) {
        for (AccountMapData data : dataList) {
            log.debug("processFutureAccount data:{}", data);
            Map<String, AccountData> coinAccounts = data.getCoinAccounts();
            for (Map.Entry<String, AccountData> entry : coinAccounts.entrySet()) {
                futurePrivateDataListenerContainer.fireAccountListener(entry.getValue());
            }

        }
    }

    private void processOrder(List<OrderData> dataList) {
        for (OrderData data : dataList) {
            log.debug("processFutureOrder data:{}", data);
            futurePrivateDataListenerContainer.fireOrderListener(data);
        }
    }

    private void processOrderAlgo(List<OrderAlgoData> dataList) {
        for (OrderAlgoData data : dataList) {
            log.debug("processFutureOrderAlgo data:{}", data);
            futurePrivateDataListenerContainer.fireOrderAlgoListener(data);
        }
    }

}
