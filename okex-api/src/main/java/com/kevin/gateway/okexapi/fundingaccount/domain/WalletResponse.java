package com.kevin.gateway.okexapi.fundingaccount.domain;

import com.kevin.gateway.core.Coin;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 资金账户信息Vo
 */
@Data
public class WalletResponse {

    /**
     * 币种，如BTC
     */
    private Coin currency;
    /**
     * 余额
     */
    private BigDecimal balance;
    /**
     * 冻结（不可用）
     */
    private BigDecimal hold;
    /**
     * 可用余额
     */
    private BigDecimal available;
}
