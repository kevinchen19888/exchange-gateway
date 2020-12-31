package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.future.type.MarginMode;
import lombok.Data;

@Data
public class SetMarginModeResponse {

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


    /**
     * 返回设定结果
     */
    private String result;

}

