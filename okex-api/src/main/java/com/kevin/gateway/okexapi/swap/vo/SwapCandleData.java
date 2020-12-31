package com.kevin.gateway.okexapi.swap.vo;


import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

@Data
public class SwapCandleData {
    /**
     * 		开始时间
     */
    private SwapMarketId instrumentId;
    /**
     *		K线详情
     */
    private SwapCandle candle;

}
