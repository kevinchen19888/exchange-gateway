package com.kevin.gateway.okexapi.option.websocket.event;

import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.base.util.DepthAction;
import com.kevin.gateway.okexapi.base.util.DepthEntries;
import com.kevin.gateway.okexapi.base.websocket.ErrorListener;
import com.kevin.gateway.okexapi.base.websocket.response.FailureResponse;
import com.kevin.gateway.okexapi.option.vo.*;
import com.kevin.gateway.okexapi.option.OptionMarketId;
import com.kevin.gateway.okexapi.option.vo.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *   公共数据监听者容器，无需登录即可监听
 */
public class OptionPublicDataListenerContainer {
    private final List<OptionTickerListener> tickerListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<OptionTradeListener> tradeListeners = Collections.synchronizedList(new ArrayList<>());
    private final Map<DepthEntries,List<OptionDepthListener>> depthListeners = new ConcurrentHashMap<>();
    private final Map<CandleInterval,List<OptionCandleListener>> candleListeners = new ConcurrentHashMap<>();
    private final List<OptionInstrumentListener> instrumentListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<OptionSummaryListener> summaryListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<ErrorListener> errorListeners = Collections.synchronizedList(new ArrayList<>());

    public OptionPublicDataListenerContainer(){
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

    public void addInstrumentListeners(OptionInstrumentListener instrumentListener){
        for(OptionInstrumentListener listenerIter: instrumentListeners){
            if(listenerIter.equals(instrumentListener)){
                return;
            }
        }

        instrumentListeners.add(instrumentListener);
    }

    public void addSummaryListeners(OptionSummaryListener summaryListener){

        for(OptionSummaryListener listenerIter: summaryListeners){
            if(listenerIter.equals(summaryListener)){
                return;
            }
        }
        summaryListeners.add(summaryListener);
    }

    public void addTickerListener(OptionTickerListener optionTickerListener){
        for(OptionTickerListener listenerIter: tickerListeners){
            if(listenerIter.equals(optionTickerListener)){
                return;
            }
        }

        tickerListeners.add(optionTickerListener);
    }

    public void addCandleListener(CandleInterval candlePeriod, OptionCandleListener optionCandleListener){
        List<OptionCandleListener> candleListnerList = candleListeners.get(candlePeriod);
        for(OptionCandleListener listenerIter: candleListnerList){
            if(listenerIter.equals(optionCandleListener)){
                return;
            }
        }

        candleListnerList.add(optionCandleListener);
    }

    public void addTradeListener( OptionTradeListener optionTradeListener){
        for(OptionTradeListener listenerIter: tradeListeners){
            if(listenerIter.equals(optionTradeListener)){
                return;
            }
        }

        tradeListeners.add(optionTradeListener);
    }

    public void addDepthListener(DepthEntries depthEntries, OptionDepthListener optionDepthListener){
        List<OptionDepthListener> depthListenerList = depthListeners.get(depthEntries);
        for(OptionDepthListener listenerIter: depthListenerList){
            if(listenerIter.equals(optionDepthListener)){
                return;
            }
        }

        depthListenerList.add(optionDepthListener);
    }

    // 数据接收侧处理
    public void fireCandleListener(OptionMarketId id, CandleInterval candlePeriod, OptionCandleData candleData){
        List<OptionCandleListener> candleListnerList = candleListeners.get(candlePeriod);
        if(candleListnerList!=null) {
            for (OptionCandleListener candleListener : candleListnerList) {
                candleListener.handleCandleData(id, candlePeriod,candleData);
            }
        }
    }

    public void fireTickerListener(OptionMarketId id, OptionTickerData tickerData){
        for(OptionTickerListener tickerListener: tickerListeners){
            tickerListener.handleTickerData(id,tickerData);
        }
    }

    public void fireTradeListener(OptionMarketId id, OptionTradeData tradeData){
        for(OptionTradeListener tradeListener: tradeListeners){
            tradeListener.handleTradeData(id,tradeData);
        }
    }


    public void fireDepth5Listener(OptionMarketId id, OptionDepthData depthData){
        for(OptionDepthListener depth5Listener: depthListeners.get(DepthEntries.DEPTH5)){
            depth5Listener.handleDepthData(id,depthData,DepthEntries.DEPTH5,DepthAction.PARTIAL);
        }
    }
    public void fireDepth400Listener(OptionMarketId id, OptionDepthData depthata, DepthAction action){
        for(OptionDepthListener depth400Listener: depthListeners.get(DepthEntries.DEPTH)){
            depth400Listener.handleDepthData(id,depthata,DepthEntries.DEPTH,action);
        }
    }
    public void fireDepthL2TbtListener(OptionMarketId id, OptionDepthData depthData, DepthAction action){
        for(OptionDepthListener depthL2TbtListener: depthListeners.get(DepthEntries.DEPTH_L2_TBT)){
            depthL2TbtListener.handleDepthData(id,depthData,DepthEntries.DEPTH_L2_TBT, action);
        }
    }

    public void fireInstrumentListener(OptionMarketId id, OptionInstrumentData InstrumentData){
        for(OptionInstrumentListener instrumentListener: instrumentListeners){
            instrumentListener.handleInstrumentData(id,InstrumentData);
        }
    }

    public void fireSummaryListener(OptionMarketId id, OptionSummaryData summaryData){
        for(OptionSummaryListener summaryListener: summaryListeners){
            summaryListener.handleSummaryData(id, summaryData);
        }
    }

    public void fireErrorListener(FailureResponse failureResponse){
        for(ErrorListener errorListener: errorListeners){
            errorListener.handleErrorMessage(failureResponse);
        }
    }

}
