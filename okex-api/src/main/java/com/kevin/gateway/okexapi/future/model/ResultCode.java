package com.kevin.gateway.okexapi.future.model;

import lombok.Data;

@Data
public class ResultCode {

    /**
     * 错误码，请求成功时为0，请求失败时会显示相应错误码
     */
    private String errorCode;

    /**
     * 错误信息，请求成功时为空，请求失败时会显示错误信息
     */
    private String errorMessage;

    /**
     * 接口调用返回结果，true/false
     */
    private Boolean result;
}
