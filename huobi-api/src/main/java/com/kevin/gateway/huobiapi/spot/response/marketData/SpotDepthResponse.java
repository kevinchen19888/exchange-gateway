package com.kevin.gateway.huobiapi.spot.response.marketData;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import com.kevin.gateway.huobiapi.spot.vo.SpotDepthVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 市场深度数据
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SpotDepthResponse extends SpotBaseResponse {
    private SpotDepthVo tick;
}
