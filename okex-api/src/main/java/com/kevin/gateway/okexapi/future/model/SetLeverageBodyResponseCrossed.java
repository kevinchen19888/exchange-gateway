package com.kevin.gateway.okexapi.future.model;


import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.future.type.MarginMode;
import lombok.Data;

@Data
public class SetLeverageBodyResponseCrossed extends SetLeverageBodyResponseBase {

    /**
     * 全仓、逐仓参数 账户类型
     * 全仓：crossed
     * 逐仓：fixed
     */
    private MarginMode marginMode = MarginMode.crossed;


    /**
     * 全仓、逐仓参数  已设定的杠杆倍数，1-100的数值
     */
    private float leverage;


    /**
     * 全仓参数：  标的指数，如：BTC-USD，BTC-USDT
     */
    private CoinPair underlying;


    /**
     *
     */
    private Coin currency;


}
