package com.kevin.gateway.okexapi.fundingaccount.domain;

import com.kevin.gateway.core.Coin;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 提币Vo
 */
@Data
public class WithdrawalResponse {
    /**
     * 提币币种
     */
    private Coin currency;
    /**
     * 提币数量
     */
    private BigDecimal amount;
    /**
     * 提币申请ID
     */
    private String withdrawalId;
    /**
     * 提币申请结果。若是提现申请失败，将给出错误码提示
     */
    private Boolean result;
}
