package com.kevin.gateway.okexapi.future.websocket.response;

import com.kevin.gateway.okexapi.future.type.MarginMode;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户持全仓信息
 */
@Data
public class PositionCrossedData extends PositionData {
    /**
     * 账户类型
     * 全仓：crossed
     */
    private MarginMode marginMode = MarginMode.crossed;
    /**
     * 多仓数量
     */
    private BigDecimal longQty;
    /**
     * 多仓可平仓数量
     */
    private BigDecimal longAvailQty;
    /**
     * 开仓平均价
     */
    private BigDecimal longAvgCost;
    /**
     * 结算基准价
     */
    private BigDecimal longSettlementPrice;
    /**
     * 已实现盈余
     */
    private BigDecimal realisedPnl;
    /**
     * 空仓数量
     */
    private BigDecimal shortQty;
    /**
     * 	空仓可平仓数量
     */
    private BigDecimal shortAvailQty;
    /**
     * 开仓平均价
     */
    private BigDecimal shortAvgCost;
    /**
     * 结算基准价
     */
    private BigDecimal shortSettlementPrice;
    /**
     * 预估强平价
     */
    private BigDecimal liquidationPrice;
    /**
     * 合约ID，如BTC-USDT-180213
     */
//    private FutureMarketId instrumentId;
    /**
     * 杠杆倍数
     */
    private BigDecimal leverage;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    /**
     * 账户数据更新时间
     */
    private LocalDateTime timestamp;
    /**
     * 空仓保证金
     */
    private BigDecimal shortMargin;
    /**
     * 空仓收益
     */
    private BigDecimal shortPnl;
    /**
     * 空仓收益率
     */
    private BigDecimal shortPnlRatio;
    /**
     * 空仓未实现盈亏
     */
    private BigDecimal shortUnrealisedPnl;
    /**
     * 多仓保证金
     */
    private BigDecimal longMargin;
    /**
     * 多仓收益
     */
    private BigDecimal longPnl;
    /**
     * 多仓收益率
     */
    private BigDecimal longPnlRatio;
    /**
     * 多仓未实现盈亏
     */
    private BigDecimal longUnrealisedPnl;
    /**
     * 多仓开仓冻结张数
     */
    private BigDecimal longOpenOutstanding;
    /**
     * 空仓开仓冻结张数
     */
    private BigDecimal shortOpenOutstanding;
    /**
     * 多仓已结算收益
     */
    private BigDecimal longSettledPnl;
    /**
     * 空仓已结算收益
     */
    private BigDecimal shortSettledPnl;
    /**
     * 最新成交价
     */
    private BigDecimal last;

}