package com.kevin.gateway.huobiapi.spot.response.marginLoan;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.model.SpotMarginAccountInfoType;
import com.kevin.gateway.huobiapi.spot.model.SpotMarginAccountState;
import com.kevin.gateway.huobiapi.spot.model.SpotMarginAccountType;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 借币账户详情（全仓）
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotCrossMarginAccountBalanceResponse extends SpotBaseResponse {
    private SpotCrossMarginAccountBalanceInfo data;

    @Data
    private static class SpotCrossMarginAccountBalanceInfo {
        private String id;//账户编号
        private SpotMarginAccountState state;//账户状态	working,fl-sys,fl-end,fl-negative
        @JsonProperty(value = "risk-rate")
        private BigDecimal riskRate;//	风险率
        @JsonProperty(value = "acct-balance-sum")
        private BigDecimal acctBalanceSum;//总持有usdt折合
        @JsonProperty(value = "debt-balance-sum")
        private BigDecimal debtBalanceSum;//总负债usdt折合

        private SpotMarginAccountType type;	//账户类型 (margin or cross-margin)	cross-margin


        private List<SpotCrossMarginAccountBalance> list;//借币账户详情列表

        @Data
        private static class SpotCrossMarginAccountBalance {
            private SpotCoin currency;//	币种
            private SpotMarginAccountInfoType type;//类型	type: 交易余额, frozen: 冻结余额, loan: 待还借贷本金, interest: 待还借贷利息
            private BigDecimal balance;//	余额，负数表示应还金额
        }
    }
}
