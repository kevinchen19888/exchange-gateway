package com.alchemy.gateway.core;

import com.alchemy.gateway.core.common.OrderLimitManager;
import com.alchemy.gateway.core.common.RateLimiterManager;
import com.alchemy.gateway.core.common.RateLimiterManagerImpl;
import com.alchemy.gateway.core.websocket.ReconnectHandlerImpl;
import com.alchemy.gateway.core.websocket.ReconnectWebSocketHandler;
import com.alchemy.gateway.core.websocket.WebSocketClientAware;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.ConnectionManagerSupport;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;

import java.util.Optional;

public abstract class AbstractExchangeApi implements ExchangeApi, RestTemplateAware, WebSocketClientAware {
    @Nullable
    protected RestTemplate restTemplate;

    @Nullable
    protected WebSocketClient webSocketClient;

    private RateLimiterManager rateLimiterManager = new RateLimiterManagerImpl();

    private OrderLimitManager orderLimitManager = new OrderLimitManager();

    @Override
    public void setRestTemplate(RestTemplate restTemplate) {
        // 注入 RestTemplate
        this.restTemplate = restTemplate;
    }

    @Override
    public void setWebSocketClient(WebSocketClient webSocketClient) {
        // 注入 WebSocketClient
        this.webSocketClient = webSocketClient;
    }

    @Override
    public RateLimiterManager getRateLimiterManager() {
        return this.rateLimiterManager;
    }

    @Override
    public OrderLimitManager getOrderLimitManager() {
        return orderLimitManager;
    }

    /**
     * 连接 WebSocket 服务器
     *
     * @param webSocketHandler 处理 WebSocket 事件的处理器接口
     * @return 连接管理器
     */
    public ConnectionManagerSupport connectWebSocket(WebSocketHandler webSocketHandler) {
        ReconnectWebSocketHandler reconnectWebSocketHandler = new ReconnectWebSocketHandler(webSocketHandler);
        WebSocketConnectionManager webSocketConnectionManager =
                new WebSocketConnectionManager(webSocketClient,
                        reconnectWebSocketHandler,
                        this.getConnectionInfo().getWebSocketEndpoint());
        reconnectWebSocketHandler.setReconnectHandler(new ReconnectHandlerImpl(webSocketConnectionManager));

        webSocketConnectionManager.start();
        return webSocketConnectionManager;
    }

    public ConnectionManagerSupport connectWebSocket(WebSocketHandler webSocketHandler, final String url) {
        ReconnectWebSocketHandler reconnectWebSocketHandler = new ReconnectWebSocketHandler(webSocketHandler);
        WebSocketConnectionManager webSocketConnectionManager = new WebSocketConnectionManager(webSocketClient,
                reconnectWebSocketHandler, url);
        reconnectWebSocketHandler.setReconnectHandler(new ReconnectHandlerImpl(webSocketConnectionManager));

        webSocketConnectionManager.start();
        return webSocketConnectionManager;
    }

    public RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            throw new IllegalStateException("请先调用 setRestTemplate");
        }
        return restTemplate;
    }

    public WebSocketClient getWebSocketClient() {
        if (webSocketClient == null) {
            throw new IllegalStateException("请先调用 setWebSocketClient");
        }
        return webSocketClient;
    }

}
