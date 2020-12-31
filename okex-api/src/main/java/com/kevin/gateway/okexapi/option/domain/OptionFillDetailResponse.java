package com.kevin.gateway.okexapi.option.domain;

import com.kevin.gateway.okexapi.base.util.OrderSide;
import com.kevin.gateway.okexapi.option.OptionMarketId;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 获取成交明细 vo
 */
@Data
public class OptionFillDetailResponse {
    /**
     * 成交ID，与last_fill_id保持一致，为撮合id
     */
    private String tradeId;
    /**
     * 账单ID，与账单流水里的ledger_id保持一致
     */
    private String ledgerId;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 合约名称，如BTC-USD-190927-12500-C
     */
    private OptionMarketId instrumentId;
    /**
     * 成交价格
     */
    private BigDecimal fillPrice;
    /**
     * 成交数量
     */
    private BigDecimal fillQty;
    /**
     * 成交时间, ISO 8601格式
     */
    private LocalDateTime timestamp;
    /**
     * 成交方向(buy/sell)
     */
    private OrderSide side;
    /**
     * 流动性方向，T：taker M：maker
     */
    private OptionExecType execType;
    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 期权合约流动性方向
     */
    public enum OptionExecType {
        TAKER("taker"),
        MAKER("maker");

        OptionExecType(String value) {
            this.val = value;
        }

        private final String val;

        @JsonValue
        public String getVal() {
            return this.val;
        }

        @JsonCreator
        public static OptionExecType fromVal(String value) {
            for (OptionExecType execType : OptionExecType.values()) {
                if (execType.val.equals(value)) {
                    return execType;
                }
            }
            throw new IllegalArgumentException("无效订单交易类型,s:" + value);
        }
    }
}
