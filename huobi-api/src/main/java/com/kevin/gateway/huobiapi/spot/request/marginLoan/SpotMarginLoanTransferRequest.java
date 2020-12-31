package com.kevin.gateway.huobiapi.spot.request.marginLoan;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.SpotMarketId;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 资产划转（逐仓）
 */
@Data
public class SpotMarginLoanTransferRequest {
    private SpotMarketId symbol;//交易对, e.g. btcusdt, ethbtc
    private SpotCoin currency;//币种
    private BigDecimal amount;//划转数量
}
