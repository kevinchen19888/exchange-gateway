package com.kevin.gateway.huobiapi.spot.response.conditional;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 策略委托下单
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotAlgoOrdersResponse extends SpotBaseResponse {
    private SpotAlgoOrders data;

    @Data
    private static class SpotAlgoOrders {
        private String clientOrderId;
    }
}
