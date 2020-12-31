package com.kevin.gateway.huobiapi.spot.response.spotAndMargin;

import com.kevin.gateway.huobiapi.spot.SpotMarketId;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 获取用户当前手续费率
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotTransactFeeRateResponse extends SpotBaseResponse {
    private List<SpotTransactFeeRate> data;

    @Data
    private static class SpotTransactFeeRate {
        private SpotMarketId symbol;    //	交易代码
        private BigDecimal makerFeeRate;    //	基础费率 - 被动方，如适用交易手续费返佣，返回返佣费率（负值）
        private BigDecimal takerFeeRate;    //	基础费率 - 主动方
        private BigDecimal actualMakerRate;    //	抵扣后费率 - 被动方，如不适用抵扣或未启用抵扣，返回基础费率；如适用交易手续费返佣，返回返佣费率（负值）
        private BigDecimal actualTakerRate;    //	抵扣后费率 – 主动方，如不适用抵扣或未启用抵扣，返回基础费率
    }
}
