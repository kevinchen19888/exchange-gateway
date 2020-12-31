package com.kevin.gateway.huobiapi.spot.request.marginLoanC2C;

import com.kevin.gateway.huobiapi.base.util.OrderSide;
import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.model.SpotLoanTerm;
import com.kevin.gateway.huobiapi.spot.model.SpotTimeInForce;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 借入借出下单
 */
@Data
public class SpotC2cOfferRequest {
    private String accountId;//	借入账户ID（仅对借入订单有效）
    private SpotCoin currency;//借入/借出币种
    private OrderSide side;//	订单方向（lend, borrow）
    private SpotTimeInForce timeInForce;//	订单有效期（gtc, ioc）
    private BigDecimal amount;    //	订单金额
    private BigDecimal interestRate;    //	日息率
    private SpotLoanTerm loanTerm;//借币期限（单位：天；有效值：10, 20, 30）
}
