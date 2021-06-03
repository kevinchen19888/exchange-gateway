package com.kevin.gateway.huobiapi.spot.response.marketdata;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import com.kevin.gateway.huobiapi.spot.vo.SpotHistoryTradeVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 获得近期交易记录
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SpotHistoryTradeResponse extends SpotBaseResponse {
    private List<SpotHistoryTradeVo> data;
}
