package com.alchemy.gateway.exchangeclients.huobi.domain;

import com.alchemy.gateway.core.order.RoleType;
import com.alchemy.gateway.core.order.TradeVo;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author kevin chen
 */
@Data
public class HuobiTradeVo implements TradeVo {
    private String exchangeOrderId;
    private String exchangeName;
    private Long exchangeTradeId;
    private BigDecimal filledAmount;
    private BigDecimal filledFee;
    private BigDecimal price;
    private RoleType role;
    /**
     * 手续费抵扣币种
     */
    private String feeDeductCoin;
    /**
     * 抵扣数量
     */
    private BigDecimal feeDeductAmount;
    private LocalDateTime createdAt;

    @Override
    public String getExchangeOrderId() {
        return this.exchangeOrderId;
    }

    @Override
    public String getExchangeName() {
        return this.exchangeName;
    }

    @Override
    public String getExchangeTradeId() {
        return this.exchangeOrderId;
    }

    @Override
    public BigDecimal getFilledAmount() {
        return this.filledAmount;
    }

    @Override
    public BigDecimal getFilledFee() {
        return this.filledFee;
    }

    @Override
    public String getFilledFeeCoin() {
        return null;
    }

    @Override
    public BigDecimal getPrice() {
        return this.price;
    }

    @Override
    public RoleType getRole() {
        return this.role;
    }

    @Override
    public String getFeeDeductCoin() {
        return this.feeDeductCoin;
    }

    @Override
    public BigDecimal getFeeDeductAmount() {
        return this.feeDeductAmount;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}
