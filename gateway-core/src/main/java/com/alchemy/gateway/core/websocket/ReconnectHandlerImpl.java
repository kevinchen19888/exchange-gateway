package com.alchemy.gateway.core.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.client.ConnectionManagerSupport;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class ReconnectHandlerImpl implements ReconnectHandler {

    private final ConnectionManagerSupport connectionManagerSupport;
    private final int retryCounter; // TODO: 使用重试次数
    private static final int DEFAULT_RETRY_COUNTER = 10;

    private final List<CloseStatus> retryableCloseStatuses = Arrays.asList(
            CloseStatus.SERVER_ERROR,
            CloseStatus.NO_CLOSE_FRAME,
            CloseStatus.SERVICE_RESTARTED,
            CloseStatus.SERVICE_OVERLOAD
    );


    public ReconnectHandlerImpl(ConnectionManagerSupport connectionManagerSupport) {
        this(connectionManagerSupport, DEFAULT_RETRY_COUNTER);
    }

    public ReconnectHandlerImpl(ConnectionManagerSupport connectionManagerSupport, int retryCounter) {
        this.connectionManagerSupport = connectionManagerSupport;
        this.retryCounter = retryCounter;
    }


    @Override
    public void reconnect(CloseStatus closeStatus) {
        log.info("重连前 status={}",closeStatus);
        for (CloseStatus cs : retryableCloseStatuses) {
            if (cs.equalsCode(closeStatus)) {
                log.info("开始重连...");
                connectionManagerSupport.stop();
                connectionManagerSupport.start();
                return;
            }
        }
    }
}
