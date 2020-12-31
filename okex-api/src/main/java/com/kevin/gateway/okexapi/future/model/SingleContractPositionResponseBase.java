package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 所有合约持仓信息, 公共部分
 * margin_mode	String	账户类型
 * short_margin	String	空仓保证金
 * long_unrealised_pnl	String	多仓未实现盈亏
 * long_settled_pnl	String	多仓已结算收益
 * last	String	最新成交价
 * instrument_id	String	合约ID，如BTC-USD-180213,BTC-USDT-191227
 * created_at	String	创建时间
 * updated_at	String	最近一次加减仓的更新时间
 * long_qty	String	多仓数量
 * long_avail_qty	String	多仓可平仓数量
 * short_qty	String	空仓数量
 * short_avail_qty	String	空仓可平仓数量
 * long_margin	String	多仓保证金
 * long_avg_cost	String	开仓平均价
 * long_settlement_price	String	结算基准价
 * realised_pnl	String	已实现盈余
 * short_avg_cost	String	开仓平均价
 * short_settlement_price	String	结算基准价
 * long_pnl	String	多仓收益
 * short_pnl	String	空仓收益
 * short_unrealised_pnl	String	空仓未实现盈亏
 * short_settled_pnl	String	空仓已结算收益
 * short_pnl_ratio	String	空仓收益率
 * long_pnl_ratio	String	多仓收益率
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "margin_mode")// name属性指定了json序列化&反序列化时多态的判断
@JsonSubTypes({
        @JsonSubTypes.Type(value = SingleContractPositionResponseCrossed.class, name = "crossed"),
        @JsonSubTypes.Type(value = SingleContractPositionResponseFixed.class, name = "fixed")

})

@Data
public class SingleContractPositionResponseBase {


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
     * 多仓结算基准价
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
     * 空仓可平仓数量
     */
    private BigDecimal shortAvailQty;

    /**
     * 开仓平均价
     */
    private BigDecimal shortAvgCost;

    /**
     * 空仓结算基准价
     */
    private BigDecimal shortSettlementPrice;

    /**
     * 合约ID，如BTC-USD-180213
     */
    private FutureMarketId instrumentId;


    /**
     * 创建时间
     */
    private LocalDateTime createdAt;


    /**
     * 下单，撤单，清结算，成交的更新时间
     */
    private LocalDateTime updatedAt;


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
     * 空仓已结算收益
     */
    private BigDecimal shortSettledPnl;

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
     * 多仓已结算收益
     */
    private BigDecimal longSettledPnl;

    /**
     * 最新成交价
     */
    private BigDecimal last;

}

