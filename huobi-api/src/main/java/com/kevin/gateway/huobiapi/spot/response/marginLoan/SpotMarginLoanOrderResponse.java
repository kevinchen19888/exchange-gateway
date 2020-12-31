package com.kevin.gateway.huobiapi.spot.response.marginLoan;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.SpotMarketId;
import com.kevin.gateway.huobiapi.spot.model.SpotMarginLoanOrderSate;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 查询借币订单（逐仓）
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotMarginLoanOrderResponse extends SpotBaseResponse {
    private List<SpotMarginLoanOrder> data;

    @Data
    private static class SpotMarginLoanOrder {
        private String id;//订单号
        @JsonProperty(value = "user-id")
        private String userId;//用户ID
        @JsonProperty(value = "account-id")
        private String accountId;//账户ID
        @JsonProperty(value = "")
        private SpotMarketId symbol;//交易对
        private SpotCoin currency;//币种
        @JsonProperty(value = "loan-amount")
        private BigDecimal loanAmount;//借币本金总额
        @JsonProperty(value = "loan-balance")
        private BigDecimal loanBalance;//未还本金
        @JsonProperty(value = "interest-rate")
        private BigDecimal interestRate;//币息率
        @JsonProperty(value = "interest-amount")
        private BigDecimal interestAmount;//币息总额
        @JsonProperty(value = "interest-balance")
        private BigDecimal interestBalance;//未还币息
        @JsonProperty(value = "created-at")
        private long createdAt;//借币发起时间
        @JsonProperty(value = "accrued-at")
        private long accruedAt;//最近一次计息时间
        private SpotMarginLoanOrderSate state;//订单状态	created 未放款，accrual 已放款，cleared 已还清，invalid 异常
        @JsonProperty(value = "paid-point")
        private BigDecimal paidPoint;//已支付点卡金额（用于还息）
        @JsonProperty(value = "paid-coin")
        private BigDecimal paidCoin;//已支付原币金额（用于还息）
        @JsonProperty(value = "deduct-rate")
        private BigDecimal deductRate;//抵扣率（用于还息）
        @JsonProperty(value = "deduct-currency")
        private String deductCurrency;//抵扣币种（用于还息）
        @JsonProperty(value = "deduct-amount")
        private BigDecimal deductAmount;//抵扣金额（用于还息）
        @JsonProperty(value = "updated-at")
        private long updatedAt;//更新时间
        @JsonProperty(value = "hour-interest-rate")
        private BigDecimal hourInterestRate;//时息率
        @JsonProperty(value = "day-interest-rate")
        private BigDecimal dayInterestRate;//日息率
    }
}
