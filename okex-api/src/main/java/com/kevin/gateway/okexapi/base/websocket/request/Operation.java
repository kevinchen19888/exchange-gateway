package com.kevin.gateway.okexapi.base.websocket.request;

import java.util.List;

/**
 * 指令
 * <p>
 * 格式：{"op": "<value>", "args": ["<value1>","<value2>"]}
 */
public interface Operation {

    String getOp();

    List<String> getArgs();
}
