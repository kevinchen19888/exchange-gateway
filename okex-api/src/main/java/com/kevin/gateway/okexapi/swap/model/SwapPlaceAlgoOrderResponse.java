package com.kevin.gateway.okexapi.swap.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SwapPlaceAlgoOrderResponse {

    /**
     * 错误码，请求成功时为0，请求失败时会显示相应错误码
     */
    private String errorCode;

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误信息，请求成功时为空，请求失败时会显示错误信息
     */
    private String errorMessage;


    /**
     * 详细信息
     */
    @JsonProperty("detailMsg")
    private String detailMsg;


    /**
     * 信息
     */
    private String msg;

    private SwapPlaceAlgoOrderResponseData data;

}



