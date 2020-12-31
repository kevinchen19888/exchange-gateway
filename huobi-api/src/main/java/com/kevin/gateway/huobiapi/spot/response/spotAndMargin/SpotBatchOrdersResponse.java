package com.kevin.gateway.huobiapi.spot.response.spotAndMargin;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 批量下单
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotBatchOrdersResponse extends SpotBaseResponse {
    private List<SpotBatchOrders> data;

    @Data
    private static class SpotBatchOrders {
        @JsonProperty(value = "order-id")
        private String orderId;    //	订单编号
        @JsonProperty(value = "client-order-id")
        private String clientOrderId;    //	用户自编订单号（如有）
        @JsonProperty(value = "err-code")
        private String errCode;//订单被拒错误码（仅对被拒订单有效）
        @JsonProperty(value = "err-msg")
        private String errMsg;//	订单被拒错误信息（仅对被拒订单有效）
    }
}
