package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.core.CoinPair;
import lombok.Data;

@Data
public class LeverageCrossedItem extends LeverageBaseItem {

    private String marginMode = "crossed";
    /**
     * 标的指数，如：BTC-USD,BTC-USDT
     */
    private CoinPair underlying;

    /**
     * 全仓杠杆倍数
     */
    private int leverage;
}
