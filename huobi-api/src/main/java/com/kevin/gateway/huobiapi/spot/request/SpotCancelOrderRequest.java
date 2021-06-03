package com.kevin.gateway.huobiapi.spot.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 撤销订单
 */
@Data
public class SpotCancelOrderRequest {
    @JsonProperty(value = "client-order-id")
    private String clientOrderId;    //	用户自编订单号（最大长度64个字符，须在24小时内保持唯一性）
}
