package com.kevin.gateway.okexapi.spot.websocket.event;


import com.kevin.gateway.okexapi.spot.websocket.response.MarginAccountData;

public interface SpotMarginAcountListener {
    void handleMarginAccountData(MarginAccountData marginAccountData);
}
