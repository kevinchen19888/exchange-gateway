package com.kevin.gateway.okexapi.future.websocket.event;


import com.kevin.gateway.okexapi.base.websocket.ErrorListener;
import com.kevin.gateway.okexapi.base.websocket.response.FailureResponse;
import com.kevin.gateway.okexapi.future.websocket.response.AccountData;
import com.kevin.gateway.okexapi.future.websocket.response.OrderAlgoData;
import com.kevin.gateway.okexapi.future.websocket.response.OrderData;
import com.kevin.gateway.okexapi.future.websocket.response.PositionData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FuturePrivateDataListenerContainer {

    private final List<FuturePositionListener> futurePositionListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<FutureAccountListener> futureAccountListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<FutureOrderListener> futureOrderListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<FutureOrderAlgoListener> futureOrderAlgoListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<ErrorListener> errorListeners = Collections.synchronizedList(new ArrayList<>());

    public void addPositionListener(FuturePositionListener futurePositionListener) {
        for(FuturePositionListener listenerIter: futurePositionListeners){
            if(listenerIter.equals(futurePositionListener)){
                return;
            }
        }
        futurePositionListeners.add(futurePositionListener);
    }

    public void addAccountListener(FutureAccountListener futureAccountListener) {
        for(FutureAccountListener listenerIter: futureAccountListeners){
            if(listenerIter.equals(futureAccountListener)){
                return;
            }
        }
        futureAccountListeners.add(futureAccountListener);
    }

    public void addOrderListener(FutureOrderListener futureOrderListener) {
        for(FutureOrderListener listenerIter: futureOrderListeners){
            if(listenerIter.equals(futureOrderListener)){
                return;
            }
        }
        futureOrderListeners.add(futureOrderListener);
    }

    public void addOrderAlgoListener(FutureOrderAlgoListener futureOrderAlgoListener) {
        for(FutureOrderAlgoListener listenerIter: futureOrderAlgoListeners){
            if(listenerIter.equals(futureOrderAlgoListener)){
                return;
            }
        }
        futureOrderAlgoListeners.add(futureOrderAlgoListener);
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
        for (FuturePositionListener futurePositionListener : futurePositionListeners) {
            futurePositionListener.handleMarginAccountData(positionData);
        }
    }

    public void fireAccountListener(AccountData accountData) {
        for (FutureAccountListener futureAccountListener : futureAccountListeners) {
            futureAccountListener.handleAccountData(accountData);
        }
    }

    public void fireOrderListener(OrderData orderData) {
        for (FutureOrderListener futureOrderListener : futureOrderListeners) {
            futureOrderListener.handleOrderData(orderData);
        }
    }

    public void fireOrderAlgoListener(OrderAlgoData orderAlgoData) {
        for (FutureOrderAlgoListener futureOrderAlgoListener : futureOrderAlgoListeners) {
            futureOrderAlgoListener.handleOrderAlgoData(orderAlgoData);
        }
    }

    public void fireErrorListener(FailureResponse failureResponse) {
        for (ErrorListener errorListener : errorListeners) {
            errorListener.handleErrorMessage(failureResponse);
        }
    }

}
