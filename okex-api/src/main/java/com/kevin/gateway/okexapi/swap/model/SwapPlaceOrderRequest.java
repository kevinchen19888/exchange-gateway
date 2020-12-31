package com.kevin.gateway.okexapi.swap.model;

import com.kevin.gateway.okexapi.future.type.MatchPriceType;
import com.kevin.gateway.okexapi.future.type.OpenCloseLongShortType;
import com.kevin.gateway.okexapi.future.type.OrderType;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SwapPlaceOrderRequest {

    /**
     * 由您设置的订单ID来识别您的订单,格式是字母（区分大小写）+数字 或者 纯字母（区分大小写），1-32位字符 （不能重复）
     */
    private String clientOid;

    /**
     * 合约ID，如BTC-USD-SWAP
     */
    private SwapMarketId instrumentId;

    /**
     * 1:开多
     * 2:开空
     * 3:平多
     * 4:平空
     */
    private OpenCloseLongShortType type;

    /**
     * 0：普通委托（order type不填或填0都是普通委托）
     * 1：只做Maker（Post only）
     * 2：全部成交或立即取消（FOK）
     * 3：立即成交并取消剩余（IOC）
     * 4：市价委托
     */
    private OrderType orderType;

    /**
     * 委托价格
     */
    private BigDecimal price;

    /**
     * 买入或卖出合约的数量（以张计数）
     */
    private Integer size;

    /**
     * 是否以对手价下单(0:不是; 1:是)，默认为0，当取值为1时，price字段无效。当以对手价下单，order_type只能选择0（普通委托）
     */
    private MatchPriceType matchPrice;

}

