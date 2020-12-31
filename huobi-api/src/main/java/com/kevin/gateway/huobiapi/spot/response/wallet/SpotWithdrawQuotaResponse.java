package com.kevin.gateway.huobiapi.spot.response.wallet;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 提币额度查询
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotWithdrawQuotaResponse extends SpotBaseResponse {
    private SpotWithdrawQuotaInfo data;

    @Data
    private static class SpotWithdrawQuotaInfo {
        private SpotCoin currency;//	币种
        private List<SpotWithdrawQuota> chains;

        @Data
        private static class SpotWithdrawQuota {
            private String chain;    //链名称
            private BigDecimal maxWithdrawAmt;    //单次最大提币金额
            private BigDecimal withdrawQuotaPerDay;    //当日提币额度
            private BigDecimal remainWithdrawQuotaPerDay;    //当日提币剩余额度
            private BigDecimal withdrawQuotaPerYear;    //当年提币额度
            private BigDecimal remainWithdrawQuotaPerYear;    //当年提币剩余额度
            private BigDecimal withdrawQuotaTotal;    //总提币额度
            private BigDecimal remainWithdrawQuotaTotal;//总提币剩余额度
        }
    }
}
