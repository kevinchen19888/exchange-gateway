package com.kevin.gateway.huobiapi.spot.response.marginLoanC2C;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 资产划转
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SpotC2cTransferResponse extends SpotBaseResponse {
    private SpotC2cTransfer data;

    @Data
    private static class SpotC2cTransfer {
        private String transactId;//划转交易ID
        private long transactTime;// 划转交易时间（unix time in millisecond）
    }
}
