package com.kevin.gateway.huobiapi.spot.request.marginloanc2c;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 还币
 */
@Data
public class SpotC2cRepaymentRequest {
    private String accountId;//还币账户ID
    private SpotCoin currency;//还币币种
    private BigDecimal amount;    //还币金额
    private String offerId;//原始借入订单ID

}
