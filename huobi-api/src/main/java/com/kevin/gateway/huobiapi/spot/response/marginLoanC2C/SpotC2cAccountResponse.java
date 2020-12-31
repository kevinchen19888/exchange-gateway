package com.kevin.gateway.huobiapi.spot.response.marginLoanC2C;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.SpotMarketId;
import com.kevin.gateway.huobiapi.spot.model.SpotMarginAccountState;
import com.kevin.gateway.huobiapi.spot.model.SpotSubAccountType;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 查询账户余额
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SpotC2cAccountResponse extends SpotBaseResponse {
    private SpotC2cAccountInfo data;

    @Data
    private static class SpotC2cAccountInfo {
        private String accountId;//账户ID
        private SpotMarginAccountState accountStatus;//账户状态（working, lock, fl-sys, fl-mgt, fl-end, fl-negative）
        private SpotMarketId symbol;//交易对（仅对借入账户类型有效）
        private BigDecimal riskRate;//风险率（仅对借入账户类型有效）
        private List<SubAccountTypes> subAccountTypes;//账户子类型列表

        @Data
        private static class SubAccountTypes {
            private SpotSubAccountType subAccountType;//账户子类型（trade, lending, earnings, loan, interest, advance）
            private SpotCoin currency;//币种
            private BigDecimal acctBalance;//账户余额
            private BigDecimal availBalance;//可用余额 （仅对借入账户下trade子类型有效）
            private BigDecimal transferable;//可转出金额 （仅对借入账户下trade子类型有效）
            private BigDecimal borrowable;//可借入金额 （仅对借入账户下trade子类型有效）}
        }
    }
}
