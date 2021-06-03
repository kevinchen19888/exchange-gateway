package com.kevin.gateway.huobiapi.spot.response.marginloanc2c;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.model.*;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 查询特定借入借出订单及其交易记录
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SpotSearchC2cOfferByIdResponse extends SpotBaseResponse {
    private SpotSearchC2cOfferByIdInfo data;

    @Data
    private static class SpotSearchC2cOfferByIdInfo {
        private String offerId;    //订单ID
        private long createTime;//订单创建时间（unix time in millisecond）
        private long lastActTime;//订单更新时间（unix time in millisecond）
        private OrderState offerStatus;    //订单状态（有效值：submitted, filled, partial-filled, canceled, partial-canceled）
        private String accountId;    //账户ID
        private SpotCoin currency;    //借入/借出币种
        private SpotC2cSide side;//订单方向（有效值：lend, borrow）
        private SpotTimeInForce timeInForce;    //订单有效期（gtc, ioc）
        private BigDecimal origAmount;    //订单原始金额
        private BigDecimal amount;    //订单剩余金额
        private BigDecimal interestRate;    //日息率
        private SpotLoanTerm loanTerm;//借币期限
        private List<SpotSearchC2cOfferById> transactions;//按transactTime倒序排列

        @Data
        private static class SpotSearchC2cOfferById {
            private BigDecimal transactRate;    //交易价格（即达成交易的日息率）
            private BigDecimal transactAmount;    //交易金额
            private long transactTime;//交易时间（unix time in millisecond）
            private long transactId;//交易ID
            private boolean aggressor;//是否交易主动方（有效值：true, false）
            private BigDecimal unpaidPrincipal;    //未还本金
            private BigDecimal unpaidInterest;    //未还币息（截至查询时间）
            private BigDecimal paidInterest;    //已还币息
            private SpotTransactStatus transactStatus;    //还币状态（有效值：pending, closed）
        }
    }
}
