package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.future.type.AutoMarginType;
import lombok.Data;

@Data
public class SetAutoMarginResponse extends ResultCode {

    /**
     * 标的指数，如：BTC-USD,BTC-USDT
     */
    private CoinPair underlying;

    /**
     * 开/关自动增加保证金：1、开 2、关
     */
    private AutoMarginType type;


}

