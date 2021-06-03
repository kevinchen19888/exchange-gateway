package com.kevin.gateway.huobiapi.spot.response.marginloanc2c;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.model.SpotC2cSide;
import com.kevin.gateway.huobiapi.spot.model.SpotTransactStatus;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 查询借入借出交易记录
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SpotSearchC2cTransactionsResponse extends SpotBaseResponse {
    private List<SpotSearchC2cTransactions> data;

    @Data
    private static class SpotSearchC2cTransactions {
        private BigDecimal transactRate;    //交易价格（即达成交易的日息率）
        private BigDecimal transactAmount;    //交易金额
        private long transactTime;//交易时间（unix time in millisecond）
        private String transactId;//交易ID
        private boolean aggressor;//是否交易主动方（有效值：true, false）
        private BigDecimal unpaidPrincipal;    //未还本金
        private BigDecimal unpaidInterest;    //未还币息（截至查询时间）
        private BigDecimal paidInterest;//已还币息
        private SpotTransactStatus transactStatus;    //还币状态（有效值：pending, closed）
        private String offerId;//订单ID
        private String accountId;    //账户ID
        private SpotCoin currency;//借入/借出币种
        private SpotC2cSide side; //订单方向（有效值：lend, borrow）
    }
}
