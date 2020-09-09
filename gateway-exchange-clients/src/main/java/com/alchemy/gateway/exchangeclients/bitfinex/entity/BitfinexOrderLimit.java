package com.alchemy.gateway.exchangeclients.bitfinex.entity;

import com.alchemy.gateway.core.common.OrderLimit;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;




@EqualsAndHashCode(callSuper = true)
@Data
public class BitfinexOrderLimit extends OrderLimit {

    /**
     *   最小下单价格
     */

    private BigDecimal minOrderPrice;
    private BigDecimal maxOrderPrice;
    private BigDecimal maxOrderQty;
    private BigDecimal minOrderVal;

    /**
     * 市价最小订单数量
     */
    private BigDecimal marketMinOrderQty;

    /**
     * 市价最大订单数量
     */
    private BigDecimal marketMaxOrderQty;

    /**
     *  客户在交易对上最大挂单量
     */
    private Integer maxNumOrders;
    /**
     * 最多条件单数(过滤器定义允许账户在交易对上开设的"algo"订单的最大数量,
     * "Algo"订单是STOP_LOSS，STOP_LOSS_LIMIT，TAKE_PROFIT和TAKE_PROFIT_LIMIT止盈止损单。)
     */
    private Integer maxNumAlgoOrders;
}
