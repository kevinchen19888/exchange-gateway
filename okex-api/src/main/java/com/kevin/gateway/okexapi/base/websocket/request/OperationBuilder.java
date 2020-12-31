package com.kevin.gateway.okexapi.base.websocket.request;

/**
 * 指令构建器
 */
public interface OperationBuilder {
    /**
     * 构建指令
     *
     * @return 指令
     */
    Operation build();
}
