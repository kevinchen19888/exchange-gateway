package com.kevin.gateway.okexapi.swap.websocket;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.base.util.DepthAction;
import com.kevin.gateway.okexapi.base.websocket.WebSocketStreamFilter;
import com.kevin.gateway.okexapi.base.websocket.WebSocketTemplate;
import com.kevin.gateway.okexapi.base.websocket.request.SubscribeOpBuilder;
import com.kevin.gateway.okexapi.base.websocket.response.FailureResponse;
import com.kevin.gateway.okexapi.swap.vo.*;
import com.kevin.gateway.okexapi.swap.websocket.event.SwapPublicDataListenerContainer;
import com.kevin.gateway.okexapi.swap.websocket.response.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Slf4j
public final class SwapPublicDataWsTemplate extends WebSocketTemplate {
    private final SwapPublicDataListenerContainer swapPublicDataListenerContainer;

    public SwapPublicDataWsTemplate(
            SwapPublicDataListenerContainer swapPublicDataListenerContainer,
            SubscribeOpBuilder subscribeOpBuilder,
            OkexEnvironment okexEnvironment,
            WebSocketStreamFilter webSocketStreamFilter,
            @Nullable Credentials credentials){
        super(  okexEnvironment,
                webSocketStreamFilter,
                subscribeOpBuilder,
                credentials);
        this.swapPublicDataListenerContainer = swapPublicDataListenerContainer;

    }

    @Override
    protected void handleEventFailureResponse(WebSocketSession session, FailureResponse eventFailure) {
        // TODO: ...
        swapPublicDataListenerContainer.fireErrorListener(eventFailure);
    }

