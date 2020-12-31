package com.kevin.gateway.okexapi.spot.websocket;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.base.util.DepthAction;
import com.kevin.gateway.okexapi.base.websocket.WebSocketStreamFilter;
import com.kevin.gateway.okexapi.base.websocket.WebSocketTemplate;
import com.kevin.gateway.okexapi.base.websocket.request.SubscribeOpBuilder;
import com.kevin.gateway.okexapi.base.websocket.response.FailureResponse;
import com.kevin.gateway.okexapi.spot.vo.SpotCandleData;
import com.kevin.gateway.okexapi.spot.vo.SpotDepthData;
import com.kevin.gateway.okexapi.spot.vo.SpotTickerData;
import com.kevin.gateway.okexapi.spot.vo.SpotTradeData;
import com.kevin.gateway.okexapi.spot.websocket.event.SpotPublicDataListenerContainer;
import com.kevin.gateway.okexapi.spot.websocket.response.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Slf4j
public final class SpotPublicDataWsTemplate extends WebSocketTemplate {
    private final SpotPublicDataListenerContainer spotPublicDataListenerContainer;

    public SpotPublicDataWsTemplate(
            SpotPublicDataListenerContainer spotPublicDataListenerContainer,
            SubscribeOpBuilder subscribeOpBuilder,
            OkexEnvironment okexEnvironment,
            WebSocketStreamFilter webSocketStreamFilter,
            @Nullable Credentials credentials){
        super( okexEnvironment,
                webSocketStreamFilter,
                subscribeOpBuilder,
                credentials);
        this.spotPublicDataListenerContainer = spotPublicDataListenerContainer;

    }

    @Override
    protected void handleEventFailureResponse(WebSocketSession session, FailureResponse eventFailure) {
        // TODO: ...
        spotPublicDataListenerContainer.fireErrorListener(eventFailure);
    }

