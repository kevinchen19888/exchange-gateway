package com.kevin.gateway.okexapi.base.websocket;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.websocket.request.LoginOpBuilder;
import com.kevin.gateway.okexapi.base.websocket.request.Operation;
import com.kevin.gateway.okexapi.base.websocket.request.SubscribeOpBuilder;
import com.kevin.gateway.okexapi.base.websocket.request.SubscriptionTopic;
import com.kevin.gateway.okexapi.base.websocket.response.EventResponse;
import com.kevin.gateway.okexapi.base.websocket.response.EventSuccessResponse;
import com.kevin.gateway.okexapi.base.websocket.response.FailureResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class WebSocketTemplate implements WebSocketInterface, WebSocketHandler {

    private WebSocketSession webSocketSession = null;
    private final OkexEnvironment webSocketEnvironment;
    private final long reconnectCheckerRate;
    private final long pingFixedRate;
    private final TimeUnit timeUnit;
    private final WebSocketStreamFilter webSocketStreamFilter;
    private final Credentials credentials;
    private final Object lifecycleMonitor = new Object();
    private final SubscribeOpBuilder subscribeOpBuilder;

    /// 由于是同步建立连接,并且用synchronize控制并发,下面代码中没有出现"连接中"的状态
    private int connStatus = 0; ///< 连接状态 0-未连接 1-连接中 2-已连接
    /// 重连任务
    private final ScheduledFuture<?> autoReconnectTask;

    private ScheduledFuture<?> pingFuture = null;

    protected final ObjectMapper objectMapper;
    private final ScheduledExecutorService scheduledExecutorService;

    public WebSocketTemplate(OkexEnvironment webSocketEnvironment,
                             WebSocketStreamFilter webSocketStreamFilter,
                             SubscribeOpBuilder subscribeOpBuilder,
                             @Nullable Credentials credentials) {
        this.checkValidatedTopicsWithCredentials(subscribeOpBuilder.getSubscriptionTopics(), credentials);

        this.webSocketEnvironment = webSocketEnvironment;
        this.scheduledExecutorService = webSocketEnvironment.getScheduledExecutorService();
        this.reconnectCheckerRate = webSocketEnvironment.getRreconnectCheckerRate();
        this.pingFixedRate = webSocketEnvironment.getPingFixedRate();
        this.timeUnit = webSocketEnvironment.getTimeUnit();
        this.webSocketStreamFilter = webSocketStreamFilter;
        this.credentials = credentials;
        this.subscribeOpBuilder = subscribeOpBuilder;
        // 设置 ObjectMapper, 后续由环境获取objectMapper对象
        objectMapper = webSocketEnvironment.getObjectMapper();
        autoReconnectTask = scheduledExecutorService.scheduleWithFixedDelay(this::connect, reconnectCheckerRate, reconnectCheckerRate, timeUnit);
    }

    private void checkValidatedTopicsWithCredentials(List<SubscriptionTopic> topicList, @Nullable Credentials credentials) {
        if (credentials != null) {
            if (topicList.stream().anyMatch(topic -> !topic.getChannelTrait().isLoginRequired())) {
                throw new IllegalArgumentException("参数错误：在存在凭证的情况下，传入了公共频道");
            }
        } else {
            if (topicList.stream().anyMatch(topic -> topic.getChannelTrait().isLoginRequired())) {
                throw new IllegalArgumentException("参数错误：在没有凭证的情况下，传入了私有频道");
            }
        }
    }

    public Optional<Credentials> getCredentials() {
        return Optional.ofNullable(this.credentials);
    }

    @Override
    public void connect() {
        synchronized (this.lifecycleMonitor) {
            if (!isConnected()) {
                try {
                    openConnection();
                } catch (ExecutionException | InterruptedException | java.net.URISyntaxException e) {
                    throw new IllegalStateException("WebSocket 连接失败", e);
                }
            }
        }
    }

    /**
     * 主动断开连接
     * <p>
     * 主动断开连接不自动重连
     *
     * @throws IOException 断开时可能抛出异常
     */
    @Override
    public void disconnect() throws IOException {
        // 关闭连接
        synchronized (this.lifecycleMonitor) {
            autoReconnectTask.cancel(true);
            if (isConnected()) {
                this.webSocketSession.close(); /// NOTE 一定会触发afterConnectionClosed
//                this.connecting = false;
                /// 停止keep-alive线程
//                pingFuture.cancel(true); /// NOTE pingFuture可能是null (在afterConnectionClosed中处理,不用在此调用)
            }
        }
    }

    /**
     * 判断是否已经连接
     *
     * @return true 已经连接
     */
    public boolean isConnected() {
        return connStatus == 2;
    }

    @Override
    public Optional<WebSocketSession> getWebSocketSession() {
        return Optional.ofNullable(this.webSocketSession);
    }

    protected void openConnection() throws ExecutionException, InterruptedException, java.net.URISyntaxException {
        StandardWebSocketClient standardWebSocketClient = new StandardWebSocketClient();
        this.webSocketSession = standardWebSocketClient.doHandshake(
                this,
                new WebSocketHttpHeaders(),
                new URI(this.webSocketEnvironment.getWebSocketEndpointUrl())
        ).get();    // 为了避免状态不一致的问题，此处使用同步访问
    }

    protected void sendOperation(WebSocketSession session, Operation op) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        String s = mapper.writeValueAsString(op);
//        if (s.length() > OkexWebsocketConstant.SUBSCRIPTION_JSON_LEN_MAX) {
//            throw new IOException("订阅消息超越长度4096限制");
//        }
        log.debug("sendOperation:{}", s);
        session.sendMessage(new TextMessage(s));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        connStatus = 2;
        // 【可选的】发送认证
        if (this.getCredentials().isPresent()) {
            Credentials credentials = this.getCredentials().get();
            Operation op = new LoginOpBuilder(credentials).build();
            sendOperation(session, op);
        } else

        // 发送订阅(无需登录时，需登录时要等到登录成功才能发送订阅)
        {
            Operation op = this.subscribeOpBuilder.build();
            sendOperation(session, op);
        }
        // 添加定时 ping （保持连接）任务
        this.pingFuture = scheduledExecutorService.scheduleWithFixedDelay(() -> {
            WebSocketSession webSocketSession = WebSocketTemplate.this.webSocketSession;
            if (isConnected()) {
                try {
                    /// TODO WebSocketSession#sendMessage线程安全吗? 2个线程同时发送消息会怎样
                    webSocketSession.sendMessage(new TextMessage("ping"));
                } catch (IOException e) {
                    log.error("发送 ping 时出错", e);
                }
            } else {
                log.warn("发送 ping 时连接不存在或者已经关闭");
            }

        }, pingFixedRate, pingFixedRate, timeUnit);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof BinaryMessage) {
            TextMessage textMessage = webSocketStreamFilter.doFilter((BinaryMessage) message);
            handleTextMessage(session, textMessage);
            return;
        }
        if (message instanceof TextMessage) {
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("不支持文本格式的消息"));
            // handleTextMessage(session, (TextMessage) message);
            return;
        } else if (message instanceof PongMessage) {
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("不支持 Pong 格式的消息"));
            // handlePongMessage(session, (PongMessage) message);
            return;
        } else {
            throw new IllegalStateException("不支持的 WebSocket 消息类型: " + message);
        }
    }

    protected abstract void handleDataResponse(JsonNode root) throws JsonProcessingException;

    private void handlePongMessage(WebSocketSession session, PongMessage message) {
        log.info("收到 Pong 消息: {}", message);
    }

    public void handleEventResponse(WebSocketSession session, EventResponse eventResponse) {
        // TODO: 处理事件响应
//        log.info("handleEventResponse :{}", eventResponse);
    }

    public void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws JsonProcessingException, Exception {
        // 先解析到 JsonNode 格式
        log.debug("handleTextMessage:{}", textMessage.getPayload());
        if (textMessage.getPayload().equals("pong")) {
            log.debug(textMessage.getPayload());
            return;
        }
        JsonNode root = objectMapper.readTree(textMessage.getPayload());

        // 获取根节点的字段名称列表
        List<String> fieldNames = new ArrayList<>();
        root.fieldNames().forEachRemaining(fieldNames::add);

        // 处理登录事件的响应的情况
        if (fieldNames.contains("event") && fieldNames.contains("success")) {
            EventSuccessResponse eventSuccess = objectMapper.treeToValue(root, EventSuccessResponse.class);
            handleEventSuccessResponse(session, eventSuccess);
        }

        // 处理订阅/取消事件响应的情况
        if (fieldNames.contains("event") && fieldNames.contains("channel")) {
            EventResponse eventResponse = objectMapper.treeToValue(root, EventResponse.class);
            handleEventResponse(session, eventResponse);
        }

        // 处理失败事件响应的情况
        if (fieldNames.contains("event") && fieldNames.contains("error")) {
            FailureResponse eventFailure = objectMapper.treeToValue(root, FailureResponse.class);
            log.warn("handleTextMessage received error:{}", eventFailure);
            handleEventFailureResponse(session, eventFailure);
        }

        // TODO: 处理其它数据类型的情况
        if (fieldNames.contains("table")) {
            handleDataResponse(root);
        }
    }

    private void handleEventSuccessResponse(WebSocketSession session, EventSuccessResponse eventSuccess) throws Exception {
        // TODO: ...
        if (eventSuccess.getEvent().equals("login") && eventSuccess.isSuccess()) {
            log.info("handleEventSuccessResponse received a login success message");

            // 登录成功，可以发送订阅信息了
            Operation op = this.subscribeOpBuilder.build();
            sendOperation(session, op);
        }
    }

    protected void handleEventFailureResponse(WebSocketSession session, FailureResponse eventFailure) {
        // TODO: ...
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        /// 可能出现java.io.IOException: java.util.concurrent.TimeoutException
        log.info("handleTransportError:{}", exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // 取消 Ping （保持连接）任务
        if (!this.pingFuture.cancel(true))
            log.error("取消ping失败");
        connStatus = 0;
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
