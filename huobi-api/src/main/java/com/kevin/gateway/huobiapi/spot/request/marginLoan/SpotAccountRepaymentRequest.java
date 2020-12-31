package com.kevin.gateway.huobiapi.spot.request.marginLoan;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 归还借币（全仓）子用户可用
 */
@Data
public class SpotAccountRepaymentRequest {
    private String accountId;    //	还币账户ID
    private String transactId;    //原始借币交易ID
    private SpotCoin currency;//还币币种
    private BigDecimal amount;//还币金额
}
