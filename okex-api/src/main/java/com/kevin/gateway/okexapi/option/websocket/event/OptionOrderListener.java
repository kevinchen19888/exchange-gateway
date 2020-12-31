package com.kevin.gateway.okexapi.option.websocket.event;

import com.kevin.gateway.okexapi.option.websocket.response.OrderData;


public interface OptionOrderListener {
    void handleOrderData(OrderData orderData);
}
