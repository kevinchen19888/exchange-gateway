package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.type.OpenCloseLongShortType;

import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderType;
import com.kevin.gateway.okexapi.spot.model.SpotPlaceAlgoType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FuturePlacePolicyOrderRequest {

    /**
     * 合约ID
     */
    private FutureMarketId instrumentId;


    /**
     * 1:开多
     * 2:开空
     * 3:平多
     * 4:平空
     */
    private OpenCloseLongShortType type;


    /**
     * 1：止盈止损
     * 2：跟踪委托
     * 3：冰山委托
     * 4：时间加权
     * 5：止盈止损
     */
    private SpotAlgoOrderType orderType;

    /**
     * 委托总数（以张计数），填写值1\<=X\<=1000000的整数
     */
    private BigDecimal size;


    /**
     * 计划委托： 触发价格，填写值0\<X\<=1000000
     * 跟踪委托参数 : 激活价格
     */
    private BigDecimal triggerPrice;


    /**
     * 计划委托： 委托价格，填写值0\<X\<=1000000
     */
    private BigDecimal algoPrice;


    /**
     * 计划委托： 	1:限价 2:市场价；触发价格类型，默认是限价；为市场价时，委托价格不必填
     */
    private SpotPlaceAlgoType algoType;


    /**
     * 跟踪委托参数  回调幅度，填写值0.001（0.1%）\<=X\<=0.05（5%）
     */
    private BigDecimal callbackRate;


    /**
     * 冰山委托参数  	委托深度，填写值0.0001(0.01%)\<=X\<=0.01（1%）
     */
    private BigDecimal algoVariance;

    /**
     * 冰山委托参数  单笔均值，填写2-1000的整数（永续2-500的整数）
     */
    private Integer avgAmount;

    /**
     * 冰山委托参数  	价格限制 ，填写值0\<X\<=1000000
     */
    private BigDecimal priceLimit;


}
