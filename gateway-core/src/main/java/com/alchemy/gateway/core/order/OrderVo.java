package com.alchemy.gateway.core.order;

import com.alchemy.gateway.core.common.Market;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface OrderVo {
    /**
     * 平台唯一委托单id
     *
     * @return 平台唯一委托单id
     */
    Long getMineOrderId();
    void setMineOrderId(Long mineOrderId);
    /**
     * 获取交易所名称
     *
     * @return 交易所名称，例如：huobi, binance...
     */
    String getExchangeName();
    void setExchangeName(String exchangeName);

    /**
     * 所在市场
     *
     * @return 市场
     */
    Market getMarket();

    /**
     * 交易所订单 ID
     *
     * @return 交易所订单 ID
     */
    String getExchangeOrderId();

    /**
     * 订单状态
     *
     * @return 订单状态
     */
    OrderState getOrderState();
    void setOrderState(String state);

    /**
     * 订单交易类型
     *
     * @return 订单交易类型
     */
    OrderType getType();

    /**
     * 订单交易方向
     *
     * @return 订单交易方向
     */
    OrderSide getSide();

    /**
     * 价格
     *
     * @return 价格
     */
    BigDecimal getPrice();

    /**
     * 数量
     *
     * @return 数量
     */
    BigDecimal getAmount();

    /**
     * 触发价格
     *
     * @return 触发价格
     */
    BigDecimal getStopPrice();

    /**
     * 已成交数量
     *
     * @return 已成交数量
     */
    BigDecimal getFinishedVolume();
    void setFinishedVolume(BigDecimal finishedVolume);

    /**
     * 已成交总金额
     *
     * @return 已成交总金额
     */
    BigDecimal getFinishedAmount();
    void setFinishedAmount(BigDecimal finishedAmount);

    /**
     * 已成交手续费
     *
     * @return 已成交手续费
     */
    BigDecimal getFinishedFees();
    void setFinishedFees(BigDecimal finishedFees);

    /**
     * 返佣费用币种（没有返佣时为 NULL）
     */
    String getRebateCoin();
    void setRebateCoin(String rebateCoin);

    /**
     * 返佣金额
     */
    BigDecimal getRebate();
    void setRebate(BigDecimal rabate);

    /**
     * 创建时间
     *
     * @return 创建时间
     */
    LocalDateTime getCreatedAt();

    /**
     * 完成时间
     *
     * @return 完成时间
     */
    LocalDateTime getFinishedAtAt();

    /**
     * 修改时间
     *
     * @return 修改时间
     */
    LocalDateTime getUpdatedAt();

    /**
     * 使用其它的订单信息更新自身的订单信息
     *
     * @param otherOrderVo 其它订单
     */
    void update(OrderVo otherOrderVo);

}
