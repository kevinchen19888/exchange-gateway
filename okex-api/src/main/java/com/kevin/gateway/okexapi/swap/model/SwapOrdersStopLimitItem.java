package com.kevin.gateway.okexapi.swap.model;

import com.kevin.gateway.okexapi.future.model.OrdersBaseItem;
import com.kevin.gateway.okexapi.future.type.OrderSideType;
import com.kevin.gateway.okexapi.future.type.TriggerSide;
import com.kevin.gateway.okexapi.future.type.TriggerType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SwapOrdersStopLimitItem extends OrdersBaseItem {

    /**
     * 止盈触发价格，填写值0\<X\<=1000000
     */
    private BigDecimal tpTriggerPrice;

    /**
     * 止盈委托价格，填写值0\<X\<=1000000
     */
    private BigDecimal tpPrice;

    /**
     * 1:限价 2:市场价；止盈触发价格类型，默认是限价；为市场价时，委托价格不必填
     */
    private TriggerType tpTriggerType;

    /**
     * 1:限价 2:市场价；止损触发价格类型，默认是限价；为市场价时，委托价格不必填；
     */
    private TriggerType slTriggerType;

    /**
     * 止损触发价格，填写值0\<X\<=1000000
     */
    private BigDecimal slTriggerPrice;

    /**
     * 止损委托价格，填写值0\<X\<=1000000
     */
    private BigDecimal slPrice;

    /**
     * 触发方向：1:止盈 2:止损,
     */
    private TriggerSide triggerSide;

    /**
     * 下单委托方向：1:止盈 2:止损, 3双向
     */
    private OrderSideType orderSide;

}

