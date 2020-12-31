package com.kevin.gateway.okexapi.margin.response;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class MarginPlaceOrderMapResponse {

    @JsonIgnore
    private Map<String, List<MarginPlaceOrderResponse>> placeOrder = new HashMap<>();

    @JsonAnySetter
    public void addOrderInfo(String key, List<MarginPlaceOrderResponse> value) {
        this.placeOrder.put(key, value);
    }
}
