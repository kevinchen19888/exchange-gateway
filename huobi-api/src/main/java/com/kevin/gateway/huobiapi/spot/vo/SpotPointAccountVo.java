package com.kevin.gateway.huobiapi.spot.vo;

import com.kevin.gateway.huobiapi.spot.model.SpotAccountStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 点卡余额查询
 */
@Data
public class SpotPointAccountVo {
    private String accountId;//	账户ID
    private SpotAccountStatus accountStatus;//账户状态（working, lock, fl-sys, fl-mgt, fl-end, fl-negative）
    private BigDecimal acctBalance;    //	账户余额
    private List<PointAccountInfo> groupIds;    //	点卡分组ID列表

    @Data
    private static class PointAccountInfo {
        private long groupId;    //点卡分组ID
        private long expiryDate;    //点卡到期日（unix time in millisecond）
        private BigDecimal remainAmt;//剩余数量
    }
}
