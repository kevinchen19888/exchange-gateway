package com.kevin.gateway.huobiapi.spot.request.marginloan;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 归还借币（逐仓）
 */
@Data
public class SpotMarginOrderRepayRequest {
    private BigDecimal amount;//划转数量
}
