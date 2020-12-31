package com.kevin.gateway.okexapi.option.websocket.event;


import com.kevin.gateway.okexapi.base.websocket.ErrorListener;
import com.kevin.gateway.okexapi.base.websocket.response.FailureResponse;
import com.kevin.gateway.okexapi.option.websocket.response.AccountData;
import com.kevin.gateway.okexapi.option.websocket.response.OrderData;
import com.kevin.gateway.okexapi.option.websocket.response.PositionData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class OptionPrivateDataListenerContainer {

    private final List<OptionPositionListener> optionPositionListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<OptionAccountListener> optionAccountListeners = Collections.synchronizedList(new ArrayList<>());
    private final List<OptionOrderListener> optionOrderListeners =Collections.synchronizedList( new ArrayList<>());
    private final List<ErrorListener> errorListeners = Collections.synchronizedList(new ArrayList<>());

    public void addPositionListener(OptionPositionListener optionPositionListener) {
        for(OptionPositionListener listenerIter: optionPositionListeners){
            if(listenerIter.equals(optionPositionListener)){
                return;
            }
        }

        optionPositionListeners.add(optionPositionListener);
    }

    public void addAccountListener(OptionAccountListener optionAccountListener) {
        for(OptionAccountListener listenerIter: optionAccountListeners){
            if(listenerIter.equals(optionAccountListener)){
                return;
            }
        }

        optionAccountListeners.add(optionAccountListener);
    }

    public void addOrderListener(OptionOrderListener optionOrderListener) {
        for(OptionOrderListener listenerIter: optionOrderListeners){
            if(listenerIter.equals(optionOrderListener)){
                return;
            }
        }

        optionOrderListeners.add(optionOrderListener);
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
        for (OptionPositionListener optionPositionListener : optionPositionListeners) {
            optionPositionListener.handlePositionData(positionData);
        }
    }

    public void fireAccountListener(AccountData accountData) {
        for (OptionAccountListener optionAccountListener : optionAccountListeners) {
            optionAccountListener.handleAccountData(accountData);
        }
    }

    public void fireOrderListener(OrderData orderData) {
        for (OptionOrderListener optionOrderListener : optionOrderListeners) {
            optionOrderListener.handleOrderData(orderData);
        }
    }

    public void fireErrorListener(FailureResponse failureResponse) {
        for (ErrorListener errorListener : errorListeners) {
            errorListener.handleErrorMessage(failureResponse);
        }
    }

}
