package com.alchemy.gateway.broker.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderMarketMessage {
    @JsonProperty("exchange_name")
    private String exchangeName;//交易所名称
    private String symbol;//币对
    @JsonProperty("account_id")
    private String accountId;//账户id
    private Integer direction;//买卖方向
    private String amount;//数量
    @JsonProperty("order_id")
    private String orderId;//订单id
}
