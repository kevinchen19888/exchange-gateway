package com.alchemy.gateway.core.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * describe:
 *
 * @author zoulingwei
 */
public interface TradeVo {

    String getExchangeOrderId();

    String getExchangeName();

    String getExchangeTradeId();

    /**
     * 成交数量
     */
    BigDecimal getFilledAmount();

    BigDecimal getFilledFee();

    /**
     * 手续费用币种（没有返佣时为 NULL）
     */
    String getFilledFeeCoin();

    BigDecimal getPrice();

    RoleType getRole();

    /**
     * 抵扣费用币种（没有手续费时为 NULL）
     */
    String getFeeDeductCoin();

    /**
     * 抵扣金额（抵扣币种金额、抵扣点卡等）
     */
    BigDecimal getFeeDeductAmount();

    LocalDateTime getCreatedAt();

}
