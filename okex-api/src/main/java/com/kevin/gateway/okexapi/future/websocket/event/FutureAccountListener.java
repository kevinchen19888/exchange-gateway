package com.kevin.gateway.okexapi.future.websocket.event;


import com.kevin.gateway.okexapi.future.websocket.response.AccountData;

public interface FutureAccountListener {
    void handleAccountData(AccountData accountData);
}
