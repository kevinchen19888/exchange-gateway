package com.kevin.gateway.okexapi.future.websocket.response;

import com.kevin.gateway.okexapi.base.util.AlgoOrderType;
import com.kevin.gateway.okexapi.future.type.TriggerOrderSide;
import com.kevin.gateway.okexapi.future.type.TriggerOrderType;
import com.kevin.gateway.okexapi.future.type.TriggerSide;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 止盈止损
 */
@Data
public class StopOrderData extends OrderAlgoData {
    /**
     * 1：计划委托
     * 2：跟踪委托
     * 3：冰山委托
     * 4：时间加权
     * 5：止盈止损
     */
    private AlgoOrderType orderType = AlgoOrderType.STOP;
    /**
     * 止盈触发价格，填写值0\<X\<=1000000
     */
    private BigDecimal tpTriggerPrice;
    /**
     * 止盈委托价格，填写值0\<X\<=1000000
     */
    private BigDecimal tpPrice;
    /**
     * 1:限价 2:市场价；止盈触发价格类型，默认是限价；为市场价时，委托价格不必填；
     */
    private TriggerOrderType tpTriggerType;
    /**
     * 1:限价 2:市场价；止损触发价格类型，默认是限价；为市场价时，委托价格不必填；
     */
    private TriggerOrderType slTriggerType;
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
    private TriggerOrderSide orderSide;
    /**
     * 最新成交价
     */
    private BigDecimal lastFillPx;
    /**
     * 订单创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 挂单冻结保证金
     */
    private BigDecimal marginForUnfilled;
    /**
     * 合约面值
     */
    private BigDecimal contractVal;
    /**
     * 实际委托价格
     */
    private BigDecimal realPrice;
    /**
     * 实际委托数量
     */
    private BigDecimal realAmount;

}
