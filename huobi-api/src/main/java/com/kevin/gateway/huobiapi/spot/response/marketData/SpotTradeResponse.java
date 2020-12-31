package com.kevin.gateway.huobiapi.spot.response.marketData;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import com.kevin.gateway.huobiapi.spot.vo.SpotTradeVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 最近市场成交记录
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SpotTradeResponse extends SpotBaseResponse {

    private SpotTradeInfo tick;

    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @Data
    public static class SpotTradeInfo extends SpotBaseResponse {
        private List<SpotTradeVo> data;
    }
}
