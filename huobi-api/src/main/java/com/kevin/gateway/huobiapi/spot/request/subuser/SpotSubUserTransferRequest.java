package com.kevin.gateway.huobiapi.spot.request.subuser;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.model.SpotTransferType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 资产划转（母子用户之间）
 */
@Data
public class SpotSubUserTransferRequest {
    @JsonProperty(value = "sub-uid")
    private String subUid;//子用户uid
    private SpotCoin currency;    //币种
    private BigDecimal amount;    //划转金额
    private SpotTransferType type;//划转类型
}
