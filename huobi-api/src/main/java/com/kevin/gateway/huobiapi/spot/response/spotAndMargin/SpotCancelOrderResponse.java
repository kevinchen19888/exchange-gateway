package com.kevin.gateway.huobiapi.spot.response.spotAndMargin;

import com.kevin.gateway.huobiapi.spot.model.SpotOrderState;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 撤销订单
 */
@Data
public class SpotCancelOrderResponse {
    private String data;
    private String status;    //	状态
    @JsonProperty(value = "err-code")
    private String errCode;//订单被拒错误码（仅对被拒订单有效）
    @JsonProperty(value = "err-msg")
    private String errMsg;//	订单被拒错误信息（仅对被拒订单有效）
    @JsonProperty(value = "order-state")
    private SpotOrderState orderState;//	当前订单有效
}
