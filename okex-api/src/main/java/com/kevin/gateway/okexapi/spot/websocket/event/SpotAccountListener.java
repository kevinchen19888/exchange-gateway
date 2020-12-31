package com.kevin.gateway.okexapi.spot.websocket.event;


import com.kevin.gateway.okexapi.spot.websocket.response.AccountData;


public interface SpotAccountListener {
    void handleAccountData(AccountData accountData);
}
