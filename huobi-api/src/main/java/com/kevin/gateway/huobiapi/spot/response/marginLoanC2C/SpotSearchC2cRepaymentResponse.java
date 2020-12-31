package com.kevin.gateway.huobiapi.spot.response.marginLoanC2C;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 查询还币交易记录
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SpotSearchC2cRepaymentResponse extends SpotBaseResponse {
    private List<SpotSearchC2cRepaymentInfo> data;

    @Data
    private static class SpotSearchC2cRepaymentInfo {
        private String repayId;//还币交易ID
        private long repayTime;//还币交易时间（unix time in millisecond）
        private String accountId;//还币账户ID
        private SpotCoin currency;//还币币种
        private BigDecimal paidAmount;//已还币金额
        private List<SpotSearchC2cRepayment> transactIds;//还币交易ID列表（按还币优先顺序排列）

        @Data
        private static class SpotSearchC2cRepayment {
            private String transactId;//交易ID
            private BigDecimal paidPrincipal;//单笔还币交易已还本金
            private BigDecimal paidInterest;//单笔还币交易已还币息
        }
    }
}
