package com.kevin.gateway.okexapi.option.websocket;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.base.util.DepthAction;
import com.kevin.gateway.okexapi.base.websocket.WebSocketStreamFilter;
import com.kevin.gateway.okexapi.base.websocket.WebSocketTemplate;
import com.kevin.gateway.okexapi.base.websocket.request.SubscribeOpBuilder;
import com.kevin.gateway.okexapi.base.websocket.response.FailureResponse;
import com.kevin.gateway.okexapi.option.vo.*;
import com.kevin.gateway.okexapi.option.websocket.response.*;
import com.kevin.gateway.okexapi.option.websocket.event.OptionPublicDataListenerContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Slf4j
public final class OptionPublicDataWsTemplate extends WebSocketTemplate {
    private final OptionPublicDataListenerContainer optionPublicDataListenerContainer;

    public OptionPublicDataWsTemplate(
            OptionPublicDataListenerContainer optionPublicDataListenerContainer,
            SubscribeOpBuilder subscribeOpBuilder,
            OkexEnvironment okexEnvironment,
            WebSocketStreamFilter webSocketStreamFilter,
            @Nullable Credentials credentials){
        super(  okexEnvironment,
                webSocketStreamFilter,
                subscribeOpBuilder,
                credentials);
        this.optionPublicDataListenerContainer = optionPublicDataListenerContainer;

    }

