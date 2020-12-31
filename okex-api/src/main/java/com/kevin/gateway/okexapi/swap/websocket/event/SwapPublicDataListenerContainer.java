package com.kevin.gateway.okexapi.swap.websocket.event;

import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.base.util.DepthAction;
import com.kevin.gateway.okexapi.base.util.DepthEntries;
import com.kevin.gateway.okexapi.base.websocket.ErrorListener;
import com.kevin.gateway.okexapi.base.websocket.response.FailureResponse;
import com.kevin.gateway.okexapi.swap.vo.*;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import com.kevin.gateway.okexapi.swap.vo.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *   公共数据监听者容器，无需登录即可监听
 */
public class SwapPublicDataListenerContainer {
    private final List<SwapTickerListener> tickerListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<SwapTradeListener> tradeListeners = Collections.synchronizedList(new ArrayList<>());
    private final Map<CandleInterval,List<SwapCandleListener>> candleListeners = new ConcurrentHashMap<>();
    private final Map<DepthEntries,List<SwapDepthListener>> depthListeners = new ConcurrentHashMap<>();
    private final List<SwapMarkPriceListener> markPriceListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<SwapPriceRangeListener> priceRangeListeners =Collections.synchronizedList( new ArrayList<>());
    private final List<SwapFundingRateListener> fundingRateListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<ErrorListener> errorListeners = Collections.synchronizedList(new ArrayList<>());

    public SwapPublicDataListenerContainer(){
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

    public void addMarkPriceListener(SwapMarkPriceListener markPriceListener){
        for(SwapMarkPriceListener listenerIter: markPriceListeners){
            if(listenerIter.equals(markPriceListener)){
                return;
            }
        }

        markPriceListeners.add(markPriceListener);
    }

    public void addPriceRangeListener(SwapPriceRangeListener priceRangeListener){
        for(SwapPriceRangeListener listenerIter: priceRangeListeners){
            if(listenerIter.equals(priceRangeListener)){
                return;
            }
        }

        priceRangeListeners.add(priceRangeListener);
    }

    public void addFindingRateListener(SwapFundingRateListener fundingRateListener){
        for(SwapFundingRateListener listenerIter: fundingRateListeners){
            if(listenerIter.equals(fundingRateListener)){
                return;
            }
        }

        fundingRateListeners.add(fundingRateListener);
    }

    public void addTickerListener(SwapTickerListener swapTickerListener){

        for(SwapTickerListener listenerIter: tickerListeners){
            if(listenerIter.equals(swapTickerListener)){
                return;
            }
        }
        tickerListeners.add(swapTickerListener);
    }

    public void addCandleListener(CandleInterval candlePeriod, SwapCandleListener swapCandleListener){
        List<SwapCandleListener> candleListenerList = candleListeners.get(candlePeriod);
        for(SwapCandleListener listenerIter: candleListenerList){
            if(listenerIter.equals(swapCandleListener)){
                return;
            }
        }

        candleListenerList.add(swapCandleListener);
    }

    public void addTradeListener( SwapTradeListener swapTradeListener){

        for(SwapTradeListener listenerIter: tradeListeners){
            if(listenerIter.equals(swapTradeListener)){
                return;
            }
        }
        tradeListeners.add(swapTradeListener);
    }

    public void addDepthListener(DepthEntries depthEntries, SwapDepthListener swapDepthListener){
        List<SwapDepthListener> depthListenerList = depthListeners.get(depthEntries);
        for(SwapDepthListener listenerIter: depthListenerList){
            if(listenerIter.equals(swapDepthListener)){
                return;
            }
        }

        depthListenerList.add(swapDepthListener);
    }

    // 数据接收侧处理
    public void fireCandleListener(SwapMarketId id, CandleInterval candlePeriod, SwapCandleData candleData){
        List<SwapCandleListener> candleListnerList = candleListeners.get(candlePeriod);
        if(candleListnerList!=null) {
            for (SwapCandleListener candleListener : candleListnerList) {
                candleListener.handleCandleData(id, candlePeriod,candleData);
            }
        }
    }

    public void fireTickerListener(SwapMarketId id, SwapTickerData tickerData){
        for(SwapTickerListener tickerListener: tickerListeners){
            tickerListener.handleTickerData(id,tickerData);
        }
    }

    public void fireTradeListener(SwapMarketId id, SwapTradeData tradeData){
        for(SwapTradeListener tradeListener: tradeListeners){
            tradeListener.handleTradeData(id,tradeData);
        }
    }

    public void fireDepth5Listener(SwapMarketId id, SwapDepthData depthData){
        for(SwapDepthListener depth5Listener: depthListeners.get(DepthEntries.DEPTH5)){
            depth5Listener.handleDepthData(id,depthData,DepthEntries.DEPTH5,DepthAction.PARTIAL);
        }
    }
    public void fireDepth400Listener(SwapMarketId id, SwapDepthData depthata,DepthAction action){
        for(SwapDepthListener depth400Listener: depthListeners.get(DepthEntries.DEPTH)){
            depth400Listener.handleDepthData(id,depthata,DepthEntries.DEPTH,action);
        }
    }
    public void fireDepthL2TbtListener(SwapMarketId id, SwapDepthData depthData,DepthAction action){
        for(SwapDepthListener depthL2TbtListener: depthListeners.get(DepthEntries.DEPTH_L2_TBT)){
            depthL2TbtListener.handleDepthData(id,depthData,DepthEntries.DEPTH_L2_TBT, action);
        }
    }

    public void fireFindingRateListener(SwapMarketId id, SwapFundingRateData fundingRateData){
        for(SwapFundingRateListener fundingRateListener:fundingRateListeners){
            fundingRateListener.handleFindingRateData(id,fundingRateData);
        }
    }

    public void fireMarkPriceListener(SwapMarketId id, SwapMarkPriceData markPriceData){
        for(SwapMarkPriceListener markPriceListener: markPriceListeners){
            markPriceListener.handleMarkPriceData(id, markPriceData);
        }
    }

    public void firePriceRangeListener(SwapMarketId id, SwapPriceRangeData priceRangeData){
        for(SwapPriceRangeListener priceRangeListener: priceRangeListeners){
            priceRangeListener.handlePriceRangeData(id, priceRangeData);
        }
    }

    public void fireErrorListener(FailureResponse failureResponse){
        for(ErrorListener errorListener: errorListeners){
            errorListener.handleErrorMessage(failureResponse);
        }
    }

}
