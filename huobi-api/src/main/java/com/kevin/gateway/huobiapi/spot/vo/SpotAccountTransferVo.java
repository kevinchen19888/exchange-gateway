package com.kevin.gateway.huobiapi.spot.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 资金划转
 */
@Data
public class SpotAccountTransferVo {
    @JsonProperty(value = "transact-id")
    private int transactId;//	交易流水号
    @JsonProperty(value = "transact-time")
    private long transactTime; //交易时间
}
