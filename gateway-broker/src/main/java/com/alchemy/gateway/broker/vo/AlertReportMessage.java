package com.alchemy.gateway.broker.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author zoulingwei
 */
@Builder
@Data
public class AlertReportMessage {
    @JsonProperty("account_id")
    private String accountId;
    /**
     * 1-警告 2-错误 3-严重错误
     */
    @JsonProperty("level")
    private int level;
    @JsonProperty("error_code")
    private int errorCode;// 告警信息
    @JsonProperty("error_text")
    private String errorText; // 告警信息
    @JsonProperty("reporter")
    private String reporter;//来源实例id

}