    @Override
    protected  void handleDataResponse(JsonNode root) throws JsonProcessingException {

        OptionWebsocketResponse respRet = objectMapper.treeToValue(root,OptionWebsocketResponse.class);
        switch((OptionChannel)respRet.getChannel()){
            case TICKER:
                processTicker(((OptionWebsocketTickerResponse)respRet).getData());
                break;
            case TRADE:
                processTrade(((OptionWebsocketTradeResponse)respRet).getData());
                break;
            case DEPTH:
                processDepth400(((OptionWebsocketDepth400Response)respRet).getData(),((OptionWebsocketDepth400Response)respRet).getAction());
                break;
            case DEPTH5:
                processDepth5(((OptionWebsocketDepth5Response)respRet).getData());
                break;
            case DEPTH_L2_TBT:
                processDepthL2Tbt(((OptionWebsocketDepthL2TbtResponse)respRet).getData(),((OptionWebsocketDepthL2TbtResponse)respRet).getAction());
                break;
            case INSTRUMENTS:
                processInstrument(((OptionWebsocketInstrumentResponse)respRet).getData());
                break;
            case SUMMARY:
                processEstimatedPrice(((OptionWebsocketSummaryResponse)respRet).getData());
                break;
            case CANDLE60S:
                processCandle60s(((OptionWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE180S:
                processCandle180s(((OptionWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE300S:
                processCandle300s(((OptionWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE900S:
                processCandle900s(((OptionWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE1800S:
                processCandle1800s(((OptionWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE3600S:
                processCandle3600s(((OptionWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE7200S:
                processCandle7200s(((OptionWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE14400S:
                processCandle14400s(((OptionWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE21600S:
                processCandle21600s(((OptionWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE43200S:
                processCandle43200s(((OptionWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE86400S:
                processCandle86400s(((OptionWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE604800S:
                processCandle604800s(((OptionWebsocketCandleResponse)respRet).getData());
                break;
        }

    }

    @Override
    protected void handleEventFailureResponse(WebSocketSession session, FailureResponse eventFailure) {
        // TODO: ...
        optionPublicDataListenerContainer.fireErrorListener(eventFailure);
    }

    private void processTicker( List<OptionTickerData> dataList) {
        for(OptionTickerData data:dataList) {
            log.debug("processTicker data:{}", data);
            optionPublicDataListenerContainer.fireTickerListener(data.getInstrumentId(),data);
        }
    }

    private void processInstrument( List<OptionInstrumentData> dataList) {
        for(OptionInstrumentData data:dataList) {
            log.debug("processInstrument data:{}", data);
            optionPublicDataListenerContainer.fireInstrumentListener(data.getInstrumentId(),data);
        }
    }

    private void processEstimatedPrice( List<OptionSummaryData> dataList) {
        for(OptionSummaryData data:dataList) {
            log.debug("processEstimatedPrice data:{}", data);
            optionPublicDataListenerContainer.fireSummaryListener(data.getInstrumentId(),data);
        }
    }

    private void processDepth5(List<OptionDepthData> dataList) {
        for(OptionDepthData data:dataList) {
            log.debug("processDepth5 data:{}", data);
            optionPublicDataListenerContainer.fireDepth5Listener(data.getInstrumentId(),data);
        }
    }

    private void processDepth400(List<OptionDepthData> dataList, DepthAction action) {
        for(OptionDepthData data:dataList) {
            log.debug("processDepth400 data:{}", data);
            optionPublicDataListenerContainer.fireDepth400Listener(data.getInstrumentId(),data,action);
        }
    }

    private void processDepthL2Tbt(List<OptionDepthData> dataList,DepthAction action) {
        for(OptionDepthData data:dataList) {
            log.debug("processDepthL2Tbt data:{}", data);
            optionPublicDataListenerContainer.fireDepthL2TbtListener(data.getInstrumentId(),data,action);
        }
    }

    private void processTrade(List<OptionTradeData> dataList){
        for(OptionTradeData data:dataList) {
            log.debug("processTrade data:{}", data);
            optionPublicDataListenerContainer.fireTradeListener(data.getInstrumentId(), data);
        }
    }

    private void processCandle60s(List<OptionCandleData> dataList) {
        for(OptionCandleData data:dataList) {
            log.debug("processCandle60s data:{}", data);
            optionPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_1, data);
        }
    }
    private void processCandle180s(List<OptionCandleData> dataList) {
        for(OptionCandleData data:dataList) {
            log.debug("processCandle180s data:{}", data);
            optionPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_3, data);
        }
    }
    private void processCandle300s(List<OptionCandleData> dataList) {
        for(OptionCandleData data:dataList) {
            log.debug("processCandle300s data:{}", data);
            optionPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_5, data);
        }
    }
    private void processCandle900s(List<OptionCandleData> dataList) {
        for(OptionCandleData data:dataList) {
            log.debug("processCandle900s data:{}", data);
            optionPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_15, data);
        }
    }
    private void processCandle1800s(List<OptionCandleData> dataList) {
        for(OptionCandleData data:dataList) {
            log.debug("processCandle1800s data:{}", data);
            optionPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_30, data);
        }
    }
    private void processCandle3600s(List<OptionCandleData> dataList) {
        for(OptionCandleData data:dataList) {
            log.debug("processCandle3600s data:{}", data);
            optionPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_60, data);
        }
    }
    private void processCandle7200s(List<OptionCandleData> dataList) {
        for(OptionCandleData data:dataList) {
            log.debug("processCandle7200s data:{}", data);
            optionPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.HOUR_2, data);
        }
    }
    private void processCandle14400s(List<OptionCandleData> dataList) {
        for(OptionCandleData data:dataList) {
            log.debug("processCandle14400s data:{}", data);
            optionPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.HOUR_4, data);
        }
    }
    private void processCandle21600s(List<OptionCandleData> dataList) {
        for(OptionCandleData data:dataList) {
            log.debug("processCandle21600sdata:{}", data);
            optionPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.HOUR_6, data);
        }
    }
    private void processCandle43200s(List<OptionCandleData> dataList){
        for(OptionCandleData data:dataList) {
            log.debug("processCandle43200s data:{}", data);
            optionPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.HOUR_12, data);
        }
    }
    private void processCandle86400s(List<OptionCandleData> dataList){
        for(OptionCandleData data:dataList) {
            log.debug("processCandle86400s data:{}", data);
            optionPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.DAY_1, data);
        }
    }
    private void processCandle604800s(List<OptionCandleData> dataList) {
        for(OptionCandleData data:dataList) {
            log.debug("processCandle604800s data:{}", data);
            optionPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.DAY_7, data);
        }
    }

}
