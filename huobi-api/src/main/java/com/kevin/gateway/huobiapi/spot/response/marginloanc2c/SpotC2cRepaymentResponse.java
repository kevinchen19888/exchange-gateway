package com.kevin.gateway.huobiapi.spot.response.marginloanc2c;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 还币
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SpotC2cRepaymentResponse extends SpotBaseResponse {
    private SpotC2cRepayment data;

    @Data
    private static class SpotC2cRepayment {
        private String repayId;    //订单ID
        private long repayTime;//还币交易时间（unix time in millisecond）
    }
}
