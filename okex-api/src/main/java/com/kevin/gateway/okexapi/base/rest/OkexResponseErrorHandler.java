package com.kevin.gateway.okexapi.base.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class OkexResponseErrorHandler implements ResponseErrorHandler {
    private final ObjectMapper objectMapper;

    public OkexResponseErrorHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        OkexError okexError = objectMapper.readValue(response.getBody(), OkexError.class);
        throw new OkexApiError(okexError.getErrorMessage(), okexError.getErrorCode(), response.getStatusCode());
    }
}
