package com.kevin.gateway.huobiapi.spot.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SpotOrderResponse {

    @JsonProperty("status")
    private String status;
    @JsonProperty("err-code")
    private String errcode;
    @JsonProperty("err-msg")
    private String errmsg;
    @JsonProperty("data")
    private String data;
}
