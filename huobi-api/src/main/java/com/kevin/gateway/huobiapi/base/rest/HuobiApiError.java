package com.kevin.gateway.huobiapi.base.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@ToString
public class HuobiApiError extends RuntimeException {
    private final String status;
    @JsonProperty(value = "err-code")
    private final String errCode;
    @JsonProperty(value = "err-msg")
    private final String errMsg;


    public HuobiApiError(String status, String errCode, String errMsg) {
        this.status = status;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getStatus() {
        return status;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
