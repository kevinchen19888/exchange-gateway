package com.alchemy.gateway.exchangeclients.okex.impl;

import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.order.OrderSide;
import com.alchemy.gateway.core.order.OrderState;
import com.alchemy.gateway.core.order.OrderType;
import com.alchemy.gateway.core.order.OrderVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Slf4j
public class OkexOrderVo implements OrderVo {
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
    private String rebateCoin;
    private BigDecimal rebate;


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
        if (state.equals("-2")) {//失败
            orderState = OrderState.SUBMIT_FAILED;
        }
        if (state.equals("-1")) {//撤单成功
            orderState = OrderState.CANCELLED;
        }
        if (state.equals("0")) {//等待成交
            orderState = OrderState.CREATED;
        }
        if (state.equals("1")) {//部分成交
            orderState = OrderState.PARTIAL_FILLED;
        }
        if (state.equals("2")) {//完全成交
            orderState = OrderState.FILLED;
        }
        if (state.equals("3")) {//下单中
            orderState = OrderState.CREATED;
        }
        if (state.equals("4")) {//撤单中
            orderState = OrderState.CANCELLING;
        }
        return orderState;
    }

    @Override
    public void setOrderState(String state) {
        switch (state) {
            case "submit_failed":
                this.state = "-2";
                break;
            case "cancelled":
                this.state = "-1";
                break;
            case "created":
                this.state = "0";
                break;
            case "partial_filled":
                this.state = "1";
                break;
            case "filled":
                this.state = "2";
                break;
            case "cancelling":
                this.state = "4";
                break;
            default:
                log.warn("暂不支持的订单状态转换,OrderState:{}", state);
                break;
        }

    }

    @Override
    public OrderType getType() {
        OrderType orderType = null;
        if (type.equals(OrderType.LIMIT.getName())) {
            orderType = OrderType.LIMIT;
        }
        if (type.equals(OrderType.MARKET.getName())) {
            orderType = OrderType.MARKET;
        }
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
        return rebateCoin;
    }

    @Override
    public void setRebateCoin(String rebateCoin) {
        this.rebateCoin = rebateCoin;
    }

    @Override
    public BigDecimal getRebate() {
        return rebate;
    }

    @Override
    public void setRebate(BigDecimal rabate) {
        this.rebate = rabate;
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
        setRebate(otherOrderVo.getRebate());
        setRebateCoin(otherOrderVo.getRebateCoin());
    }

    @Override
    public String toString() {
        return "OkexOrderVo{" +
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
                ", rebateCoin='" + rebateCoin + '\'' +
                ", rebate=" + rebate +
                '}';
    }
}
