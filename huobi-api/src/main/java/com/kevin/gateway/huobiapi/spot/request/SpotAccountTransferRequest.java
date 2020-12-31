package com.kevin.gateway.huobiapi.spot.request;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.model.SpotAccountType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 资金划转
 */
@Data
public class SpotAccountTransferRequest {
    @JsonProperty(value = "from-user")
    private long fromUser;    //转出用户uid	母用户uid,子用户uid
    @JsonProperty(value = "from-account-type")
    private SpotAccountType fromAccountType;//转出账户类型	spot,margin
    @JsonProperty(value = "from-account")
    private long fromAccount;//转出账户id
    @JsonProperty(value = "to-user")
    private long toUser;//转入用户uid	母用户uid,子用户uid
    @JsonProperty(value = "to-account-type")
    private SpotAccountType toAccountType;//转入账户类型	spot,margin
    @JsonProperty(value = "to-account")
    private long toAccount;//转入账户id
    private SpotCoin currency;//	币种
    private BigDecimal amount;    //	划转金额
}
