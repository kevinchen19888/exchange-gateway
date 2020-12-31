package com.kevin.gateway.okexapi.index.websocket.event;

import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.base.websocket.ErrorListener;
import com.kevin.gateway.okexapi.base.websocket.response.FailureResponse;
import com.kevin.gateway.okexapi.index.vo.*;
import com.kevin.gateway.okexapi.index.IndexInstrumentId;
import com.kevin.gateway.okexapi.index.vo.IndexCandleData;
import com.kevin.gateway.okexapi.index.vo.IndexTickerData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *   公共数据监听者容器，无需登录即可监听
 */
public class IndexPublicDataListenerContainer {
    private final List<IndexTickerListener> tickerListeners = Collections.synchronizedList(new ArrayList<>());
    private final Map<CandleInterval,List<IndexCandleListener>> candleListeners = new ConcurrentHashMap<>();
    private final List<ErrorListener> errorListeners = Collections.synchronizedList(new ArrayList<>());

    public IndexPublicDataListenerContainer(){
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


    public void addTickerListener(IndexTickerListener indexTickerListener){

        for(IndexTickerListener listenerIter: tickerListeners){
            if(listenerIter.equals(indexTickerListener)){
                return;
            }
        }
        tickerListeners.add(indexTickerListener);
    }

    public void addCandleListener(CandleInterval candlePeriod, IndexCandleListener indexCandleListener){
        List<IndexCandleListener> candleListnerList = candleListeners.get(candlePeriod);
        for(IndexCandleListener listenerIter: candleListnerList){
            if(listenerIter.equals(indexCandleListener)){
                return;
            }
        }
        candleListnerList.add(indexCandleListener);
    }


    // 数据接收侧处理
    public void fireCandleListener(IndexInstrumentId id, CandleInterval candlePeriod, IndexCandleData candleData){
        List<IndexCandleListener> candleListnerList = candleListeners.get(candlePeriod);
        if(candleListnerList!=null) {
            for (IndexCandleListener candleListener : candleListnerList) {
                candleListener.handleCandleData(id, candlePeriod,candleData);
            }
        }
    }

    public void fireTickerListener(IndexInstrumentId id, IndexTickerData tickerData){
        for(IndexTickerListener tickerListener: tickerListeners){
            tickerListener.handleTickerData(id,tickerData);
        }
    }

    public void fireErrorListener(FailureResponse failureResponse){
        for(ErrorListener errorListener: errorListeners){
            errorListener.handleErrorMessage(failureResponse);
        }
    }


}
