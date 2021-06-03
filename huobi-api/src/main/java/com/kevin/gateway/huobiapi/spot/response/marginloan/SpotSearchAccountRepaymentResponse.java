package com.kevin.gateway.huobiapi.spot.response.marginloan;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 还币交易记录查询（全仓）
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotSearchAccountRepaymentResponse extends SpotBaseResponse {
    private List<SpotSearchAccountRepaymentInfo> data;

    @Data
    private static class SpotSearchAccountRepaymentInfo {
        private String repayId;//	还币交易ID
        private long repayTime;//	还币交易时间（unix time in millisecond）
        private String accountId;    //	还币账户ID
        private SpotCoin currency;//	还币币种
        private BigDecimal repaidAmount;    //	已还币金额
        private SpotSearchAccountRepayment transactIds;    //该笔还币所涉及的原始借币交易ID列表（按还币优先顺序排列）

        @Data
        private static class SpotSearchAccountRepayment {
            private String transactId;//原始借币交易ID
            private BigDecimal repaidPrincipal;//	该笔还币交易已还本金
            private BigDecimal repaidInterest;//	该笔还币交易已还利息
            private BigDecimal paidHt;//	该笔还币交易已支付HT金额
            private BigDecimal paidPoint;//	该笔还币交易已支付点卡金额

        }
    }
}
