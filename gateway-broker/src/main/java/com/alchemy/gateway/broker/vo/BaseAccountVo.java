package com.alchemy.gateway.broker.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * describe:
 *
 * @author zoulingwei
 */
@Data
public class BaseAccountVo {
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

}
