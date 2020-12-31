package com.kevin.gateway.okexapi.option.domain;

import com.kevin.gateway.okexapi.base.util.OrderSide;
import com.kevin.gateway.okexapi.option.OptionMarketId;
import com.kevin.gateway.okexapi.option.util.OptionOrderStatus;
import com.kevin.gateway.okexapi.option.util.OptionOrderType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OptionOrdersResponse extends OptionErrorResponse {
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 由您设置的订单ID来识别您的订单
     */
    private String clientOid;
    /**
     * 状态变更时间，ISO 8601格式
     */
    private LocalDateTime timestamp;
    /**
     * 合约名称，如BTC-USD-190927-12500-C
     */
    private OptionMarketId instrumentId;
    /**
     * 委托数量
     */
    private BigDecimal size;
    /**
     * 委托价格
     */
    private BigDecimal price;
    /**
     * 订单方向(buy/sell)
     */
    private OrderSide side;
    /**
     * 成交数量
     */
    private BigDecimal filledQty;
    /**
     * 成交均价（如果成交数量为0，该字段也为0）
     */
    private BigDecimal priceAvg;
    /**
     * 手续费
     */
    private BigDecimal fee;
    /**
     * 合约面值（即合约乘数）
     */
    private BigDecimal contractVal;
    /**
     * 最新成交价格（如没有成交，为0）
     */
    private BigDecimal lastFillPx;
    /**
     * 最新成交数量（如没有成交，为0）
     */
    private BigDecimal lastFillQty;
    /**
     * 0：普通委托
     */
    private OptionOrderType orderType;
    /**
     * 区分用户订单的属性/1.买入，2.卖出，11.强平卖出，12.强平买入，13.减仓卖出，14.减仓买入
     */
    private Attribute type;
    /**
     * 订单状态("-2":失败, "-1":撤单成功, "0":等待成交 , "1":部分成交, "2":完全成交, "3":下单中, "4":撤单中, "5": 修改中）
     */
    private OptionOrderStatus state;

    public enum Attribute {
        /**
         * 买入，
         */
        BUY(1),
        /**
         * 卖出，
         */
        SELL(2),
        /**
         * 强平卖出，
         */
        LIQUIDATION_SELL(11),
        /**
         * 强平买入，
         */
        LIQUIDATION_BUY(12),
        /**
         * 减仓卖出，
         */
        PARTIAL_LIQUIDATION_SELL(13),
        /**
         * 减仓买入
         */
        PARTIAL_LIQUIDATION_BUY(14);

        private final int val;

        Attribute(int val) {
            this.val = val;
        }

        @JsonValue
        public int getVal() {
            return val;
        }

        @JsonCreator
        public static Attribute fromVal(int val) {
            for (Attribute attribute : Attribute.values()) {
                if (attribute.val == val) {
                    return attribute;
                }
            }
            throw new IllegalArgumentException("无效订单的属性,s:" + val);
        }
    }

}
