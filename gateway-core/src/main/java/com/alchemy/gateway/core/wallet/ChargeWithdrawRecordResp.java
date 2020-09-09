package com.alchemy.gateway.core.wallet;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author kevin chen
 */
@Data
public class ChargeWithdrawRecordResp {
    private String id;
    private ChargeWithdrawTypeEnum chargeWithdrawType;
    private String coin;    // 币种
    private BigDecimal amount; // 数量
    private BigDecimal fee; // 费用
    /**
     * 状态(成功;失败)
     */
    private boolean success;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;

}
