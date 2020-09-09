package com.alchemy.gateway.broker.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SwapOrderStoplimitMessage {
    @JsonProperty("exchange_name")
    private String exchangeName;
    private String symbol;
    @JsonProperty("account_id")
    private String accountId;
    private String direction;
    @JsonProperty("stop_price")
    private String stopPrice;
    private String price;
    private String amount;
    private String offset;
    @JsonProperty("order_id")
    private String orderId;
}
