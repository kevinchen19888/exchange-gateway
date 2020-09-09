package com.alchemy.gateway.broker.entity;

import com.alchemy.gateway.broker.entity.type.converter.OrderSideConverter;
import com.alchemy.gateway.broker.entity.type.converter.OrderStateConverter;
import com.alchemy.gateway.broker.entity.type.converter.OrderTypeConverter;
import com.alchemy.gateway.core.order.OrderSide;
import com.alchemy.gateway.core.order.OrderState;
import com.alchemy.gateway.core.order.OrderType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单表
 */
@Entity
@Data
@Table(name = "entrust_order")
public class Order {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Long id;

    /**
     * 实盘平台的订单 ID
     */
    @Column(name = "mine_order_id", nullable = false)
    private Long mineOrderId;

    /**
     * 币对
     */
    @Column(name = "symbol", nullable = false)
    private String symbol;

    /**
     * 订单类型,  0: 市价, 1: 限价, 2: 止损限价
     */
    @Column(name = "type", nullable = false)
    @Convert(converter = OrderTypeConverter.class)
    private OrderType type;

    /**
     * 价格
     */
    @Column(name = "price")
    private BigDecimal price;

    /**
     * 数量或者金额: 市价单时为市价单币种的交易额, 限价单时为数量
     */
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    /**
     * 交易方向, 0: 买, 1:卖
     */
    @Column(name = "side", nullable = false)
    @Convert(converter = OrderSideConverter.class)
    private OrderSide side;

    /**
     * 订单状态, 0: 提交中, 1: 提交失败(网络超时,key无效,余额不足), 2: 已撤销, 3: 完成, 4: 已创建订单, 5: 部分成交, 6: 撤销中
     */
    @Column(name = "state", nullable = false)
    @Convert(converter = OrderStateConverter.class)
    private OrderState state;

    /**
     * 止损价格（仅在止损订单有效，非止损订单时为 NULL）
     */
    @Column(name = "stop_price")
    private BigDecimal stopPrice;

    /**
     * 交易所内订单 id（交易所接口提交失败时为 NULL）
     */
    @Column(name = "exchange_order_id")
    private String exchangeOrderId;

    /**
     * 已完成金额
     */
    @Column(name = "finished_amount", nullable = false)
    private BigDecimal finishedAmount = BigDecimal.ZERO;

    /**
     * 已完成手续费
     */
    @Column(name = "finished_fee", nullable = false)
    private BigDecimal finishedFee = BigDecimal.ZERO;

    /**
     * 已完成成交量
     */
    @Column(name = "finished_volume", nullable = false)
    private BigDecimal finishedVolume = BigDecimal.ZERO;

    /**
     * 返佣费用币种（没有返佣时为 NULL）
     */
    @Column(name = "rebate_coin")
    private String rebateCoin;

    /**
     * 返佣金额
     */
    @Column(name = "rebate")
    private BigDecimal rebate;

    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * 完成时间
     */
    @Column(name = "finished_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime finishedAt;

    /**
     * 指向账户表的外键 account.id
     */
    @Column(name = "account_id", nullable = false)
    private Long accountId;

}