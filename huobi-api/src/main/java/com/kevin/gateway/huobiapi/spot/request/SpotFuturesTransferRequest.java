package com.kevin.gateway.huobiapi.spot.request;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.model.SpotTransferType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 币币现货账户与合约账户划转
 */
@Data
public class SpotFuturesTransferRequest {
    private SpotCoin currency;    //币种, e.g. btc
    private BigDecimal amount;    //划转数量
    private SpotTransferType type;    //	划转类型
}
