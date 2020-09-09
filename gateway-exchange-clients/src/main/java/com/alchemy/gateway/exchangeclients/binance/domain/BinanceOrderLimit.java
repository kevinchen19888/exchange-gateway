package com.alchemy.gateway.exchangeclients.binance.domain;

import com.alchemy.gateway.core.common.OrderLimit;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author kevin chen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BinanceOrderLimit extends OrderLimit {
    private BigDecimal minOrderPrice;// 最小下单价格
    private BigDecimal maxOrderPrice;
    private BigDecimal maxOrderQty;
    private BigDecimal minOrderVal;
    private BigDecimal marketMinOrderQty;// 市价最小订单数量
    private BigDecimal marketMaxOrderQty;// 市价最大订单数量
    private Integer maxNumOrders; // 客户在交易对上最大挂单量
    /**
     * 最多条件单数(过滤器定义允许账户在交易对上开设的"algo"订单的最大数量,
     * "Algo"订单是STOP_LOSS，STOP_LOSS_LIMIT，TAKE_PROFIT和TAKE_PROFIT_LIMIT止盈止损单。)
     */
    private Integer maxNumAlgoOrders;
}
