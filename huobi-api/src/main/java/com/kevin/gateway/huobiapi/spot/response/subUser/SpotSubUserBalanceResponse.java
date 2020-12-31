package com.kevin.gateway.huobiapi.spot.response.subUser;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.model.BalanceType;
import com.kevin.gateway.huobiapi.spot.model.SpotAccountType;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 子用户余额
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotSubUserBalanceResponse  extends SpotBaseResponse {
    private List<SpotSubUserBalanceInfo> data;

    @Data
    private static class SpotSubUserBalanceInfo {
        private String id;//子用户 UID
        private SpotAccountType type;//账户类型	spot：现货账户，point：点卡账户, margin:逐仓杠杆账户，super-margin：全仓杠杆账户
        private List<SpotSubUserBalance> list;

        @Data
        public static class SpotSubUserBalance {
            private SpotCoin currency;//币种
            private BalanceType type;//账户类型	trade：交易账户，frozen：冻结账户
            private BigDecimal balance;    //账户余额（可用余额和冻结余额的总和）
        }
    }
}
