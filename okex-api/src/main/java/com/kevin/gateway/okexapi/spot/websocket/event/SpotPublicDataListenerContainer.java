package com.kevin.gateway.okexapi.spot.websocket.event;

import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.base.util.DepthAction;
import com.kevin.gateway.okexapi.base.util.DepthEntries;
import com.kevin.gateway.okexapi.base.websocket.ErrorListener;
import com.kevin.gateway.okexapi.base.websocket.response.FailureResponse;
import com.kevin.gateway.okexapi.spot.vo.*;
import com.kevin.gateway.okexapi.spot.SpotMarketId;
import com.kevin.gateway.okexapi.spot.vo.SpotCandleData;
import com.kevin.gateway.okexapi.spot.vo.SpotDepthData;
import com.kevin.gateway.okexapi.spot.vo.SpotTickerData;
import com.kevin.gateway.okexapi.spot.vo.SpotTradeData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 *   公共数据监听者容器，无需登录即可监听
 */
public class SpotPublicDataListenerContainer {
    private final List<SpotTickerListener> tickerListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<SpotTradeListener> tradeListeners = Collections.synchronizedList(new ArrayList<>());
    private final Map<CandleInterval,List<SpotCandleListener>> candleListeners = new ConcurrentHashMap<>();
    private final Map<DepthEntries,List<SpotDepthListener>> depthListeners = new ConcurrentHashMap<>();
    private final List<ErrorListener> errorListeners = Collections.synchronizedList(new ArrayList<>());

    public SpotPublicDataListenerContainer(){
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
        }
        errorListeners.add(errorListener);
    }


    public void addTickerListener(SpotTickerListener spotTickerListener){

        for(SpotTickerListener listenerIter: tickerListeners){
            if(listenerIter.equals(spotTickerListener)){
                return;
            }
        }
        tickerListeners.add(spotTickerListener);
    }

    public void addCandleListener(CandleInterval candlePeriod, SpotCandleListener spotCandleListener){
        List<SpotCandleListener> candleListenerList = candleListeners.get(candlePeriod);
        for(SpotCandleListener listenerIter: candleListenerList){
            if(listenerIter.equals(spotCandleListener)){
                return;
            }
        }

        candleListenerList.add(spotCandleListener);
    }

    public void addDepthListener(DepthEntries depthEntries, SpotDepthListener spotDepthListener){
        List<SpotDepthListener> depthListenerList = depthListeners.get(depthEntries);
        for(SpotDepthListener listenerIter: depthListenerList){
            if(listenerIter.equals(spotDepthListener)){
                return;
            }
        }

        depthListenerList.add(spotDepthListener);
    }

    public void addTradeListener( SpotTradeListener spotTradeListener){

        for(SpotTradeListener listenerIter: tradeListeners){
            if(listenerIter.equals(spotTradeListener)){
                return;
            }
        }
        tradeListeners.add(spotTradeListener);
    }


    // 数据接收侧处理
    public void fireCandleListener(SpotMarketId id, CandleInterval candlePeriod, SpotCandleData candleData){
        List<SpotCandleListener> candleListnerList = candleListeners.get(candlePeriod);
        if(candleListnerList!=null) {
            for (SpotCandleListener candleListener : candleListnerList) {
                candleListener.handleCandleData(id, candlePeriod,candleData);
            }
        }
    }

    public void fireTickerListener(SpotMarketId id, SpotTickerData tickerData){
        for(SpotTickerListener tickerListener: tickerListeners){
            tickerListener.handleTickerData(id,tickerData);
        }
    }

    public void fireTradeListener(SpotMarketId id, SpotTradeData tradeData){
        for(SpotTradeListener tradeListener: tradeListeners){
            tradeListener.handleTradeData(id,tradeData);
        }
    }

    public void fireDepth5Listener(SpotMarketId id, SpotDepthData depth5Data){
        for(SpotDepthListener depth5Listener: depthListeners.get(DepthEntries.DEPTH5)){
            depth5Listener.handleDepthData(id,depth5Data,DepthEntries.DEPTH5,DepthAction.PARTIAL);
        }
    }
    public void fireDepth400Listener(SpotMarketId id, SpotDepthData depth400Data,DepthAction action){
        for(SpotDepthListener depth400Listener: depthListeners.get(DepthEntries.DEPTH)){
            depth400Listener.handleDepthData(id,depth400Data,DepthEntries.DEPTH,action);
        }
    }
    public void fireDepthL2TbtListener(SpotMarketId id, SpotDepthData depthL2TbtData,DepthAction action){
        for(SpotDepthListener depthL2TbtListener: depthListeners.get(DepthEntries.DEPTH_L2_TBT)){
            depthL2TbtListener.handleDepthData(id,depthL2TbtData,DepthEntries.DEPTH_L2_TBT, action);
        }
    }

    public void fireErrorListener(FailureResponse failureResponse){
        for(ErrorListener errorListener: errorListeners){
            errorListener.handleErrorMessage(failureResponse);
        }
    }

}
