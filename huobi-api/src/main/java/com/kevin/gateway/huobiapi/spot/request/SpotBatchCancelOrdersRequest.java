package com.kevin.gateway.huobiapi.spot.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 批量撤销订单
 */
@Data
public class SpotBatchCancelOrdersRequest {
    @JsonProperty(value = "order-ids")
    private String[] orderIds;//订单编号列表（order-ids和client-order-ids必须且只能选一个填写，不超过50张订单）		单次不超过50个订单
    @JsonProperty(value = "client-order-ids")
    private String[] clientOrderIds;//用户自编订单号列表（order-ids和client-order-ids必须且只能选一个填写，不超过50张订单）		单次不超过50个订单
}
