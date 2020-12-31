package com.kevin.gateway.okexapi.future.websocket;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.base.util.DepthAction;
import com.kevin.gateway.okexapi.base.websocket.WebSocketStreamFilter;
import com.kevin.gateway.okexapi.base.websocket.WebSocketTemplate;
import com.kevin.gateway.okexapi.base.websocket.request.SubscribeOpBuilder;
import com.kevin.gateway.okexapi.base.websocket.response.FailureResponse;
import com.kevin.gateway.okexapi.future.vo.*;
import com.kevin.gateway.okexapi.future.websocket.event.FuturePublicDataListenerContainer;
import com.kevin.gateway.okexapi.future.websocket.response.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Slf4j
public final class FuturePublicDataWsTemplate extends WebSocketTemplate {
    private final FuturePublicDataListenerContainer futurePublicDataListenerContainer;

    public FuturePublicDataWsTemplate(
            FuturePublicDataListenerContainer futurePublicDataListenerContainer,
            SubscribeOpBuilder subscribeOpBuilder,
            OkexEnvironment okexEnvironment,
            WebSocketStreamFilter webSocketStreamFilter,
            @Nullable Credentials credentials) {
        super(okexEnvironment, webSocketStreamFilter, subscribeOpBuilder, credentials);
        this.futurePublicDataListenerContainer = futurePublicDataListenerContainer;

    }

    @Override
    protected void handleDataResponse(JsonNode root) throws JsonProcessingException {

        FutureWebsocketResponse respRet = objectMapper.treeToValue(root, FutureWebsocketResponse.class);
        switch ((FutureChannel) respRet.getChannel()) {
            case TICKER:
                processTicker(((FutureWebsocketTickerResponse) respRet).getData());
                break;
            case TRADE:
                processTrade(((FutureWebsocketTradeResponse) respRet).getData());
                break;
            case DEPTH:
                processDepth400(((FutureWebsocketDepth400Response) respRet).getData(),((FutureWebsocketDepth400Response) respRet).getAction());
                break;
            case DEPTH5:
                processDepth5(((FutureWebsocketDepth5Response) respRet).getData());
                break;
            case DEPTH_L2_TBT:
                processDepthL2Tbt(((FutureWebsocketDepthL2TbtResponse) respRet).getData(),((FutureWebsocketDepthL2TbtResponse) respRet).getAction());
                break;
            case INSTRUMENTS:
                processInstrument(((FutureWebsocketInstrumentResponse) respRet).getData());
                break;
            case MARK_PRICE:
                processMarkPrice(((FutureWebsocketMarkPriceResponse) respRet).getData());
                break;
            case PRICE_RANGE:
                processPriceRange(((FutureWebsocketPriceRangeResponse) respRet).getData());
                break;
            case ESTIMATED_PRICE:
                processEstimatedPrice(((FutureWebsocketEstimatedPriceResponse) respRet).getData());
                break;
            case CANDLE60S:
                processCandle60s(((FutureWebsocketCandleResponse) respRet).getData());
                break;
            case CANDLE180S:
                processCandle180s(((FutureWebsocketCandleResponse) respRet).getData());
                break;
            case CANDLE300S:
                processCandle300s(((FutureWebsocketCandleResponse) respRet).getData());
                break;
            case CANDLE900S:
                processCandle900s(((FutureWebsocketCandleResponse) respRet).getData());
                break;
            case CANDLE1800S:
                processCandle1800s(((FutureWebsocketCandleResponse) respRet).getData());
                break;
            case CANDLE3600S:
                processCandle3600s(((FutureWebsocketCandleResponse) respRet).getData());
                break;
            case CANDLE7200S:
                processCandle7200s(((FutureWebsocketCandleResponse) respRet).getData());
                break;
            case CANDLE14400S:
                processCandle14400s(((FutureWebsocketCandleResponse) respRet).getData());
                break;
            case CANDLE21600S:
                processCandle21600s(((FutureWebsocketCandleResponse) respRet).getData());
                break;
            case CANDLE43200S:
                processCandle43200s(((FutureWebsocketCandleResponse) respRet).getData());
                break;
            case CANDLE86400S:
                processCandle86400s(((FutureWebsocketCandleResponse)respRet).getData());
                break;
            case CANDLE604800S:
                processCandle604800s(((FutureWebsocketCandleResponse) respRet).getData());
                break;
        }
    }

    @Override
    protected void handleEventFailureResponse(WebSocketSession session, FailureResponse eventFailure) {
        // TODO: ...
        futurePublicDataListenerContainer.fireErrorListener(eventFailure);
    }

    private void processTicker(List<FutureTickerData> dataList) {
        for (FutureTickerData data : dataList) {
            log.debug("processTicker:{}", data);
            futurePublicDataListenerContainer.fireTickerListener(data.getInstrumentId(), data);
        }
    }