    @Override
    protected  void handleDataResponse(JsonNode root) throws JsonProcessingException {

        SwapWebsocketResponse respRet = objectMapper.treeToValue(root,SwapWebsocketResponse.class);
        switch((SwapChannel)respRet.getChannel()){
            case TICKER:
                processTicker(((SwapWebsocketTickerResponse)respRet).getData());
                break;
            case TRADE:
                processTrade(((SwapWebsocketTradeResponse)respRet).getData());
                break;
            case DEPTH:
                processDepth400(((SwapWebsocketDepth400Response)respRet).getData(), ((SwapWebsocketDepth400Response)respRet).getAction());
                break;
            case DEPTH5:
                processDepth5(((SwapWebsocketDepth5Response)respRet).getData());
                break;
            case DEPTH_L2_TBT:
                processDepthL2Tbt(((SwapWebsocketDepthL2TbtResponse)respRet).getData(), ((SwapWebsocketDepthL2TbtResponse)respRet).getAction());
                break;
            case MARK_PRICE:
                processMarkPrice(((SwapWebsocketMarkPriceResponse)respRet).getData());
                break;
            case PRICE_RANGE:
                processPriceRange(((SwapWebsocketPriceRangeResponse)respRet).getData());
                break;
            case FUNDING_RATE:
                processFindingRate(((SwapWebsocketFundingRateResponse)respRet).getData());
                break;
            case CANDLE60S:
                processCandle60s(((SwapWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE180S:
                processCandle180s(((SwapWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE300S:
                processCandle300s(((SwapWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE900S:
                processCandle900s(((SwapWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE1800S:
                processCandle1800s(((SwapWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE3600S:
                processCandle3600s(((SwapWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE7200S:
                processCandle7200s(((SwapWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE14400S:
                processCandle14400s(((SwapWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE21600S:
                processCandle21600s(((SwapWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE43200S:
                processCandle43200s(((SwapWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE86400S:
                processCandle86400s(((SwapWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE604800S:
                processCandle604800s(((SwapWebsocketCandleResponse)respRet).getData());
                break;
        }

    }

    private void processTicker( List<SwapTickerData> dataList) {
        for(SwapTickerData data:dataList) {
            log.debug("processTicker data:{}", data);
            swapPublicDataListenerContainer.fireTickerListener(data.getInstrumentId(),data);
        }
    }

    private void processMarkPrice( List<SwapMarkPriceData> dataList) {
        for(SwapMarkPriceData data:dataList) {
            log.debug("processMarkPrice data:{}", data);
            swapPublicDataListenerContainer.fireMarkPriceListener(data.getInstrumentId(),data);
        }
    }

    private void processPriceRange( List<SwapPriceRangeData> dataList) {
        for(SwapPriceRangeData data:dataList) {
            log.debug("processPriceRange data:{}", data);
            swapPublicDataListenerContainer.firePriceRangeListener(data.getInstrumentId(),data);
        }
    }

    private void processFindingRate( List<SwapFundingRateData> dataList) {
        for(SwapFundingRateData data:dataList) {
            log.debug("processFindingRate data:{}", data);
            swapPublicDataListenerContainer.fireFindingRateListener(data.getInstrumentId(),data);
        }
    }

    private void processDepth5(List<SwapDepthData> dataList) {
        for(SwapDepthData data:dataList) {
            log.debug("processDepth5 data:{}", data);
            swapPublicDataListenerContainer.fireDepth5Listener(data.getInstrumentId(),data);
        }
    }

    private void processDepth400(List<SwapDepthData> dataList, DepthAction action) {
        for(SwapDepthData data:dataList) {
            log.debug("processDepth400 data:{}", data);
            swapPublicDataListenerContainer.fireDepth400Listener(data.getInstrumentId(),data,action);
        }
    }

    private void processDepthL2Tbt(List<SwapDepthData> dataList,DepthAction action) {
        for(SwapDepthData data:dataList) {
            log.debug("processDepthL2Tbt data:{}", data);
            swapPublicDataListenerContainer.fireDepthL2TbtListener(data.getInstrumentId(),data,action);
        }
    }

    private void processTrade(List<SwapTradeData> dataList){
        for(SwapTradeData data:dataList) {
            log.debug("processTrade data:{}", data);
            swapPublicDataListenerContainer.fireTradeListener(data.getInstrumentId(), data);
        }
    }

    private void processCandle60s(List<SwapCandleData> dataList) {
        for(SwapCandleData data:dataList) {
            log.debug("processCandle60s data:{}", data);
            swapPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_1, data);
        }
    }
    private void processCandle180s(List<SwapCandleData> dataList) {
        for(SwapCandleData data:dataList) {
            log.debug("processCandle180s data:{}", data);
            swapPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_3, data);
        }
    }
    private void processCandle300s(List<SwapCandleData> dataList) {
        for(SwapCandleData data:dataList) {
            log.debug("processCandle300s data:{}", data);
            swapPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_5, data);
        }
    }
    private void processCandle900s(List<SwapCandleData> dataList) {
        for(SwapCandleData data:dataList) {
            log.debug("processCandle900s data:{}", data);
            swapPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_15, data);
        }
    }
    private void processCandle1800s(List<SwapCandleData> dataList) {
        for(SwapCandleData data:dataList) {
            log.debug("processCandle1800s data:{}", data);
            swapPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_30, data);
        }
    }
    private void processCandle3600s(List<SwapCandleData> dataList) {
        for(SwapCandleData data:dataList) {
            log.debug("processCandle3600s data:{}", data);
            swapPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_60, data);
        }
    }
    private void processCandle7200s(List<SwapCandleData> dataList) {
        for(SwapCandleData data:dataList) {
            log.debug("processCandle7200s data:{}", data);
            swapPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.HOUR_2, data);
        }
    }
    private void processCandle14400s(List<SwapCandleData> dataList) {
        for(SwapCandleData data:dataList) {
            log.debug("processCandle14400s data:{}", data);
            swapPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.HOUR_4, data);
        }
    }
    private void processCandle21600s(List<SwapCandleData> dataList) {
        for(SwapCandleData data:dataList) {
            log.debug("processCandle21600s data:{}", data);
            swapPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.HOUR_6, data);
        }
    }
    private void processCandle43200s(List<SwapCandleData> dataList){
        for(SwapCandleData data:dataList) {
            log.debug("processCandle43200s data:{}", data);
            swapPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.HOUR_12, data);
        }
    }
    private void processCandle86400s(List<SwapCandleData> dataList){
        for(SwapCandleData data:dataList) {
            log.debug("processCandle86400s data:{}", data);
            swapPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.DAY_1, data);
        }
    }
    private void processCandle604800s(List<SwapCandleData> dataList) {
        for(SwapCandleData data:dataList) {
            log.debug("processCandle604800s data:{}", data);
            swapPublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.DAY_7, data);
        }
    }

}
