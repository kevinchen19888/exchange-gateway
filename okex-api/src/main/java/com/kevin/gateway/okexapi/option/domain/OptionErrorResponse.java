package com.kevin.gateway.okexapi.option.domain;

import lombok.Data;

@Data
public class OptionErrorResponse {

    /**
     * 错误信息
     */
    protected String errorMessage;

    /**
     * 错误码
     */
    protected String errorCode;
}
