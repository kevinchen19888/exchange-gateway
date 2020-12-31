package com.kevin.gateway.okexapi.future.model;


import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class SetLeverageBodyResponseFixed extends SetLeverageBodyResponseBase {

    @JsonIgnore
    private Map<String, LongShortItem> leverageItemMap = new HashMap<>();

    @JsonAnySetter
    public void addLeverageItemMap(String key, LongShortItem longShortItem) {
        leverageItemMap.put(key, longShortItem);
    }

    @Data
    static class LongShortItem {

        @JsonProperty("long")
        private int longItem;

        @JsonProperty("short")
        private int shortItem;

    }
}
