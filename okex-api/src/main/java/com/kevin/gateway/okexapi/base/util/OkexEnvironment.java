package com.kevin.gateway.okexapi.base.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kevin.gateway.core.json.BaseModule;
import com.kevin.gateway.okexapi.base.rest.OkexCredentialsRequestInterceptor;
import com.kevin.gateway.okexapi.base.rest.OkexResponseErrorHandler;
import com.kevin.gateway.okexapi.base.rest.RequestResponseLoggingInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public enum OkexEnvironment {

    PRODUCT("https://www.okex.com", "wss://real.okex.com:8443/ws/v3"),
    AWS("https://aws.okex.com", "wss://awspush.okex.com:8443/ws/v3"),
    EMULATED("https://www.okex.com", "wss://real.okex.com:8443/ws/v3?brokerId=9999", true);

    // ...

    private final String restEndpointUrl;
    private final String webSocketEndpointUrl;
    private final RestTemplate restTemplate;
    private final boolean emulating;
    private final ObjectMapper objectMapper;
    private final boolean isDebugMode;
    private final long reconnectCheckerRate;
    private final long pingFixedRate;
    private final TimeUnit timeUnit;
    private final ScheduledExecutorService scheduledExecutorService;

    OkexEnvironment(String restEndpointUrl, String webSocketEndpointUrl) {
        this(restEndpointUrl, webSocketEndpointUrl, false);
    }

    OkexEnvironment(String restEndpointUrl, String webSocketEndpointUrl, boolean emulating) {
        this.restEndpointUrl = restEndpointUrl;
        this.webSocketEndpointUrl = webSocketEndpointUrl;
        this.emulating = emulating;
        this.isDebugMode = System.getenv("OKEX_DEBUG") != null; // TODO: 使用常量?

        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.registerModule(new BaseModule());
        this.objectMapper.registerModule(new OkexApiModule());
        this.objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        reconnectCheckerRate = 3; ///
        pingFixedRate = 20;
        timeUnit = TimeUnit.SECONDS;
        scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        this.restTemplate = isDebugMode
                ?
                new RestTemplateBuilder()
                        .rootUri(restEndpointUrl)
                        .messageConverters(new MappingJackson2HttpMessageConverter(this.objectMapper))
                        .additionalInterceptors(new OkexCredentialsRequestInterceptor(emulating))
                        .additionalInterceptors(new RequestResponseLoggingInterceptor())
                        .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                        .errorHandler(new OkexResponseErrorHandler(objectMapper))
                        .build()
                :
                new RestTemplateBuilder()
                        .rootUri(restEndpointUrl)
                        .messageConverters(new MappingJackson2HttpMessageConverter(this.objectMapper))
                        .additionalInterceptors(new OkexCredentialsRequestInterceptor(emulating))
                        .errorHandler(new OkexResponseErrorHandler(objectMapper))
                        .build();
    }

    public RestTemplate getRestTemplate() {
        return this.restTemplate;
    }

    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }

    public boolean isEmulating() {
        return this.emulating;
    }

    public String restEndpointUrl() {
        return this.restEndpointUrl;
    }

    public String getWebSocketEndpointUrl() {
        return webSocketEndpointUrl;
    }

    public boolean isDebugMode() {
        return this.isDebugMode;
    }

    public long getRreconnectCheckerRate() {
        return this.reconnectCheckerRate;
    }

    public long getPingFixedRate() {
        return this.pingFixedRate;
    }

    public TimeUnit getTimeUnit() {
        return this.timeUnit;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return this.scheduledExecutorService;
    }
}
