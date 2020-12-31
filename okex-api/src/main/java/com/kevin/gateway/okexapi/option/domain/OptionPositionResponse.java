package com.kevin.gateway.okexapi.option.domain;

import com.kevin.gateway.okexapi.option.OptionMarketId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 获取某个标的下的持仓信息 vo
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OptionPositionResponse extends OptionErrorResponse {

    private List<Holding> holding;

    @Data
    public static class Holding {
        /**
         * 合约ID，如BTC-USD-190927-12500-C
         */
        private OptionMarketId instrumentId;
        /**
         * 净持仓数量
         */
        private BigDecimal position;
        /**
         * 开仓平均价
         */
        private BigDecimal avgCost;
        /**
         * 可平仓数量
         */
        private BigDecimal availPosition;
        /**
         * 上期结算基准价
         */
        private BigDecimal settlementPrice;
        /**
         * 不含手续费的持仓收益，可跨期 (markPx - avgCost) Pos multiplier
         */
        private BigDecimal totalPnl;
        /**
         * 收益率 sideFactor * (markPx/avgCost - 1)
         */
        private BigDecimal pnlRatio;
        /**
         * 当期已实现收益
         */
        private BigDecimal realizedPnl;
        /**
         * 当期未实现收益
         */
        private BigDecimal unrealizedPnl;
        /**
         * 持仓保证金（即初始保证金），只有标准账户提供
         */
        private BigDecimal posMargin;
        /**
         * 期权市值
         */
        private BigDecimal optionValue;
        /**
         * 仓位建立时间，ISO 8601格式
         */
        private LocalDateTime createdAt;
        /**
         * 最后一次仓位变化时间，ISO 8601格式
         */
        private LocalDateTime updatedAt;
    }
}
