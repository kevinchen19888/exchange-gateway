package com.kevin.gateway.huobiapi.spot.response.marketdata;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import com.kevin.gateway.huobiapi.spot.vo.SpotTickerVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 聚合行情（Ticker）
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SpotTickerResponse extends SpotBaseResponse {
    public SpotTickerVo tick;

}
