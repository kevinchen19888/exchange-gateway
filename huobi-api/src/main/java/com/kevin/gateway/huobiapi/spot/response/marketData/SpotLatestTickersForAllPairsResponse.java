package com.kevin.gateway.huobiapi.spot.response.marketData;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import com.kevin.gateway.huobiapi.spot.vo.SpotLatestTickersForAllPairsVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 所有交易对的最新 Tickers
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SpotLatestTickersForAllPairsResponse extends SpotBaseResponse {

    private List<SpotLatestTickersForAllPairsVo> data;

}
