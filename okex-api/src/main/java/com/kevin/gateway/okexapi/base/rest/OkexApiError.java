package com.kevin.gateway.okexapi.base.rest;

import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
public class OkexApiError extends RuntimeException {
    private final String errorMessage;
    private final long errorCode;
    private final HttpStatus httpStatus;


    public OkexApiError(String errorMessage, long errorCode, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public long getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
