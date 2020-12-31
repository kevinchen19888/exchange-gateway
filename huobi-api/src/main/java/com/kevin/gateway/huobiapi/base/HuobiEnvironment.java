package com.kevin.gateway.huobiapi.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kevin.gateway.core.json.BaseModule;
import com.kevin.gateway.huobiapi.base.rest.HuobiCredentialsRequestInterceptor;
import com.kevin.gateway.huobiapi.base.rest.HuobiResponseErrorHandler;
import com.kevin.gateway.huobiapi.base.rest.RequestResponseLoggingInterceptor;
import com.kevin.gateway.huobiapi.base.util.HuobiApiModule;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public enum HuobiEnvironment {

    PRODUCT("https://api.huobi.pro", "wss://api.huobi.pro/ws"),
    AWS("https://api-aws.huobi.pro", "wss://api-aws.huobi.pro/ws");

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

    HuobiEnvironment(String restEndpointUrl, String webSocketEndpointUrl) {
        this(restEndpointUrl, webSocketEndpointUrl, false);
    }

    HuobiEnvironment(String restEndpointUrl, String webSocketEndpointUrl, boolean emulating) {
        this.restEndpointUrl = restEndpointUrl;
        this.webSocketEndpointUrl = webSocketEndpointUrl;
        this.emulating = emulating;
        this.isDebugMode = true;
//        this.isDebugMode = System.getenv("HUOBI_DEBUG") != null; // TODO: 使用常量?

        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.registerModule(new BaseModule());
        this.objectMapper.registerModule(new HuobiApiModule());
        //this.objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        reconnectCheckerRate = 3; ///
        pingFixedRate = 20;
        timeUnit = TimeUnit.SECONDS;
        scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        this.restTemplate = isDebugMode
                ?
                new RestTemplateBuilder()
                        .rootUri(restEndpointUrl)
                        .messageConverters(new MappingJackson2HttpMessageConverter(this.objectMapper))
                        .additionalInterceptors(new HuobiCredentialsRequestInterceptor(emulating))
                        .additionalInterceptors(new RequestResponseLoggingInterceptor())
                        .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                        .errorHandler(new HuobiResponseErrorHandler(objectMapper))
                        .build()
                :
                new RestTemplateBuilder()
                        .rootUri(restEndpointUrl)
                        .messageConverters(new MappingJackson2HttpMessageConverter(this.objectMapper))
                        .additionalInterceptors(new HuobiCredentialsRequestInterceptor(emulating))
                        .errorHandler(new HuobiResponseErrorHandler(objectMapper))
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
