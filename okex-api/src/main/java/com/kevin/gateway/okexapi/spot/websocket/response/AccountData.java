package com.kevin.gateway.okexapi.spot.websocket.response;

import com.kevin.gateway.core.Coin;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 币币账户信息
 */
@Data
public class AccountData {

    /**
     * 余额
     */
    private BigDecimal balance;
    /**
     * 可用于交易或资金划转的数量
     */
    private BigDecimal available;
    /**
     * 币种
     */
    private Coin currency;
    /**
     *
     */
    private String id;
    /**
     * 冻结（不可用）
     */
    private BigDecimal hold;

    private LocalDateTime timestamp;
}
