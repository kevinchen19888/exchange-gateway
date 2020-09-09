package com.alchemy.gateway.exchangeclients.huobi.domain;

import com.alchemy.gateway.core.common.OrderLimit;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author kevin chen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HuobiOrderLimit extends OrderLimit {
    private BigDecimal maxOrderQty;// TODO: 2020/8/19 即将废弃待确认
    private BigDecimal minOrderVal;// 交易对限价单和市价买单最小下单金额
    private BigDecimal maxOrderVal;
    private BigDecimal minLimitOrderQty;// 交易对限价单最小下单量 ，以基础币种为单位
    private BigDecimal maxLimitOrderQty;// 交易对限价单最大下单量 ，以基础币种为单位
    private BigDecimal maxSellMarketOrderQty;// 交易对市价卖单最大下单量，以基础币种为单位
    private BigDecimal maxBuyMarketOrderVal;// 交易对市价买单最大下单金额，以计价币种为单位
    private BigDecimal minSellMarketOrderQty;// 交易对市价卖单最小下单量，以基础币种为单位
    private int pricePrecision; // 价格精度限制
    private int amountPrecision;// 数量精度限制

}
