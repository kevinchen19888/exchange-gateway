package com.kevin.gateway.okexapi.spot.request;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.base.util.OrderSide;
import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderMode;
import com.kevin.gateway.okexapi.spot.model.SpotPlaceAlgoType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 策略委托下单参数实体
 */
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "order_type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SpotPlaceAlgoOrderRequest.SpotPlaceAlgoTriggerOrderRequest.class, name = "1"),
        @JsonSubTypes.Type(value = SpotPlaceAlgoOrderRequest.SpotPlaceAlgoTrailOrderRequest.class, name = "2"),
        @JsonSubTypes.Type(value = SpotPlaceAlgoOrderRequest.SpotPlaceAlgoIcebergOrderRequest.class, name = "3"),
        @JsonSubTypes.Type(value = SpotPlaceAlgoOrderRequest.SpotPlaceAlgoTwapOrderRequest.class, name = "4"),
        @JsonSubTypes.Type(value = SpotPlaceAlgoOrderRequest.SpotPlaceAlgoFullStopOrderRequest.class, name = "5")
})
public class SpotPlaceAlgoOrderRequest {
    /**
     * 币对名称
     */
    private CoinPair instrumentId;
    /**
     * 1：币币 2：杠杆
     */
    private SpotAlgoOrderMode mode;

    //1：计划委托
    //2：跟踪委托
    //3：冰山委托
    //4：时间加权
    //5：止盈止损
   /* @JsonProperty(value = "order_type")
    private String orderType;*/

    /**
     * 委托总数，填写值1\<=X\<=1000000的整数
     */
    private BigDecimal size;
    /**
     * buy or sell
     */
    private OrderSide side;

    /**
     * 计划委托参数
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @Data
    public static class SpotPlaceAlgoTriggerOrderRequest extends SpotPlaceAlgoOrderRequest {
        /**
         * 触发价格，填写值0\<X\<=1000000
         * 跟踪委托参数:	激活价格 ，填写值0\<X\<=1000000
         */
        private BigDecimal triggerPrice;
        /**
         * 委托价格，填写值0\<X\<=1000000
         */
        private BigDecimal algoPrice;

        /**
         * 1:限价 2:市场价；触发价格类型，默认是1:限价，为2:市场价时，委托价格不必填；
         */
        private SpotPlaceAlgoType algoType;

    }

    /**
     * 跟踪委托参数
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @Data
    public static class SpotPlaceAlgoTrailOrderRequest extends SpotPlaceAlgoOrderRequest {
        /**
         * 激活价格 ，填写值0\<X\<=1000000
         */
        private BigDecimal triggerPrice;
        /**
         * 回调幅度，填写值0.001（0.1%）\<=X\<=0.05（5%）
         */
        private BigDecimal callbackRate;

    }

    /**
     * 冰山委托参数(最多同时存在6单)
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @Data
    public static class SpotPlaceAlgoIcebergOrderRequest extends SpotPlaceAlgoOrderRequest {
        /**
         * 委托深度，填写值0.0001(0.01%)\<=X\<=0.01（1%）
         */
        private BigDecimal algoVariance;
        /**
         * 单笔均值，填写值 本次委托总量的1/1000\<=X\<=本次委托总量
         */
        private BigDecimal avgAmount;
        /**
         * 价格限制 ，填写值0\<X\<=1000000
         */
        private BigDecimal limitPrice;

    }

    /**
     * 时间加权参数(最多同时存在6单)
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @Data
    public static class SpotPlaceAlgoTwapOrderRequest extends SpotPlaceAlgoOrderRequest {
        /**
         * 扫单范围，填写值0.005（0.5%）\<=X\<=0.01（1%）
         */
        private BigDecimal sweepRange;
        /**
         * 扫单比例，填写值 0.01\<=X\<=1
         */
        private BigDecimal sweepRatio;
        /**
         * 单笔上限，填写值 本次委托总量的1/1000\<=X\<=本次委托总量
         */
        private BigDecimal singleLimit;
        /**
         * 价格限制 ，填写值0\<X\<=1000000
         */
        private BigDecimal limitPrice;
        /**
         * 委托间隔，填写值5\<=X\<=120
         */
        private BigDecimal timeInterval;

    }

    /**
     * 止盈止损参数
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @Data
    public static class SpotPlaceAlgoFullStopOrderRequest extends SpotPlaceAlgoOrderRequest {
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
        private SpotPlaceAlgoType tpTriggerType;

        /**
         * 1:限价 2:市场价；止损触发价格类型，默认是限价；为市场价时，委托价格不必填；
         */
        private SpotPlaceAlgoType slTriggerType;

        /**
         * 止损触发价格，填写值0\<X\<=1000000
         */
        private BigDecimal slTriggerPrice;

        /**
         * 止损委托价格，填写值0\<X\<=1000000
         */
        private BigDecimal slPrice;

    }

}
