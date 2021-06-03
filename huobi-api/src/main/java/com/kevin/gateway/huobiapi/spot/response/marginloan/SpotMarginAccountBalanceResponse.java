package com.kevin.gateway.huobiapi.spot.response.marginloan;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.SpotMarketId;
import com.kevin.gateway.huobiapi.spot.model.SpotMarginAccountInfoType;
import com.kevin.gateway.huobiapi.spot.model.SpotMarginAccountState;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 借币账户详情（逐仓）
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotMarginAccountBalanceResponse extends SpotBaseResponse {
    private List<SpotMarginAccountBalanceInfo> data;

    @Data
    private static class SpotMarginAccountBalanceInfo {
        private SpotMarketId symbol;//交易对
        private SpotMarginAccountState state;//账户状态	working,fl-sys,fl-mgt,fl-end
        @JsonProperty(value = "risk-rate")
        private BigDecimal riskRate;//	风险率
        @JsonProperty(value = "fl-price")
        private BigDecimal flPrice;//爆仓价
        private List<SpotMarginAccountBalance> list;//借币账户详情列表

        @Data
        private static class SpotMarginAccountBalance {
            private SpotCoin currency;//	币种
            private SpotMarginAccountInfoType type;//类型	type: 交易余额, frozen: 冻结余额, loan: 待还借贷本金, interest: 待还借贷利息
            private BigDecimal balance;//	余额，负数表示应还金额
        }
    }


}
