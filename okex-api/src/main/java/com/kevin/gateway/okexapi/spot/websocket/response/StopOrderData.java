package com.kevin.gateway.okexapi.spot.websocket.response;

import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderType;
import com.kevin.gateway.okexapi.spot.model.SpotAlgoTriggerAndOrderSide;
import com.kevin.gateway.okexapi.spot.model.SpotTriggerOrderType;
import lombok.Data;

import java.math.BigDecimal;

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
    private SpotAlgoOrderType orderType = SpotAlgoOrderType.STOP_ORDER;
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
    private SpotTriggerOrderType tpTriggerType;
    /**
     * 1:限价 2:市场价；止损触发价格类型，默认是限价；为市场价时，委托价格不必填；
     */
    private SpotTriggerOrderType slTriggerType;
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
//    private OkexSpotAlgoTriggerAndOrderSide triggerSide;
    private String triggerSide;
    /**
     * 下单委托方向：1:止盈 2:止损, 3双向
     */
    private SpotAlgoTriggerAndOrderSide orderSide;

}
