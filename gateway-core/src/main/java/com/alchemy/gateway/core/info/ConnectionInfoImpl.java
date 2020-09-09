package com.alchemy.gateway.core.info;

import lombok.Data;

@Data
public class ConnectionInfoImpl implements ConnectionInfo {

    private final String restfulApiEndpoint;
    private final String webSocketEndpoint;
    private final long readTimeout;
    private final long writeTimeout;
    private final long connectTimeout;

    private static long DEFAULT_READ_TIMEOUT = 1000 * 30;
    private static long DEFAULT_WRITE_TIMEOUT = 1000 * 30;
    private static long DEFAULT_CONNECT_TIMEOUT = 1000 * 30;

    public ConnectionInfoImpl(String restfulApiEndpoint, String webSocketEndpoint) {
        this.restfulApiEndpoint = restfulApiEndpoint;
        this.webSocketEndpoint = webSocketEndpoint;
        this.readTimeout = DEFAULT_READ_TIMEOUT;
        this.writeTimeout = DEFAULT_WRITE_TIMEOUT;
        this.connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    }
}
