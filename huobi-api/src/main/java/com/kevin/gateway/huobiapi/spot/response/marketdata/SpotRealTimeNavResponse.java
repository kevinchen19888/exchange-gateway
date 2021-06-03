package com.kevin.gateway.huobiapi.spot.response.marketdata;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import com.kevin.gateway.huobiapi.spot.vo.SpotRealTimeNavVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 获取杠杆ETP实时净值
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SpotRealTimeNavResponse extends SpotBaseResponse {
    private SpotRealTimeNavVo tick;

}
