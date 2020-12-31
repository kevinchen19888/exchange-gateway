package com.kevin.gateway.huobiapi.spot.request.marginLoan;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 资产划转（全仓）
 */
@Data
public class SpotCrossMarginTransferRequest {
    private SpotCoin currency;//币种
    private BigDecimal amount;//划转数量
}
