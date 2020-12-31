package com.kevin.gateway.huobiapi.spot.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public abstract class SpotBaseResponse {
    private String ch;//主题
    private String ok;
    private String status;//状态
    private String message;
    private String code;
    private String success;
    private long ts;//时间戳
    private long version;
    private long id;
    private long nextId;//下页起始编号（ID，仅在查询结果需要分页返回时，包含此字段）
    @JsonProperty(value = "err-code")
    private String errCode;
    @JsonProperty(value = "err-msg")
    private String errMsg;
}
