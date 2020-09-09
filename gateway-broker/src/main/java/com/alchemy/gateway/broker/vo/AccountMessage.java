package com.alchemy.gateway.broker.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


/**
 * describe:
 *
 * @author zoulingwei
 */
@Data
public class AccountMessage {
    /**
     * 交易所名称
     */
    @JsonProperty("exchange_name")
    private String exchangeName;
    /**
     * 账户id
     */
    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("access_key")
    private String accessKey;
    @JsonProperty("secret_key")
    private String secretKey;

}
