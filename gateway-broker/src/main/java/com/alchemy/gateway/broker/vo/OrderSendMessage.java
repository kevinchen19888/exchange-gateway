package com.alchemy.gateway.broker.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderSendMessage {
    @JsonProperty("order_id")
    private Long orderId;    // 订单编号
    @JsonProperty("error_code")
    private int errorCode;  // 错误编码 0表示成功
    @JsonProperty("error_text")
    private String errorText;  // 错误信息  否
}
