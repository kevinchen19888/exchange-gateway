package com.kevin.gateway.okexapi.swap.websocket.event;

import com.kevin.gateway.okexapi.base.websocket.ErrorListener;
import com.kevin.gateway.okexapi.base.websocket.response.FailureResponse;
import com.kevin.gateway.okexapi.swap.websocket.response.AccountData;
import com.kevin.gateway.okexapi.swap.websocket.response.OrderAlgoData;
import com.kevin.gateway.okexapi.swap.websocket.response.OrderData;
import com.kevin.gateway.okexapi.swap.websocket.response.PositionData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SwapPrivateDataListenerContainer {

    private final List<SwapPositionListener> swapPositionListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<SwapAccountListener> swapAccountListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<SwapOrderListener> swapOrderListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<SwapOrderAlgoListener> swapOrderAlgoListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<ErrorListener> errorListeners = Collections.synchronizedList(new ArrayList<>());

    public void addPositionListener(SwapPositionListener swapPositionListener) {
        for(SwapPositionListener listenerIter: swapPositionListeners){
            if(listenerIter.equals(swapPositionListener)){
                return;
            }
        }

        swapPositionListeners.add(swapPositionListener);
    }

    public void addAccountListener(SwapAccountListener swapAccountListener) {
        for(SwapAccountListener listenerIter: swapAccountListeners){
            if(listenerIter.equals(swapAccountListener)){
                return;
            }
        }

        swapAccountListeners.add(swapAccountListener);
    }

    public void addOrderListener(SwapOrderListener swapOrderListener) {

        for(SwapOrderListener listenerIter: swapOrderListeners){
            if(listenerIter.equals(swapOrderListener)){
                return;
            }
        }
        swapOrderListeners.add(swapOrderListener);
    }

    public void addOrderAlgoListener(SwapOrderAlgoListener swapOrderAlgoListener) {
        for(SwapOrderAlgoListener listenerIter: swapOrderAlgoListeners){
            if(listenerIter.equals(swapOrderAlgoListener)){
                return;
            }
        }

        swapOrderAlgoListeners.add(swapOrderAlgoListener);
    }

    public void addErrorListeners(ErrorListener errorListener) {

        for(ErrorListener listenerIter: errorListeners){
            if(listenerIter.equals(errorListener)){
                return;
            }
        }
        errorListeners.add(errorListener);
    }

    // 数据接收侧处理
    public void firePositionListener(PositionData positionData) {
        for (SwapPositionListener swapPositionListener : swapPositionListeners) {
            swapPositionListener.handlePositionData(positionData);
        }
    }

    public void fireAccountListener(AccountData accountData) {
        for (SwapAccountListener swapAccountListener : swapAccountListeners) {
            swapAccountListener.handleAccountData(accountData);
        }
    }

    public void fireOrderListener(OrderData orderData) {
        for (SwapOrderListener swapOrderListener : swapOrderListeners) {
            swapOrderListener.handleOrderData(orderData);
        }
    }

    public void fireOrderAlgoListener(OrderAlgoData orderAlgoData) {
        for (SwapOrderAlgoListener swapOrderAlgoListener : swapOrderAlgoListeners) {
            swapOrderAlgoListener.handleOrderAlgoData(orderAlgoData);
        }
    }

    public void fireErrorListener(FailureResponse failureResponse) {
        for (ErrorListener errorListener : errorListeners) {
            errorListener.handleErrorMessage(failureResponse);
        }
    }

}
