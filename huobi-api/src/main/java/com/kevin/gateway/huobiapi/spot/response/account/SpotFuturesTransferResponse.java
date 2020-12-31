package com.kevin.gateway.huobiapi.spot.response.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 币币现货账户与合约账户划转
 */
@Data
public class SpotFuturesTransferResponse {
    private Long data;    //	Transfer id
    private String status;    //	"ok" or "error"
    @JsonProperty(value = "err-code")
    private String errCode;    //	错误码，具体错误码请见列表
    @JsonProperty(value = "err-msg")
    private String errMsg;    //	错误消息，具体消息内容请列表
    @JsonProperty(value = "print-log")
    private String printLog;
}
