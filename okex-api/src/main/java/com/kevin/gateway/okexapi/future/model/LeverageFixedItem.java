package com.kevin.gateway.okexapi.future.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

public class LeverageFixedItem extends LeverageBaseItem {

    private String marginMode = "fixed";

    @JsonIgnore
    private Map<String, OneByOneLeverageItem> leverageItemMap = new HashMap<>();

    @JsonAnySetter
    public void addLeverageItemMap(String key, OneByOneLeverageItem oneByOneLeverageItem) {
        leverageItemMap.put(key, oneByOneLeverageItem);
    }
}
