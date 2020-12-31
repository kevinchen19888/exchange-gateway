package com.kevin.gateway.huobiapi.base.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class HuobiResponseErrorHandler implements ResponseErrorHandler {
    private final ObjectMapper objectMapper;

    public HuobiResponseErrorHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        //test(response.getBody());
        HuobiError okexError = objectMapper.readValue(response.getBody(), HuobiError.class);

        throw new HuobiApiError(okexError.getStatus(), okexError.getErrCode(), okexError.getErrMsg());
    }

    public void test(InputStream inputStream) {
        String result = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));
        System.out.println(result);
    }
}
