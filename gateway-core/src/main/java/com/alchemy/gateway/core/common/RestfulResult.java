package com.alchemy.gateway.core.common;

public class RestfulResult<T> {
    int httpStatusCode;
    String reason;
    T result;

    public RestfulResult(int httpStatusCode, String reason, T result) {
        this.httpStatusCode = httpStatusCode;
        this.reason = reason;
        this.result = result;
    }

    public RestfulResult(int httpStatusCode, String reason) {
        this.httpStatusCode = httpStatusCode;
        this.reason = reason;
        this.reason = null;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getReason() {
        return reason;
    }

    public T getResult() {
        return result;
    }
}
