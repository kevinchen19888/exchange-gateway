package com.alchemy.gateway.core;

import com.alchemy.gateway.core.websocket.WebSocketClientAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeMap;

@Slf4j
public class ExchangeManagerImpl implements ExchangeManager {
    private final Map<String, ExchangeApi> exchangeApiMap = new TreeMap<>();

    public ExchangeManagerImpl(RestTemplate restTemplate, WebSocketClient webSocketClient) {
        ServiceLoader<ExchangeApi> serviceLoader = ServiceLoader.load(ExchangeApi.class);
        for (ExchangeApi exchangeApi : serviceLoader) {
            exchangeApiMap.put(exchangeApi.getName(), exchangeApi);

            // 处理可注入的 RestTemplateAware
            if (exchangeApi instanceof RestTemplateAware) {
                RestTemplateAware restTemplateAware = (RestTemplateAware) exchangeApi;
                restTemplateAware.setRestTemplate(restTemplate);
            }

            // 处理可注入的 WebSocketClientAware
            if (exchangeApi instanceof WebSocketClientAware) {
                WebSocketClientAware webSocketClientAware = (WebSocketClientAware) exchangeApi;
                webSocketClientAware.setWebSocketClient(webSocketClient);
            }

            log.info("装载交易所API: {} => {}", exchangeApi.getName(), exchangeApi.getClass());
        }
    }

    @Override
    public Set<String> getNames() {
        return exchangeApiMap.keySet();
    }

    @Override
    public ExchangeApi getAPI(String name) {
        return exchangeApiMap.get(name);
    }

    @Override
    public Map<String, ExchangeApi> getAll() {
        return exchangeApiMap;
    }
}
