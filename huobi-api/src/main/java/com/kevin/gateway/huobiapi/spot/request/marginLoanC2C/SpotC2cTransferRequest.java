package com.kevin.gateway.huobiapi.spot.request.marginLoanC2C;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 资产划转
 */
@Data
public class SpotC2cTransferRequest {
    private String from	;//转出账户ID
    private String to	;//转入账户ID
    private SpotCoin currency	;//划转币种
    private BigDecimal amount	;//划转金额
}
