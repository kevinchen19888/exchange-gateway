package com.kevin.gateway.okexapi.swap.model;


import com.kevin.gateway.okexapi.future.type.DirectionType;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SwapContractPositionResponse {
    /**
     * 预估强平价
     */
    private BigDecimal liquidationPrice;

    /**
     * 持仓数量
     */
    private BigDecimal position;


    /**
     * 可平数量
     */
    private BigDecimal availPosition;

    /**
     * 保证金
     */
    private BigDecimal margin;

    /**
     * 开仓平均价
     */
    private BigDecimal avgCost;

    /**
     * 结算基准价
     */
    private BigDecimal settlementPrice;

    /**
     * 杠杆
     */
    private float leverage;

    /**
     * 合约名称
     */
    private SwapMarketId instrumentId;

    /**
     * 已实现盈亏
     */
    private BigDecimal realizedPnl;

    /**
     * 方向  long  short
     */
    private DirectionType side;

    /**
     * 创建时间
     */
    private LocalDateTime timestamp;

    /**
     * \
     * 维持保证金率
     */
    private BigDecimal maintMarginRatio;

    /**
     * 已结算收益
     */
    private BigDecimal settledPnl;

    /**
     * 最新成交价
     */
    private BigDecimal last;

    /**
     * 未实现盈亏
     */
    private BigDecimal unrealizedPnl;
}