    private void processInstrument(List<FutureInstrumentData> dataList) {
        for (FutureInstrumentData data : dataList) {
            log.debug("processInstrument:{}", data);
            futurePublicDataListenerContainer.fireInstrumentListener(data.getInstrumentId(), data);
        }
    }

    private void processMarkPrice(List<FutureMarkPriceData> dataList) {
        for (FutureMarkPriceData data : dataList) {
            log.debug("processMarkPrice:{}", data);
            futurePublicDataListenerContainer.fireMarkPriceListener(data.getInstrumentId(), data);
        }
    }

    private void processPriceRange(List<FuturePriceRangeData> dataList) {
        for (FuturePriceRangeData data : dataList) {
            log.debug("processPriceRange:{}", data);
            futurePublicDataListenerContainer.firePriceRangeListener(data.getInstrumentId(), data);
        }
    }

    private void processEstimatedPrice(List<FutureEstimatedPriceData> dataList) {
        for (FutureEstimatedPriceData data : dataList) {
            log.debug("processEstimatedPrice:{}", data);
            futurePublicDataListenerContainer.fireEstimatedPriceListener(data.getInstrumentId(), data);
        }
    }

    private void processDepth5(List<FutureDepthData> dataList) {
        for (FutureDepthData data : dataList) {
            log.debug("processDepth5:{}", data);
            futurePublicDataListenerContainer.fireDepth5Listener(data.getInstrumentId(), data);
        }
    }

    private void processDepth400(List<FutureDepthData> dataList,DepthAction action) {
        for (FutureDepthData data : dataList) {
            log.debug("processDepth400:{}", data);
            futurePublicDataListenerContainer.fireDepth400Listener(data.getInstrumentId(), data,action);
        }
    }

    private void processDepthL2Tbt(List<FutureDepthData> dataList, DepthAction action) {
        for (FutureDepthData data : dataList) {
            log.debug("data:{}", data);
            futurePublicDataListenerContainer.fireDepthL2TbtListener(data.getInstrumentId(), data, action);
        }
    }

    private void processTrade(List<FutureTradeData> dataList) {
        for (FutureTradeData data : dataList) {
            log.debug("processTrade:{}", data);
            futurePublicDataListenerContainer.fireTradeListener(data.getInstrumentId(), data);
        }
    }

    private void processCandle60s(List<FutureCandleData> dataList) {
        for (FutureCandleData data : dataList) {
            log.debug("processCandle60s:{}", data);
            futurePublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_1, data);
        }
    }

    private void processCandle180s(List<FutureCandleData> dataList) {
        for (FutureCandleData data : dataList) {
            log.debug("processCandle180s:{}", data);
            futurePublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_3, data);
        }
    }

    private void processCandle300s(List<FutureCandleData> dataList) {
        for (FutureCandleData data : dataList) {
            log.debug("processCandle300s:{}", data);
            futurePublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_5, data);
        }
    }

    private void processCandle900s(List<FutureCandleData> dataList) {
        for (FutureCandleData data : dataList) {
            log.debug("processCandle900s:{}", data);
            futurePublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_15, data);
        }
    }

    private void processCandle1800s(List<FutureCandleData> dataList) {
        for (FutureCandleData data : dataList) {
            log.debug("processCandle1800s:{}", data);
            futurePublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_30, data);
        }
    }

    private void processCandle3600s(List<FutureCandleData> dataList) {
        for (FutureCandleData data : dataList) {
            log.debug("processCandle3600s:{}", data);
            futurePublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.MINUTES_60, data);
        }
    }

    private void processCandle7200s(List<FutureCandleData> dataList) {
        for (FutureCandleData data : dataList) {
            log.debug("processCandle7200s:{}", data);
            futurePublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.HOUR_2, data);
        }
    }

    private void processCandle14400s(List<FutureCandleData> dataList) {
        for (FutureCandleData data : dataList) {
            log.debug("processCandle14400s:{}", data);
            futurePublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.HOUR_4, data);
        }
    }

    private void processCandle21600s(List<FutureCandleData> dataList) {
        for (FutureCandleData data : dataList) {
            log.debug("processCandle21600s:{}", data);
            futurePublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.HOUR_6, data);
        }
    }

    private void processCandle43200s(List<FutureCandleData> dataList) {
        for (FutureCandleData data : dataList) {
            log.debug("processCandle43200s:{}", data);
            futurePublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.HOUR_12, data);
        }
    }
    private void processCandle86400s(List<FutureCandleData> dataList){
        for(FutureCandleData data:dataList) {
            log.debug("processCandle43200s:{}", data);
            futurePublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.DAY_1, data);
        }
    }
    private void processCandle604800s(List<FutureCandleData> dataList) {
        for (FutureCandleData data : dataList) {
            log.debug("processCandle604800s:{}", data);
            futurePublicDataListenerContainer.fireCandleListener(data.getInstrumentId(), CandleInterval.DAY_7, data);
        }
    }

}
