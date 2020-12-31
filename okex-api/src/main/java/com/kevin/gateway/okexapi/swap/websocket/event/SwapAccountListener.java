package com.kevin.gateway.okexapi.swap.websocket.event;


import com.kevin.gateway.okexapi.swap.websocket.response.AccountData;

public interface SwapAccountListener {
    void handleAccountData(AccountData accountData);
}
