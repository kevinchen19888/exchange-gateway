package com.kevin.gateway.huobiapi.spot.response.marketData;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import com.kevin.gateway.huobiapi.spot.vo.SpotLast24hMarketSummaryVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 最近24小时行情数据
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class SpotLast24hMarketSummaryResponse extends SpotBaseResponse {

    private SpotLast24hMarketSummaryVo tick;

}

