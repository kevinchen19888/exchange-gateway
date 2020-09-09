package com.alchemy.gateway.broker.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class SwapOrderCancelMessage {
    @JsonProperty("exchange_name")
    private String exchangeName;
    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("order_id")
    private String orderId;
}
