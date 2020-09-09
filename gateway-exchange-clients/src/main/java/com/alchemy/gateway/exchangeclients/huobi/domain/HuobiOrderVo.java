package com.alchemy.gateway.exchangeclients.huobi.domain;

import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.order.*;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alchemy.gateway.exchangeclients.huobi.util.HuobiCoinPairSymbolConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.alchemy.gateway.core.order.OrderState.CREATED;

/**
 * @author kevin chen
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class HuobiOrderVo extends BaseOrder implements OrderVo {
    private String orderType;
    /**
     * 已成交数量
     */
    private BigDecimal finishedVolume;
    LocalDateTime updatedAt;
    LocalDateTime finishedAt;
    BigDecimal finishedFees;
    /**
     * 已成交金额
     */
    BigDecimal finishedAmount;
    private BigDecimal stopPrice;
    private String exchangeOrderId;
    //    private OrderState orderState;
    private BigDecimal rebate;
    private String rebateCoin;

    @Override
    public Long getMineOrderId() {
        return super.getMineOrderId();
    }

    @Override
    public String getExchangeName() {
        return super.getExchangeName();
    }

    @Override
    public Market getMarket() {
        HuobiCoinPairSymbolConverter converter = new HuobiCoinPairSymbolConverter();
        return Market.spotMarket(converter.symbolToCoinPair(super.getSymbol()));
    }

    @Override
    public String getExchangeOrderId() {
        return this.exchangeOrderId;
    }

    @Override
    public OrderState getOrderState() {
        String state = super.getState();
        if (StringUtils.hasText(state)) {
            switch (state) {
                case "submitted":
                    return CREATED;
                case "partial-filled":
                    return OrderState.PARTIAL_FILLED;
                case "canceled":
                    return OrderState.CANCELLED;
                case "filled":
                    return OrderState.FILLED;
                default:
                    log.warn("getOrderState暂不支持的订单状态转换,OrderState:{}", state);
                    return null;
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
        String orderType = this.getOrderType();
        if (StringUtils.hasText(orderType)) {
            switch (this.orderType) {
                case "buy-market":
                case "sell-market":
                    return OrderType.MARKET;
                case "buy-limit":
                case "sell-limit":
                    return OrderType.LIMIT;
                case "buy-stop-limit":
                case "sell-stop-limit":
                    return OrderType.STOP_LIMIT;
                default:
                    throw new IllegalArgumentException("暂不支持的OrderType:" + this.orderType);
            }
        }
        return null;
    }

    @Override
    public OrderSide getSide() {
        String orderSide = this.getOrderSide();
        if (StringUtils.hasText(orderSide)) {
            if (orderSide.contains("buy")) {
                return OrderSide.BUY;
            }
            if (orderSide.contains("sell")) {
                return OrderSide.SELL;
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
        return this.stopPrice;
    }

    @Override
    public BigDecimal getFinishedVolume() {
        return this.finishedVolume;
    }

    @Override
    public BigDecimal getFinishedAmount() {
        return this.finishedAmount;
    }

    @Override
    public BigDecimal getFinishedFees() {
        return this.finishedFees;
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
    public void setRebate(BigDecimal rabate) {
        this.rebate = rabate;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        if (StringUtils.hasText(super.getOrderCreatedAt())) {
            return DateUtils.getEpochMilliByTime(Long.parseLong(super.getOrderCreatedAt()));
        }
        return null;
    }

    @Override
    public LocalDateTime getFinishedAtAt() {
        return this.finishedAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    @Override
    public void update(OrderVo otherOrderVo) {
        if (Objects.isNull(otherOrderVo)) {
            return;
        }
        this.finishedAmount = otherOrderVo.getFinishedAmount();
        this.finishedFees = otherOrderVo.getFinishedFees();
        this.finishedVolume = otherOrderVo.getFinishedVolume();
        String state = otherOrderVo.getOrderState().name().toLowerCase();
        switchState(state);
        this.rebate = otherOrderVo.getRebate();
        this.rebateCoin = otherOrderVo.getRebateCoin();
    }

    private void switchState(String state) {
        switch (state) {
            case "created":
                super.setState("submitted");
                break;
            case "partial_filled":
                super.setState("partial-filled");
                break;
            case "cancelled":
                super.setState("canceled");
                break;
            case "filled":
                super.setState("filled");
                break;
            default:
                log.warn("暂不支持的订单状态转换,OrderState:{}", state);
                break;
        }
    }

    @Override
    public String toString() {
        return "HuobiOrderVo{" +
                "orderType='" + orderType + '\'' +
                ", finishedVolume=" + finishedVolume +
                ", updatedAt=" + updatedAt +
                ", finishedAt=" + finishedAt +
                ", finishedFees=" + finishedFees +
                ", finishedAmount=" + finishedAmount +
                ", stopPrice=" + stopPrice +
                ", exchangeOrderId='" + exchangeOrderId + '\'' +
                ", rebate=" + rebate +
                ", rebateCoin='" + rebateCoin + '\'' +
                '}';
    }
}
