package com.kevin.gateway.huobiapi.spot.response.conditional;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 策略委托（触发前）撤单
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotCancelAlgoOrdersResponse extends SpotBaseResponse {
    private SpotCancelAlgoOrders data;

    @Data
    private static class SpotCancelAlgoOrders {
        private String[] accepted;    //已接受订单clientOrderId列表
        private String[] rejected;//已拒绝订单clientOrderId列表
    }
}
