package com.alchemy.gateway.exchangeclients.bitfinex.entity;

import com.alchemy.gateway.core.order.RoleType;
import com.alchemy.gateway.core.order.TradeVo;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class BitfinexTradeVo  implements TradeVo {


    private String exchangeOrderId;

    private String exchangeName;

    private String exchangeTradeId;


    private BigDecimal filledAmount;

    private BigDecimal filledFee;


    private BigDecimal price;


    private  RoleType  type;


    private String feeDeductCoin;


    private BigDecimal feeDeductAmount;


    private LocalDateTime createdAt;


    private Long orderId;



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
        return type;
    }

    @Override
    public String getFeeDeductCoin() {
        return feeDeductCoin;
    }

    @Override
    public BigDecimal getFeeDeductAmount() {
        return feeDeductAmount;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void setExchangeTradeId(String exchangeTradeId) {
        this.exchangeTradeId = exchangeTradeId;
    }

    public void setFilledAmount(BigDecimal filledAmount) {
        this.filledAmount = filledAmount;
    }

    public void setFilledFee(BigDecimal filledFee) {
        this.filledFee = filledFee;
    }

    public void setFeeDeductCoin(String feeDeductCoin) {
        this.feeDeductCoin = feeDeductCoin;
    }

    public void setFeeDeductAmount(BigDecimal feeDeductAmount) {
        this.feeDeductAmount = feeDeductAmount;
    }

    public void setExchangeOrderId(String exchangeOrderId) {
        this.exchangeOrderId = exchangeOrderId;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }



    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setType(RoleType type) {
        this.type = type;
    }



    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
