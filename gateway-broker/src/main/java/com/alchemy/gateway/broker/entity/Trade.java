package com.alchemy.gateway.broker.entity;

import com.alchemy.gateway.core.order.RoleType;
import com.alchemy.gateway.broker.entity.type.converter.RoleTypeConverter;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 成交记录表
 */
@Data
@Entity
@Table(name = "trade")
public class Trade {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Long id;

    /**
     * 交易所订单 ID
     */
    @Column(name = "exchange_order_id", nullable = false)
    private String exchangeOrderId;

    /**
     * 交易所名称, 比如: binance, okex
     */
    @Column(name = "exchange_name", nullable = false)
    private String exchangeName;

    /**
     * 交易所成交记录 ID
     */
    @Column(name = "exchange_trade_id", nullable = false)
    private String exchangeTradeId;

    /**
     * 成交额
     */
    @Column(name = "filled_amount", nullable = false)
    private BigDecimal filledAmount;

    /**
     * 本笔成交手续费
     */
    @Column(name = "filled_fee", nullable = false)
    private BigDecimal filledFee = BigDecimal.ZERO;

    /**
     * 手续费用币种（没有手续费时为 NULL）
     */
    @Column(name = "filled_fee_coin")
    private String filledFeeCoin;

    /**
     * 成交价格
     */
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    /**
     * 成交角色（0: maker, 1: taker）
     */
    @Column(name = "role", nullable = false)
    @Convert(converter = RoleTypeConverter.class)
    private RoleType role;

    /**
     * 抵扣费用币种（没有手续费时为 NULL）
     */
    @Column(name = "fee_deduct_coin")
    private String feeDeductCoin;

    /**
     * 抵扣金额（抵扣币种金额、抵扣点卡等）
     */
    @Column(name = "fee_deduct_amount")
    private BigDecimal feeDeductAmount;

    /**
     * 交易所交易记录创建时间
     */
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * 外键, 指向 order.id
     */
    @Column(name = "order_id", nullable = false)
    private Long orderId;
}