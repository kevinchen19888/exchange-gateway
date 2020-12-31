package com.kevin.gateway.okexapi.base.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OkexError {
    public long errorCode;
    public String errorMessage;
}
