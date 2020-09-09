package com.alchemy.gateway.core.info;

/**
 * 交易所连接信息
 */
public interface ConnectionInfo {
    /**
     * rest 接入网站
     *
     * @return Restful 入口网址
     */
    String getRestfulApiEndpoint();

    /**
     * WebSocket 接入网址
     *
     * @return WebSocket 接入网址
     */
    String getWebSocketEndpoint();

    /**
     * 读超时时间，单位：毫秒，缺省值 30000 毫秒（30秒）
     *
     * @return 超时时间
     */

    default long getReadTimeout() {
        return 1000 * 30;
    }

    /**
     * 写超时时间，单位：毫秒，缺省值 30000 毫秒（30秒）
     *
     * @return 超时时间
     */
    default long getWriteTimeout() {
        return 1000 * 30;
    }

    /**
     * 连接超时时间，单位：毫秒，缺省值 30000 毫秒（30秒）
     *
     * @return 超时时间
     */
    default long getConnectTimeout() {
        return 1000 * 30;
    }

    /**
     * 断开连接后自动重连, 缺省 false
     *
     * @return true 代表自动重连
     */
    default boolean getRetryOnConnectionFailure() {
        return false;
    }
}
