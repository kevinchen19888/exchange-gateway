package com.alchemy.gateway.exchangeclients.okex.impl;

import com.alchemy.gateway.core.order.RoleType;
import com.alchemy.gateway.core.order.TradeVo;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OkexTradeVo implements TradeVo {

    private String exchangeOrderId;
    private String exchangeName;
    private String exchangeTradeId;
    private BigDecimal filledAmount;
    private BigDecimal filledFee;
    private BigDecimal price;
    private String role;
    private String feeDeductCoin;
    private LocalDateTime createdAt;

    @Override
    public String getExchangeOrderId() {
        return exchangeOrderId;
    }

    @Override
    public String getExchangeName() {
        return exchangeName;
    }

    @Override
    public String getExchangeTradeId() {
        return exchangeTradeId;
    }

    @Override
    public BigDecimal getFilledAmount() {
        return filledAmount;
    }

    @Override
    public BigDecimal getFilledFee() {
        return filledFee;
    }

    @Override
    public String getFilledFeeCoin() {
        return null;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public RoleType getRole() {
        RoleType roleType = null;
        if (role.equals("T")) {
            roleType = RoleType.taker;
        }
        if (role.equals("M")) {
            roleType = RoleType.maker;
        }
        return roleType;
    }

    @Override
    public String getFeeDeductCoin() {
        return feeDeductCoin;
    }

    @Override
    public BigDecimal getFeeDeductAmount() {
        return null;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "OkexTradeVo{" +
                "exchangeOrderId='" + exchangeOrderId + '\'' +
                ", exchangeName='" + exchangeName + '\'' +
                ", exchangeTradeId='" + exchangeTradeId + '\'' +
                ", filledAmount=" + filledAmount +
                ", filledFee=" + filledFee +
                ", price=" + price +
                ", role='" + role + '\'' +
                ", feeDeductCoin='" + feeDeductCoin + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
