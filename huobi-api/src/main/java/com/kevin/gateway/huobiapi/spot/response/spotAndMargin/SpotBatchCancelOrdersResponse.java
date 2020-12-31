package com.kevin.gateway.huobiapi.spot.response.spotAndMargin;

import com.kevin.gateway.huobiapi.spot.model.SpotOrderState;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 批量撤销订单
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotBatchCancelOrdersResponse extends SpotBaseResponse {
    private SpotBatchCancelOrdersInfo data;

    @Data
    private static class SpotBatchCancelOrdersInfo {
        private String[] success;
        private List<SpotBatchCancelOrders> failed;

        @Data
        private static class SpotBatchCancelOrders {
            @JsonProperty(value = "order-id")
            private String orderId;    //	订单编号（如用户创建订单时包含order-id，返回中也须包含此字段）
            @JsonProperty(value = "client-order-id")
            private String clientOrderId;    //	用户自编订单号（如用户创建订单时包含client-order-id，返回中也须包含此字段）
            @JsonProperty(value = "err-code")
            private String errCode;//订单被拒错误码（仅对被拒订单有效）
            @JsonProperty(value = "err-msg")
            private String errMsg;//	订单被拒错误信息（仅对被拒订单有效）
            @JsonProperty(value = "order-state")
            private SpotOrderState orderState;//	当前订单有效
        }
    }
}
