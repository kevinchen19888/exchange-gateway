package com.kevin.gateway.okexapi.future.websocket.event;

import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.base.util.DepthAction;
import com.kevin.gateway.okexapi.base.util.DepthEntries;
import com.kevin.gateway.okexapi.base.websocket.ErrorListener;
import com.kevin.gateway.okexapi.base.websocket.response.FailureResponse;
import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.vo.*;
import com.kevin.gateway.okexapi.future.vo.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *   公共数据监听者容器，无需登录即可监听
 */
public class FuturePublicDataListenerContainer {
    private final List<FutureTickerListener> tickerListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<FutureTradeListener> tradeListeners = Collections.synchronizedList(new ArrayList<>());
    private final Map<DepthEntries, List<FutureDepthListener> > depthListeners = new ConcurrentHashMap<>();
    private final Map<CandleInterval,List<FutureCandleListener>> candleListeners = new ConcurrentHashMap<>();
    private final List<FutureInstrumentListener> instrumentListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<FutureMarkPriceListener> markPriceListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<FuturePriceRangeListener> priceRangeListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<FutureEstimatedPriceListener> estimatedPriceListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<ErrorListener> errorListeners = Collections.synchronizedList(new ArrayList<>());

    public FuturePublicDataListenerContainer(){
        for(DepthEntries depthEntries: DepthEntries.values()){
            depthListeners.put(depthEntries, new ArrayList<>());
        }

        for(CandleInterval candleInterval: CandleInterval.values()){
            candleListeners.put(candleInterval,new ArrayList<>());
        }
    }

    public void addErrorListeners(ErrorListener errorListener){

        for(ErrorListener listenerIter: errorListeners){
            if(listenerIter.equals(errorListener)){
                return;
            }
        }errorListeners.add(errorListener);
    }

    public void addInstrumentListeners(FutureInstrumentListener instrumentListener){
        for(FutureInstrumentListener listenerIter: instrumentListeners){
            if(listenerIter.equals(instrumentListener)){
                return;
            }
        }
        instrumentListeners.add(instrumentListener);
    }

    public void addMarkPriceListener(FutureMarkPriceListener markPriceListener){
        for(FutureMarkPriceListener listenerIter: markPriceListeners){
            if(listenerIter.equals(markPriceListener)){
                return;
            }
        }
        markPriceListeners.add(markPriceListener);
    }

    public void addPriceRangeListener(FuturePriceRangeListener priceRangeListener){
        for(FuturePriceRangeListener listenerIter: priceRangeListeners){
            if(listenerIter.equals(priceRangeListener)){
                return;
            }
        }
        priceRangeListeners.add(priceRangeListener);
    }

    public void addEstimatedPriceListener(FutureEstimatedPriceListener estimatedPriceListener){
        for(FutureEstimatedPriceListener listenerIter: estimatedPriceListeners){
            if(listenerIter.equals(estimatedPriceListener)){
                return;
            }
        }
        estimatedPriceListeners.add(estimatedPriceListener);
    }

    public void addTickerListener(FutureTickerListener futureTickerListener){
        for(FutureTickerListener listenerIter: tickerListeners){
            if(listenerIter.equals(futureTickerListener)){
                return;
            }
        }
        tickerListeners.add(futureTickerListener);
    }

    public void addCandleListener(CandleInterval candlePeriod, FutureCandleListener futureCandleListener){
        List<FutureCandleListener> candleListnerList = candleListeners.get(candlePeriod);
        for(FutureCandleListener listenerIter: candleListnerList){
            if(listenerIter.equals(futureCandleListener)){
                return;
            }
        }
        candleListnerList.add(futureCandleListener);
    }

    public void addTradeListener( FutureTradeListener futureTradeListener){

        for(FutureTradeListener listenerIter: tradeListeners){
            if(listenerIter.equals(futureTradeListener)){
                return;
            }
        }
        tradeListeners.add(futureTradeListener);
    }


    public void addDepthListener(DepthEntries depthEntries, FutureDepthListener futureDepthListener){
        List<FutureDepthListener> depthListnerList = depthListeners.get(depthEntries);
        for(FutureDepthListener listenerIter: depthListnerList){
            if(listenerIter.equals(futureDepthListener)){
                return;
            }
        }
        depthListnerList.add(futureDepthListener);
    }


    // 数据接收侧处理
    public void fireCandleListener(FutureMarketId id, CandleInterval candlePeriod, FutureCandleData candleData){
        List<FutureCandleListener> candleListnerList = candleListeners.get(candlePeriod);
        if(candleListnerList!=null) {
            for (FutureCandleListener candleListener : candleListnerList) {
                candleListener.handleCandleData(id, candlePeriod,candleData);
            }
        }
    }

    public void fireTickerListener(FutureMarketId id, FutureTickerData tickerData){
        for(FutureTickerListener tickerListener: tickerListeners){
            tickerListener.handleTickerData(id,tickerData);
        }
    }

    public void fireTradeListener(FutureMarketId id, FutureTradeData tradeData){
        for(FutureTradeListener tradeListener: tradeListeners){
            tradeListener.handleTradeData(id,tradeData);
        }
    }

    public void fireDepth5Listener(FutureMarketId id, FutureDepthData depth5Data){
        for(FutureDepthListener depth5Listener: depthListeners.get(DepthEntries.DEPTH5)){
            depth5Listener.handleDepthData(id,depth5Data,DepthEntries.DEPTH5,DepthAction.PARTIAL);
        }
    }
    public void fireDepth400Listener(FutureMarketId id, FutureDepthData depth400Data,DepthAction action){
        for(FutureDepthListener depth400Listener: depthListeners.get(DepthEntries.DEPTH)){
            depth400Listener.handleDepthData(id,depth400Data,DepthEntries.DEPTH,action);
        }
    }
    public void fireDepthL2TbtListener(FutureMarketId id, FutureDepthData depthL2TbtData,DepthAction action){
        for(FutureDepthListener depthL2TbtListener: depthListeners.get(DepthEntries.DEPTH_L2_TBT)){
            depthL2TbtListener.handleDepthData(id,depthL2TbtData,DepthEntries.DEPTH_L2_TBT, action);
        }
    }

    public void fireInstrumentListener(FutureMarketId id, FutureInstrumentData InstrumentData){
        for(FutureInstrumentListener instrumentListener: instrumentListeners){
            instrumentListener.handleInstrumentData(id,InstrumentData);
        }
    }

    public void fireEstimatedPriceListener(FutureMarketId id, FutureEstimatedPriceData estimatedPriceData){
        for(FutureEstimatedPriceListener estimatedPriceListener:estimatedPriceListeners){
            estimatedPriceListener.handleEstimatedPriceData(id,estimatedPriceData);
        }
    }

    public void fireMarkPriceListener(FutureMarketId id, FutureMarkPriceData markPriceData){
        for(FutureMarkPriceListener markPriceListener: markPriceListeners){
            markPriceListener.handleMarketPriceData(id, markPriceData);
        }
    }

    public void firePriceRangeListener(FutureMarketId id, FuturePriceRangeData priceRangeData){
        for(FuturePriceRangeListener priceRangeListener: priceRangeListeners){
            priceRangeListener.handlePriceRangeData(id, priceRangeData);
        }
    }

    public void fireErrorListener(FailureResponse failureResponse){
        for(ErrorListener errorListener: errorListeners){
            errorListener.handleErrorMessage(failureResponse);
        }
    }

}
