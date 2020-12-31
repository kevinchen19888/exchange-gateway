package com.kevin.gateway.okexapi.base.websocket.response;

import lombok.Data;

/**
 * 出错时的响应体
 * <p>
 * 格式：{"event":"error","message":"<error_message>","errorCode":"<errorCode>"}
 */
@Data
public class FailureResponse {
    String error;
    String message;
    long errorCode;
}
