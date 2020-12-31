package com.kevin.gateway.huobiapi.spot.vo;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 获取杠杆ETP实时净值
 */
@Data
public class SpotRealTimeNavVo {
    private String symbol;    //	杠杆ETP交易代码
    private BigDecimal nav;    //	最新净值
    private long navTime;    //	最新净值更新时间 (unix time in millisecond)
    private BigDecimal outstanding;    //	ETP总份额
    private BigDecimal actualLeverage;    //	实际杠杆率

    private List<RealTimeNavInfo> basket;

    @Data
    private static class RealTimeNavInfo {
        private SpotCoin currency;    //	币种
        private BigDecimal amount;//金额
    }
}
