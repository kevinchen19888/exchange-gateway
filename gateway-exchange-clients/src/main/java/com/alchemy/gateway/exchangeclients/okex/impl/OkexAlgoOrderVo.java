package com.alchemy.gateway.exchangeclients.okex.impl;

import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.order.OrderSide;
import com.alchemy.gateway.core.order.OrderState;
import com.alchemy.gateway.core.order.OrderType;
import com.alchemy.gateway.core.order.OrderVo;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OkexAlgoOrderVo implements OrderVo {
    private Long mineOrderId;
    private String exchangeName;
    private Market market;
    private String exchangeOrderId;
    private String state;
    private String type;
    private String side;
    private BigDecimal price;
    private BigDecimal amount;
    private BigDecimal finishedVolume;
    private BigDecimal finishedAmount;
    private BigDecimal finishedFees;
    private LocalDateTime createdAt;
    private BigDecimal stopPrice;


    @Override
    public Long getMineOrderId() {
        return mineOrderId;
    }

    @Override
    public String getExchangeName() {
        return exchangeName;
    }

    @Override
    public Market getMarket() {
        return market;
    }

    @Override
    public String getExchangeOrderId() {
        return exchangeOrderId;
    }

    @Override
    public OrderState getOrderState() {
        OrderState orderState = null;

        if (state.equals("1")) {//待生效
            orderState = OrderState.CREATED;
        }
        if (state.equals("2")) {//已生效
            orderState = OrderState.FILLED;
        }
        if (state.equals("3")) {//已撤销
            orderState = OrderState.CANCELLED;
        }
        if (state.equals("4")) {//部分生效
            orderState = OrderState.PARTIAL_FILLED;
        }
        if (state.equals("5")) {//暂停生效
            orderState = OrderState.CREATED;
        }
        if (state.equals("6")) {//委托失败
            orderState = OrderState.SUBMIT_FAILED;
        }
        return orderState;
    }

    @Override
    public void setOrderState(String state) {
        if (state.equals(OrderState.CREATED.name().toLowerCase())) {//待生效
            this.state = "1";
        }
        if (state.equals(OrderState.FILLED.name().toLowerCase())) {//已生效
            this.state = "2";
        }
        if (state.equals(OrderState.CANCELLED.name().toLowerCase())) {//已撤销
            this.state = "3";
        }
        if (state.equals(OrderState.PARTIAL_FILLED.name().toLowerCase())) {//部分生效
            this.state = "4";
        }
        if (state.equals(OrderState.CREATED.name().toLowerCase())) {//暂停生效
            this.state = "5";
        }
        if (state.equals(OrderState.SUBMIT_FAILED.name().toLowerCase())) {//委托失败
            this.state = "6";
        }
    }

    @Override
    public OrderType getType() {
        OrderType orderType = null;
        if (type.equals("1")) {//止盈止损
            orderType = OrderType.STOP_LIMIT;
        }
        return orderType;
    }

    @Override
    public OrderSide getSide() {
        OrderSide orderSide = null;
        if (side.toUpperCase().equals(OrderSide.BUY.name())) {
            orderSide = OrderSide.BUY;
        }
        if (side.toUpperCase().equals(OrderSide.SELL.name())) {
            orderSide = OrderSide.SELL;
        }
        return orderSide;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public BigDecimal getStopPrice() {
        return stopPrice;
    }

    @Override
    public BigDecimal getFinishedVolume() {
        return finishedVolume;
    }

    @Override
    public BigDecimal getFinishedAmount() {
        return finishedAmount;
    }

    @Override
    public BigDecimal getFinishedFees() {
        return finishedFees;
    }

    @Override
    public String getRebateCoin() {
        return null;
    }

    @Override
    public void setRebateCoin(String rebateCoin) {

    }

    @Override
    public BigDecimal getRebate() {
        return null;
    }

    @Override
    public void setRebate(BigDecimal rabate) {

    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public LocalDateTime getFinishedAtAt() {
        return null;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return null;
    }

    @Override
    public void update(OrderVo otherOrderVo) {
        setFinishedAmount(otherOrderVo.getFinishedAmount());
        setFinishedFees(otherOrderVo.getFinishedFees());
        setFinishedVolume(otherOrderVo.getFinishedVolume());
        setOrderState(otherOrderVo.getOrderState().name().toLowerCase());
    }

    @Override
    public String toString() {
        return "OkexAlgoOrderVo{" +
                "mineOrderId=" + mineOrderId +
                ", exchangeName='" + exchangeName + '\'' +
                ", market=" + market +
                ", exchangeOrderId='" + exchangeOrderId + '\'' +
                ", state='" + state + '\'' +
                ", type='" + type + '\'' +
                ", side='" + side + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", finishedVolume=" + finishedVolume +
                ", finishedAmount=" + finishedAmount +
                ", finishedFees=" + finishedFees +
                ", createdAt=" + createdAt +
                ", stopPrice=" + stopPrice +
                '}';
    }
}
