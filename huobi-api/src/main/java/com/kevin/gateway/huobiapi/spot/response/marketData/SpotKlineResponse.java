package com.kevin.gateway.huobiapi.spot.response.marketData;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import com.kevin.gateway.huobiapi.spot.vo.SpotKlineVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * K 线数据（蜡烛图）
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SpotKlineResponse extends SpotBaseResponse {
    private List<SpotKlineVo> data;

}
