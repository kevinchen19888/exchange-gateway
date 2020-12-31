package com.kevin.gateway.huobiapi.spot.response.subUser;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.model.SpotAccountType;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 子用户余额（汇总）
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotSubUserAggregateBalanceResponse extends SpotBaseResponse {
    private List<SpotSubUserAggregateBalance> data;

    @Data
    public static class SpotSubUserAggregateBalance {
        private SpotCoin currency;//币种
        private SpotAccountType type;//账户类型	spot：现货账户，point：点卡账户, margin:逐仓杠杆账户，super-margin：全仓杠杆账户
        private BigDecimal balance;    //账户余额（可用余额和冻结余额的总和）
    }
}
