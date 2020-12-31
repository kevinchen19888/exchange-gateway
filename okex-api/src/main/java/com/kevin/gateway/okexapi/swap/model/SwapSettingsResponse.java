package com.kevin.gateway.okexapi.swap.model;

import com.kevin.gateway.okexapi.future.type.MarginMode;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SwapSettingsResponse {

    /**
     * 多仓杠杆
     */
    private BigDecimal longLeverage;

    /**
     * 持仓模式
     */
    private MarginMode marginMode;

    /**
     * 空仓杠杆
     */
    private BigDecimal shortLeverage;

    /**
     * 合约名称
     */
    private SwapMarketId instrumentId;

}

