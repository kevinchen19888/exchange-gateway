package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.type.OpenCloseLongShortType;
import com.kevin.gateway.okexapi.future.type.OrderStatusType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "orderType")// name属性指定了json序列化&反序列化时多态的判断
@JsonSubTypes({
        @JsonSubTypes.Type(value = OrdersPreEntrustItem.class, name = "1"),
        @JsonSubTypes.Type(value = OrdersTraceEntrustItem.class, name = "2"),
        @JsonSubTypes.Type(value = OrdersIceHillEntrustItem.class, name = "3"),
        @JsonSubTypes.Type(value = OrdersTimeWeightItem.class, name = "4"),
        @JsonSubTypes.Type(value = OrdersStopLimitItem.class, name = "5")

})
public class OrdersBaseItem {

    /**
     * 合约ID，如BTC-USD-190328
     */
    private FutureMarketId instrumentId;

    /**
     * 1：计划委托
     * 2：跟踪委托
     * 3：冰山委托
     * 4：时间加权
     * 5：止盈止损
     * 根据这个类型 后面有几个派生类型
     */
//    private String orderType ;

    /**
     * 订单状态变化时间
     */
    private LocalDateTime timestamp;

    /**
     * 委托单ID
     */
    private String algoIds;

    /**
     * 订单状态
     * 1: 待生效
     * 2: 已生效
     * 3: 已撤销
     * 4: 部分生效
     * 5: 暂停生效
     */
    private OrderStatusType status;

    /**
     * 订单类型
     * 1:开多
     * 2:开空
     * 3:平多
     * 4:平空
     */
    private OpenCloseLongShortType type;

    /**
     * 杠杆倍数
     */
    private int leverage;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 委托数量（以张计数）,填写值1\<=X\<=1000000的整数
     */
    private int size;

}

