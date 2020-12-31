package com.kevin.gateway.okexapi.index.websocket;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.base.websocket.WebSocketStreamFilter;
import com.kevin.gateway.okexapi.base.websocket.WebSocketTemplate;
import com.kevin.gateway.okexapi.base.websocket.request.SubscribeOpBuilder;
import com.kevin.gateway.okexapi.base.websocket.response.FailureResponse;
import com.kevin.gateway.okexapi.index.vo.IndexCandleData;
import com.kevin.gateway.okexapi.index.vo.IndexTickerData;
import com.kevin.gateway.okexapi.index.websocket.event.IndexPublicDataListenerContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.kevin.gateway.okexapi.index.websocket.response.IndexWebsocketCandleResponse;
import com.kevin.gateway.okexapi.index.websocket.response.IndexWebsocketResponse;
import com.kevin.gateway.okexapi.index.websocket.response.IndexWebsocketTickerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Slf4j
public final class IndexPublicDataWsTemplate extends WebSocketTemplate {
    private final IndexPublicDataListenerContainer indexPublicDataListenerContainer;

    public IndexPublicDataWsTemplate(
            IndexPublicDataListenerContainer indexPublicDataListenerContainer,
            SubscribeOpBuilder subscribeOpBuilder,
            OkexEnvironment okexEnvironment,
            WebSocketStreamFilter webSocketStreamFilter,
            @Nullable Credentials credentials){
        super(  okexEnvironment,
                webSocketStreamFilter,
                subscribeOpBuilder,
                credentials);
        this.indexPublicDataListenerContainer = indexPublicDataListenerContainer;

    }

    @Override
    protected  void handleDataResponse(JsonNode root) throws JsonProcessingException {

        IndexWebsocketResponse respRet = objectMapper.treeToValue(root,IndexWebsocketResponse.class);
        switch((IndexChannel)respRet.getChannel()){
            case TICKER:
                processTicker(((IndexWebsocketTickerResponse)respRet).getData());
                break;
            case CANDLE60S:
                processCandle60s(((IndexWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE180S:
                processCandle180s(((IndexWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE300S:
                processCandle300s(((IndexWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE900S:
                processCandle900s(((IndexWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE1800S:
                processCandle1800s(((IndexWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE3600S:
                processCandle3600s(((IndexWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE7200S:
                processCandle7200s(((IndexWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE14400S:
                processCandle14400s(((IndexWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE21600S:
                processCandle21600s(((IndexWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE43200S:
                processCandle43200s(((IndexWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE86400S:
                processCandle86400s(((IndexWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE604800S:
                processCandle604800s(((IndexWebsocketCandleResponse)respRet).getData());
                break;
        }

    }

    @Override
    protected void handleEventFailureResponse(WebSocketSession session, FailureResponse eventFailure) {
        // TODO: ...
        indexPublicDataListenerContainer.fireErrorListener(eventFailure);
    }

    private void processTicker( List<IndexTickerData> dataList) {
        for(IndexTickerData data:dataList) {
            log.debug("processTicker data:{}", data);
            indexPublicDataListenerContainer.fireTickerListener(data.getInstrumentId(),data);
        }
    }

    private void processCandle60s(List<IndexCandleData> dataList) {
        for(IndexCandleData data:dataList) {
            log.debug("processCandle60s data:{}", data);
            indexPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_1, data);
        }
    }
    private void processCandle180s(List<IndexCandleData> dataList) {
        for(IndexCandleData data:dataList) {
            log.debug("processCandle180s data:{}", data);
            indexPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_3, data);
        }
    }
    private void processCandle300s(List<IndexCandleData> dataList) {
        for(IndexCandleData data:dataList) {
            log.debug("processCandle300s data:{}", data);
            indexPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_5, data);
        }
    }
    private void processCandle900s(List<IndexCandleData> dataList) {
        for(IndexCandleData data:dataList) {
            log.debug("processCandle900s data:{}", data);
            indexPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_15, data);
        }
    }
    private void processCandle1800s(List<IndexCandleData> dataList) {
        for(IndexCandleData data:dataList) {
            log.debug("processCandle1800s data:{}", data);
            indexPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_30, data);
        }
    }
    private void processCandle3600s(List<IndexCandleData> dataList) {
        for(IndexCandleData data:dataList) {
            log.debug("processCandle3600s data:{}", data);
            indexPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_60, data);
        }
    }
    private void processCandle7200s(List<IndexCandleData> dataList) {
        for(IndexCandleData data:dataList) {
            log.debug("processCandle7200s data:{}", data);
            indexPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.HOUR_2, data);
        }
    }
    private void processCandle14400s(List<IndexCandleData> dataList) {
        for(IndexCandleData data:dataList) {
            log.debug("processCandle14400s data:{}", data);
            indexPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.HOUR_4, data);
        }
    }
    private void processCandle21600s(List<IndexCandleData> dataList) {
        for(IndexCandleData data:dataList) {
            log.debug("processCandle21600s data:{}", data);
            indexPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.HOUR_6, data);
        }
    }
    private void processCandle43200s(List<IndexCandleData> dataList){
        for(IndexCandleData data:dataList) {
            log.debug("processCandle43200s data:{}", data);
            indexPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.HOUR_12, data);
        }
    }
    private void processCandle86400s(List<IndexCandleData> dataList){
        for(IndexCandleData data:dataList) {
            log.debug("processCandle86400s data:{}", data);
            indexPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.DAY_1, data);
        }
    }
    private void processCandle604800s(List<IndexCandleData> dataList) {
        for(IndexCandleData data:dataList) {
            log.debug("processCandle604800s data:{}", data);
            indexPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.DAY_7, data);
        }
    }

}
