package com.kevin.gateway.huobiapi.spot.response.basedata;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import com.kevin.gateway.huobiapi.spot.vo.SpotMarketStatusVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 获取当前市场状态
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotMarketStatusResponse extends SpotBaseResponse {
    private SpotMarketStatusVo data;
}
