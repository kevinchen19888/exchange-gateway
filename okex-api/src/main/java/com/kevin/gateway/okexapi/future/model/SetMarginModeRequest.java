package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.future.type.MarginMode;
import lombok.Data;


/**
 * 设置合约币种账户模式
 */
@Data
public class SetMarginModeRequest {

    /**
     * 标的指数，如：BTC-USD,BTC-USDT
     */
    private CoinPair underlying;

    /**
     * 账户模式
     * crossed:全仓
     * fixed:逐仓
     */
    private MarginMode marginMode;

}

