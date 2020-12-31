package com.kevin.gateway.okexapi.future.websocket.response;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 账户信息
 */
@Data
public class AccountMapData {

    /**
     * 账户多个币种的信息
     */
    @JsonIgnore
    private Map<String, AccountData> coinAccounts = new HashMap<>();

    @JsonAnySetter
    public void addCoinAccounts(String key, AccountData value) {
        coinAccounts.put(key, value);
    }



}
