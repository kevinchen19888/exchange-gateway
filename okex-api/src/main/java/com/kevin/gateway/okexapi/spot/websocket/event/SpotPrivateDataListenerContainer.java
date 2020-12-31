package com.kevin.gateway.okexapi.spot.websocket.event;

import com.kevin.gateway.okexapi.base.websocket.ErrorListener;
import com.kevin.gateway.okexapi.base.websocket.response.FailureResponse;
import com.kevin.gateway.okexapi.spot.websocket.response.AccountData;
import com.kevin.gateway.okexapi.spot.websocket.response.MarginAccountData;
import com.kevin.gateway.okexapi.spot.websocket.response.OrderAlgoData;
import com.kevin.gateway.okexapi.spot.websocket.response.OrderData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 资产用户现货账户私有信息侦听容器
 */
public class SpotPrivateDataListenerContainer {

    private final List<SpotAccountListener> accountListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<SpotOrderListener> orderListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<SpotMarginAcountListener> marginAcountListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<SpotOrderAlgoListener> orderAlgoListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<ErrorListener> errorListeners = Collections.synchronizedList(new ArrayList<>());

    public void addErrorListeners(ErrorListener errorListener) {

        for(ErrorListener listenerIter: errorListeners){
            if(listenerIter.equals(errorListener)){
                return;
            }
        }
        errorListeners.add(errorListener);
    }

    public void addAccountListener(SpotAccountListener spotAccountListener) {
        for(SpotAccountListener listenerIter: accountListeners){
            if(listenerIter.equals(spotAccountListener)){
                return;
            }
        }

        accountListeners.add(spotAccountListener);
    }
    public void removeOrderListner(SpotOrderListener spotOrderListener) {
        orderListeners.remove(spotOrderListener);
    }

    public void addOrderListener(SpotOrderListener spotOrderListener) {

        for(SpotOrderListener listenerIter: orderListeners){
            if(listenerIter.equals(spotOrderListener)){
                return;
            }
        }
        orderListeners.add(spotOrderListener);
    }

    public void addMarginAccountListener(SpotMarginAcountListener marginAcountListener) {
        for(SpotMarginAcountListener listenerIter: marginAcountListeners){
            if(listenerIter.equals(marginAcountListener)){
                return;
            }
        }

        marginAcountListeners.add(marginAcountListener);
    }

    public void addOrderAlgoListener(SpotOrderAlgoListener orderAlgoListener) {
        for(SpotOrderAlgoListener listenerIter: orderAlgoListeners){
            if(listenerIter.equals(orderAlgoListener)){
                return;
            }
        }
        orderAlgoListeners.add(orderAlgoListener);
    }

    // 数据接收侧处理
    public void fireAccountListener(AccountData accountData) {
        for (SpotAccountListener accountListener : accountListeners) {
            accountListener.handleAccountData(accountData);
        }
    }

    public void fireOrderListener(OrderData orderData) {
        for (SpotOrderListener orderListener : orderListeners) {
            orderListener.handleOrderData(orderData);
        }
    }

    public void fireMarginAccountListener(MarginAccountData marginAccountData) {
        for (SpotMarginAcountListener marginAcountListener : marginAcountListeners) {
            marginAcountListener.handleMarginAccountData(marginAccountData);
        }
    }

    public void fireOrderAlgoListener(OrderAlgoData orderAlgoData) {
        for (SpotOrderAlgoListener orderAlgoListener : orderAlgoListeners) {
            orderAlgoListener.handleOrderAlgoData(orderAlgoData);
        }
    }

    public void fireErrorListener(FailureResponse failureResponse) {
        for (ErrorListener errorListener : errorListeners) {
            errorListener.handleErrorMessage(failureResponse);
        }
    }

}
