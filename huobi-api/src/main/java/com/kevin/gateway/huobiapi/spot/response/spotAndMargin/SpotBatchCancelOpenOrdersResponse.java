package com.kevin.gateway.huobiapi.spot.response.spotAndMargin;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *批量撤销订单(open order)
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotBatchCancelOpenOrdersResponse extends SpotBaseResponse {
    private SpotBatchCancelOpenOrders data;

    @Data
    private static class SpotBatchCancelOpenOrders {
        @JsonProperty(value = "success-count")
        private int successCount;//成功取消的订单数
        @JsonProperty(value = "failed-count")
        private int failedCount;//取消失败的订单数
        @JsonProperty(value = "next-id")
        private String nextId;    //下一个符合取消条件的订单号
    }
}

