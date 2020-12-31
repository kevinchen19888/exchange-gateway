package com.kevin.gateway.huobiapi.spot.response.marginLoan;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 归还借币（全仓）:子用户可用
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotAccountRepaymentResponse extends SpotBaseResponse {
    private List<SpotAccountRepayment> data;

    @Data
    private static class SpotAccountRepayment {
        private String repayId;    //还币交易ID
        private long repayTime;//	还币交易时间（unix time in millisecond）
    }
}
