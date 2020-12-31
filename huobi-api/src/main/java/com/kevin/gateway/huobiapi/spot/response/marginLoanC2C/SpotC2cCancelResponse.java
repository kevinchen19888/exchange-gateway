package com.kevin.gateway.huobiapi.spot.response.marginLoanC2C;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 借入借出撤单
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SpotC2cCancelResponse extends SpotBaseResponse {
    private SpotC2cCancelInfo data;

    @Data
    private static class SpotC2cCancelInfo {
        private List<SpotC2cCancelAccepted> accepted;
        private List<SpotC2cCancelRejected> rejected;

        @Data
        private static class SpotC2cCancelRejected {
            private String offerId;    //订单ID
            private String errCode;    //撤单被拒错误码
            private String errMessage;//撤单被拒错误消息
        }

        @Data
        private static class SpotC2cCancelAccepted {
            private String offerId;    //订单ID
        }
    }
}
