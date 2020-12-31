package com.kevin.gateway.okexapi.spot.vo;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.spot.SpotMarketId;
import lombok.Data;

@Data
public class SpotCandleData {
    /**
     * 		开始时间
     */
    private SpotMarketId instrumentId;
    /**
     *		K线详情
     */
    private SpotCandle candle;

}
