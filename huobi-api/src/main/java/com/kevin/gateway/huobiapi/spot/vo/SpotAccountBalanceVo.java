package com.kevin.gateway.huobiapi.spot.vo;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.model.BalanceType;
import com.kevin.gateway.huobiapi.spot.model.SpotAccountState;
import com.kevin.gateway.huobiapi.spot.model.SpotAccountType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 账户余额
 */
@Data
public class SpotAccountBalanceVo {

    private long id;
    /**
     * 账户状态
     * working：正常, lock：账户被锁定
     */
    private SpotAccountState state;
    /**
     * 账户类型
     * spot：现货账户，
     * margin：逐仓杠杆账户，
     * otc：OTC 账户，
     * point：点卡账户，
     * super-margin：全仓杠杆账户,
     * investment: C2C杠杆借出账户,
     * borrow: C2C杠杆借入账户
     */
    private SpotAccountType type;

    private List<SpotAccountBalanceInfo> list;

    @Data
    private static class SpotAccountBalanceInfo {
        private BigDecimal balance;//余额
        private SpotCoin currency;//币种
        private BalanceType type;//类型	trade: 交易余额，frozen: 冻结余额
    }
}
