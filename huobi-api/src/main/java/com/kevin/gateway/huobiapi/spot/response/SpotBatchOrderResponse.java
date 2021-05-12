package com.kevin.gateway.huobiapi.spot.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 批量下单
 */
@NoArgsConstructor
@Data
public class SpotBatchOrderResponse {

    @JsonProperty("status")
    private String status;
    @JsonProperty("data")
    private List<DataDTO> data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("client-order-id")
        private String clientorderid;
        @JsonProperty("order-id")
        private String orderId;
        @JsonProperty("err-code")
        private String errcode;
        @JsonProperty("err-msg")
        private String errmsg;
    }
}
