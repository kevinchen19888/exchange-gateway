package com.kevin.gateway.okexapi.fundingaccount.domain;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.okexapi.base.util.AccountType;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

/**
 * 资金划转Vo
 */
@Data
public class TransferResponse {
    /**
     * 划转ID
     */
    private String transferId;
    /**
     * 划转币种
     */
    private Coin currency;
    /**
     * 转出账户
     */
    private AccountType from;
    /**
     * 转入账户
     */
    private AccountType to;
    /**
     * 划转量
     */
    private BigDecimal amount;
    /**
     * 划转结果。若是划转失败，将给出错误码提示
     */
    private boolean result;

    /**
     * 错误码
     */
    @Nullable
    private String errorCode;

    /**
     * 错误信息
     */
    @Nullable
    private String errorMessage;

}
