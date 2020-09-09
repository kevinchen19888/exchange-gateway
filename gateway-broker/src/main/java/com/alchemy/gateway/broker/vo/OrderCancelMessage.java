package com.alchemy.gateway.broker.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * describe:
 *
 * @author zoulingwei
 * @date 2020-07-13 11:20
 */
@Data
public class OrderCancelMessage {
    @JsonProperty("exchange_name")
    private String exchangeName;
    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("order_id")
    private Long orderId;
}
