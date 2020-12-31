package com.kevin.gateway.okexapi.swap.websocket.response;

import com.kevin.gateway.okexapi.future.type.DirectionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 */
@Data
public class HoldingData {
    /**
     * 	可平数量
     */
    private BigDecimal availPosition;
    /**
     * 	开仓平均价
     */
    private BigDecimal avgCost;
    /**
     * 最新成交价
     */
    private BigDecimal last;
    /**
     * 杠杆
     */
    private BigDecimal leverage;
    /**
     * 预估强平价
     */
    private BigDecimal liquidationPrice;
    /**
     * 维持保证金率
     */
    private BigDecimal maintMarginRatio;
    /**
     * 保证金
     */
    private BigDecimal margin;
    /**
     * 持仓数量
     */
    private BigDecimal position;
    /**
     * 已实现盈亏
     */
    private BigDecimal realizedPnl;
    /**
     * 已结算收益
     */
    private BigDecimal settledPnl;
    /**
     * 结算基准价
     */
    private BigDecimal settlementPrice;
    /**
     * 	方向（long/short）
     */
    private DirectionType side;
    /**
     * 多仓/空仓开仓冻结张数
     */
    private int openOutstanding;
    /**
     * 创建时间
     */
    private LocalDateTime timestamp;

}
