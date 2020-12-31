package com.kevin.gateway.huobiapi.spot.vo;

import com.kevin.gateway.huobiapi.spot.model.SpotAccountState;
import com.kevin.gateway.huobiapi.spot.model.SpotAccountType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 账户信息
 */
@Data
public class SpotAccountsVo {
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
    /**
     * 子账户类型（仅对逐仓杠杆账户有效）
     */
    @JsonProperty(value = "subtype")
    private String subType;
}
