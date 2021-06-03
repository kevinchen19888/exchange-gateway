package com.kevin.gateway.huobiapi.spot.response.marginloan;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.SpotMarketId;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 查询借币币息率及额度（逐仓）
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotMarginLoanInfoResponse extends SpotBaseResponse {
    private List<SpotMarginLoanInfo> data;

    @Data
    public static class SpotMarginLoanInfo {
        private SpotMarketId symbol;//交易代码
        private List<SpotMarginLoan> currencies;

        @Data
        public static class SpotMarginLoan {
            private SpotCoin currency;    //	币种
            @JsonProperty(value = "interest-rate")
            private BigDecimal interestRate;    //	基础日币息率
            @JsonProperty(value = "min-loan-amt")
            private BigDecimal minLoanAmt;    //	最小允许借币金额
            @JsonProperty(value = "max-loan-amt")
            private BigDecimal maxLoanAmt;    //	最大允许借币金额
            @JsonProperty(value = "loanable-amt")
            private BigDecimal loanableAmt;    //	最大可借金额
            @JsonProperty(value = "actual-rate")
            private BigDecimal actualRate;    //	抵扣后的实际借币币息率，如不适用抵扣或未启用抵扣，返回基础日币息率
        }
    }
}
