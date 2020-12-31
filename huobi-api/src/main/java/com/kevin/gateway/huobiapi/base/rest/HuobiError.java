package com.kevin.gateway.huobiapi.base.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HuobiError {
    private final long ts;
    private final String status;
    @JsonProperty(value = "err-code")
    private final String errCode;
    @JsonProperty(value = "err-msg")
    private final String errMsg;
}