    @Override
    protected  void handleDataResponse(JsonNode root) throws JsonProcessingException {

        SpotWebsocketResponse respRet = objectMapper.treeToValue(root,SpotWebsocketResponse.class);
        switch((SpotChannel)respRet.getChannel()){
            case TICKER:
                processTicker(((SpotWebsocketTickerResponse)respRet).getData());
                break;
            case TRADE:
                processTrade(((SpotWebsocketTradeResponse)respRet).getData());
                break;
            case DEPTH:
                processDepth400(((SpotWebsocketDepthResponse)respRet).getData(),((SpotWebsocketDepthResponse)respRet).getAction());
                break;
            case DEPTH5:
                processDepth5(((SpotWebsocketDepth5Response)respRet).getData());
                break;
            case DEPTH_L2_TBT:
                processDepthL2Tbt(((SpotWebsocketDepthL2TbtResponse)respRet).getData(),((SpotWebsocketDepthL2TbtResponse)respRet).getAction());
                break;
            case CANDLE60S:
                processCandle60s(((SpotWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE180S:
                processCandle180s(((SpotWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE300S:
                processCandle300s(((SpotWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE900S:
                processCandle900s(((SpotWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE1800S:
                processCandle1800s(((SpotWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE3600S:
                processCandle3600s(((SpotWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE7200S:
                processCandle7200s(((SpotWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE14400S:
                processCandle14400s(((SpotWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE21600S:
                processCandle21600s(((SpotWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE43200S:
                processCandle43200s(((SpotWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE86400S:
                processCandle86400s(((SpotWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE604800S:
                processCandle604800s(((SpotWebsocketCandleResponse)respRet).getData());
                break;
        }

    }


    private void processTicker( List<SpotTickerData> dataList) {
        for(SpotTickerData data:dataList) {
            log.debug("processTicker data:{}", data);
            spotPublicDataListenerContainer.fireTickerListener(data.getInstrumentId(),data);
        }
    }

    private void processDepth5(List<SpotDepthData> dataList) {
        for(SpotDepthData data:dataList) {
            log.debug("processDepth5 data:{}", data);
            spotPublicDataListenerContainer.fireDepth5Listener(data.getInstrumentId(),data);
        }
    }

    private void processDepth400(List<SpotDepthData> dataList, DepthAction action) {
        for(SpotDepthData data:dataList) {
            log.debug("processDepth400 data:{}", data);
            spotPublicDataListenerContainer.fireDepth400Listener(data.getInstrumentId(),data,action);
        }
    }

    private void processDepthL2Tbt(List<SpotDepthData> dataList, DepthAction action) {
        for(SpotDepthData data:dataList) {
            log.debug("processDepthL2Tbt data:{}", data);
            spotPublicDataListenerContainer.fireDepthL2TbtListener(data.getInstrumentId(),data,action);
        }
    }

    private void processTrade(List<SpotTradeData> dataList){
        for(SpotTradeData data:dataList) {
            log.debug("processTrade data:{}", data);
            spotPublicDataListenerContainer.fireTradeListener(data.getInstrumentId(),data);
        }
    }

    private void processCandle60s(List<SpotCandleData> dataList) {
        for(SpotCandleData data:dataList) {
            log.debug("processCandle60s data:{}", data);
            spotPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(),CandleInterval.MINUTES_1, data);
        }
    }
    private void processCandle180s(List<SpotCandleData> dataList) {
        for(SpotCandleData data:dataList) {
            log.debug("processCandle180s data:{}", data);
            spotPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(),CandleInterval.MINUTES_3, data);
        }
    }
    private void processCandle300s(List<SpotCandleData> dataList) {
        for(SpotCandleData data:dataList) {
            log.debug("processCandle300s data:{}", data);
            spotPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(),CandleInterval.MINUTES_5, data);
        }
    }
    private void processCandle900s(List<SpotCandleData> dataList) {
        for(SpotCandleData data:dataList) {
            log.debug("processCandle900s data:{}", data);
            spotPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(),CandleInterval.MINUTES_15, data);
        }
    }
    private void processCandle1800s(List<SpotCandleData> dataList) {
        for(SpotCandleData data:dataList) {
            log.debug("processCandle1800s data:{}", data);
            spotPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(),CandleInterval.MINUTES_30, data);
        }
    }
    private void processCandle3600s(List<SpotCandleData> dataList) {
        for(SpotCandleData data:dataList) {
            log.debug("processCandle3600s data:{}", data);
            spotPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(),CandleInterval.MINUTES_60, data);
        }
    }
    private void processCandle7200s(List<SpotCandleData> dataList) {
        for(SpotCandleData data:dataList) {
            log.debug("processCandle7200s data:{}", data);
            spotPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(),CandleInterval.HOUR_2, data);
        }
    }
    private void processCandle14400s(List<SpotCandleData> dataList) {
        for(SpotCandleData data:dataList) {
            log.debug("processCandle14400s data:{}", data);
            spotPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(),CandleInterval.HOUR_4, data);
        }
    }
    private void processCandle21600s(List<SpotCandleData> dataList) {
        for(SpotCandleData data:dataList) {
            log.debug("processCandle21600s data:{}", data);
            spotPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(),CandleInterval.HOUR_6, data);
        }
    }
    private void processCandle43200s(List<SpotCandleData> dataList){
        for(SpotCandleData data:dataList) {
            log.debug("processCandle43200s data:{}", data);
            spotPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(),CandleInterval.HOUR_12, data);
        }
    }
    private void processCandle86400s(List<SpotCandleData> dataList){
        for(SpotCandleData data:dataList) {
            log.debug("processCandle43200s data:{}", data);
            spotPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(),CandleInterval.DAY_1, data);
        }
    }
    private void processCandle604800s(List<SpotCandleData> dataList) {
        for(SpotCandleData data:dataList) {
            log.debug("processCandle604800s data:{}", data);
            spotPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(),CandleInterval.DAY_7, data);
        }
    }

}
