package com.kevin.gateway.okexapi.option.websocket.event;


import com.kevin.gateway.okexapi.option.websocket.response.AccountData;

public interface OptionAccountListener {
    void handleAccountData(AccountData accountData);
}
