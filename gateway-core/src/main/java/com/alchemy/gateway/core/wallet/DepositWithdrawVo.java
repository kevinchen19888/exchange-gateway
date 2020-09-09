package com.alchemy.gateway.core.wallet;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * describe:充提记录
 *
 * @author zoulingwei
 */
@Data
public class DepositWithdrawVo {
    /**
     * 交易所充提记录id
     */
    private String exchangeRecordId;
    /**
     * 交易所充提记录类型
     */
    private DepositWithdrawType type;
    /**
     * 充提币种
     */
    private String coin;
    /**
     * 充提数量
     */
    private BigDecimal amount;
    /**
     * 充提手续费
     */
    private BigDecimal fee;
    /**
     * 交易所充提记录状态
     */
    private DepositWithdrawState state;
    /**
     * 交易所充提记录创建时间
     */
    private LocalDateTime createdAt;
}
