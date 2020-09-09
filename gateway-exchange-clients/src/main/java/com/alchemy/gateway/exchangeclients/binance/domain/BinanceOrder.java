package com.alchemy.gateway.exchangeclients.binance.domain;

import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.order.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

/**
 * @author kevin chen
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class BinanceOrder extends BaseOrder implements OrderVo {

    private String orderType;
    private String stopPrice;
    private Market market;
    /**
     * 成交数量
     */
    private BigDecimal finishedVolume;
    /**
     * 成交金额
     */
    private BigDecimal finishedAmount;
    private BigDecimal finishedFees;
    private String createdAt;
    private String canceledAt;
    private String updatedAt;
    private String rebateCoin;
    private BigDecimal rebate;
    private LocalDateTime finishAt;

    @Override
    public Long getMineOrderId() {
        return super.getMineOrderId();
    }

    @Override
    public void setMineOrderId(Long mineOrderId) {
        super.setMineOrderId(mineOrderId);
    }


    @Override
    public String getExchangeName() {
        return super.getExchangeName();
    }

    @Override
    public Market getMarket() {
        return this.market;
    }

    @Override
    public String getExchangeOrderId() {
        if (StringUtils.hasText(super.getOrderId())) {
            return super.getOrderId();
        }
        return null;
    }

    @Override
    public OrderState getOrderState() {
        String orderState = super.getState();
        if (StringUtils.hasText(orderState)) {
            switch (orderState) {
                case "NEW":
                    return OrderState.CREATED;
                case "PARTIALLY_FILLED":
                    return OrderState.PARTIAL_FILLED;
                case "CANCELED":
                    return OrderState.CANCELLED;
                case "REJECTED":
                    return OrderState.SUBMIT_FAILED;
                case "FILLED":
                    return OrderState.FILLED;
                default:
                    log.warn("getOrderState暂不支持的binance订单类型,orderState:{}", orderState);
                    break;
            }
        }
        return null;
    }

    @Override
    public void setOrderState(String state) {
        switchState(state);
    }

    @Override
    public OrderType getType() {
        if (StringUtils.hasText(this.orderType)) {
            switch (this.orderType) {
                case "LIMIT":
                    return OrderType.LIMIT;
                case "MARKET":
                    return OrderType.MARKET;
                case "STOP_LOSS_LIMIT":
                    return OrderType.STOP_LIMIT;
                default:
                    throw new IllegalArgumentException("不支持的OrderType:" + this.orderType);
            }
        }
        return null;
    }

    @Override
    public OrderSide getSide() {
        if (StringUtils.hasText(super.getOrderSide())) {
            switch (super.getOrderSide()) {
                case "BUY":
                    return OrderSide.BUY;
                case "SELL":
                    return OrderSide.SELL;
                default:
                    throw new IllegalArgumentException("不支持的OrderSide:" + super.getOrderSide());
            }
        }
        return null;
    }

    @Override
    public BigDecimal getPrice() {
        if (StringUtils.hasText(super.getOrderPrice())) {
            return new BigDecimal(super.getOrderPrice());
        }
        return null;
    }

    @Override
    public BigDecimal getAmount() {
        if (StringUtils.hasText(super.getOrderAmount())) {
            return new BigDecimal(super.getOrderAmount());
        }
        return null;
    }

    @Override
    public BigDecimal getStopPrice() {
        if (StringUtils.hasText(this.stopPrice)) {
            return new BigDecimal(this.stopPrice);
        }
        return null;
    }

    @Override
    public BigDecimal getFinishedVolume() {
        return this.finishedVolume;
    }

    @Override
    public void setFinishedVolume(BigDecimal finishedVolume) {
        this.finishedVolume = finishedVolume;
    }

    @Override
    public BigDecimal getFinishedAmount() {
        return this.finishedAmount;
    }

    @Override
    public void setFinishedAmount(BigDecimal finishedAmount) {
        this.finishedAmount = finishedAmount;
    }

    @Override
    public BigDecimal getFinishedFees() {
        return this.finishedFees;
    }

    @Override
    public void setFinishedFees(BigDecimal finishedFees) {
        this.finishedFees = finishedFees;
    }

    @Override
    public String getRebateCoin() {
        return this.rebateCoin;
    }

    @Override
    public void setRebateCoin(String rebateCoin) {
        this.rebateCoin = rebateCoin;
    }

    @Override
    public BigDecimal getRebate() {
        return this.rebate;
    }

    @Override
    public void setRebate(BigDecimal rebate) {
        this.rebate = rebate;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        if (StringUtils.isEmpty(getOrderCreatedAt())) {
            return null;
        }
        return LocalDateTime.ofEpochSecond(Long.parseLong(getOrderCreatedAt()) / 1000, 0, ZoneOffset.UTC);
    }

    @Override
    public LocalDateTime getFinishedAtAt() {
        return this.finishAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return null;
    }

    @Override
    public void update(OrderVo otherOrder) {
        if (Objects.isNull(otherOrder)) {
            return;
        }
        this.finishedAmount = otherOrder.getFinishedAmount();
        this.finishedFees = otherOrder.getFinishedFees();
        this.finishedVolume = otherOrder.getFinishedVolume();
        String state = otherOrder.getOrderState().name().toLowerCase();
        switchState(state);
        this.rebate = otherOrder.getRebate();
        this.rebateCoin = otherOrder.getRebateCoin();
    }

    private void switchState(String state) {
        if (StringUtils.hasText(state)) {
            switch (state) {
                case "created":
                    super.setState("NEW");
                    break;
                case "partial_filled":
                    super.setState("PARTIALLY_FILLED");
                    break;
                case "cancelled":
                //case "EXPIRED":
                    log.info("state is:{}", state);
                    super.setState("CANCELED");
                    break;
                case "filled":
                    super.setState("FILLED");
                    break;
                case "submit_failed":
                    super.setState("REJECTED");
                    break;
                default:
                    log.warn("暂不支持的binance订单状态:{}", state);
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return "BinanceOrder{" +
                "orderType='" + orderType + '\'' +
                ", stopPrice='" + stopPrice + '\'' +
                ", market=" + market +
                ", finishedVolume=" + finishedVolume +
                ", finishedAmount=" + finishedAmount +
                ", finishedFees=" + finishedFees +
                ", createdAt='" + createdAt + '\'' +
                ", canceledAt='" + canceledAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", rebateCoin='" + rebateCoin + '\'' +
                ", rebate=" + rebate +
                ", finishAt=" + finishAt +
                '}';
    }
}
