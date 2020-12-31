package com.kevin.gateway.huobiapi.spot.request.marginLoanC2C;

import com.kevin.gateway.huobiapi.base.util.OrderSide;
import com.kevin.gateway.huobiapi.spot.SpotCoin;
import lombok.Data;

/**
 * 撤销所有借入借出订单
 */
@Data
public class SpotC2cCancelAllRequest {
    private String accountId;//账户ID（缺省值：所有账户）
    private SpotCoin currency;//借入/借出币种（缺省值：所有适用C2C的币种）
    private OrderSide side;//订单方向（有效值：lend, borrow；缺省值：所有方向）
}
