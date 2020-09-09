package com.alchemy.gateway.exchangeclients.okex.param;

import com.alchemy.gateway.core.order.OrderType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderAlgoParam {
    /**
     * 币对名称
     */
    @JsonProperty(value = "instrument_id")
    private String instrumentId;
    /**
     * 1：币币 2：杠杆
     */
    private String mode;
    /**
     * 1：止盈止损 2：跟踪委托 3:冰山委托 4:时间加权
     */
    @JsonProperty(value = "order_type")
    private String orderType;
    /**
     * 委托总数，填写值1\<=X\<=1000000的整数
     */
    private String size;
    /**
     * buy or sell
     */
    private String side;

    //止盈止损参数
    /**
     * 触发价格，填写值0\<X\<=1000000
     * 跟踪委托参数:	激活价格 ，填写值0\<X\<=1000000
     */
    @JsonProperty(value = "trigger_price")
    private String triggerPrice;
    /**
     * 委托价格，填写值0\<X\<=1000000
     */
    @JsonProperty(value = "algo_price")
    private String algoPrice;

    /**
     * 1:限价 2:市场价；触发价格类型，默认是1:限价，为2:市场价时，委托价格不必填；
     */
    @JsonProperty(value = "algo_type")
    private String algoType;

    public void setAlgoType(OrderType orderType) {
        if (orderType.equals(OrderType.LIMIT)) {
            algoType = "1";
        }
        if (orderType.equals(OrderType.MARKET)) {
            algoType = "2";
        }
    }

    //跟踪委托参数
    /**
     * 回调幅度，填写值0.001（0.1%）\<=X\<=0.05（5%）
     */
    @JsonProperty(value = "callback_rate")
    private String callbackRate;

    //冰山委托参数 （最多同时存在6单）
    /**
     * 委托深度，填写值0.0001(0.01%)\<=X\<=0.01（1%）
     */
    @JsonProperty(value = "algo_variance")
    private String algoVariance;
    /**
     * 单笔均值，填写值 本次委托总量的1/1000\<=X\<=本次委托总量
     */
    @JsonProperty(value = "avg_amount")
    private String avgAmount;
    /**
     * 价格限制 ，填写值0\<X\<=1000000
     */
    @JsonProperty(value = "limit_price")
    private String limitPrice;
    //时间加权参数 （最多同时存在6单）
    /**
     * 扫单范围，填写值0.005（0.5%）\<=X\<=0.01（1%）
     */
    @JsonProperty(value = "sweep_range")
    private String sweepRange;
    /**
     * 扫单比例，填写值 0.01\<=X\<=1
     */
    @JsonProperty(value = "sweep_ratio")
    private String sweepRatio;
    /**
     * 单笔上限，填写值 本次委托总量的1/1000\<=X\<=本次委托总量
     */
    @JsonProperty(value = "single_limit")
    private String singleLimit;
    /**
     * 委托间隔，填写值5\<=X\<=120
     */
    @JsonProperty(value = "time_interval")
    private String timeInterval;
    /**
     * 撤销指定的委托单ID
     */
    @JsonProperty(value = "algo_ids")
    private String[] algoIds;
}
