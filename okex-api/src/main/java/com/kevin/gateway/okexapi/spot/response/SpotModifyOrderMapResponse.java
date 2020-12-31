package com.kevin.gateway.okexapi.spot.response;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class SpotModifyOrderMapResponse {
    @JsonIgnore
    private Map<String, List<SpotModifyOrderResponse>> orderInfo = new HashMap<>();

    @JsonAnySetter
    public void addOrderInfo(String key, List<SpotModifyOrderResponse> value) {
        this.orderInfo.put(key, value);
    }
}
