
package com.kevin.gateway.okexapi.swap.websocket.response;


import com.kevin.gateway.okexapi.base.util.AlgoOrderStatus;
import com.kevin.gateway.okexapi.future.type.OpenCloseLongShortType;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户委托策略
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "order_type")
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = TriggerOrderData.class, name = "1"),
        @JsonSubTypes.Type(value = TrailOrderData.class, name = "2"),
        @JsonSubTypes.Type(value = IcebergOrderData.class, name = "3"),
        @JsonSubTypes.Type(value = TwapOrderData.class, name = "4"),
        @JsonSubTypes.Type(value = StopOrderData.class, name = "5")
})
@Data
public class OrderAlgoData {
    /**
     * 委托ID
     */
    private String algoId;
    /**
     * 杠杆倍数
     */
    private BigDecimal leverage;
    /**
     * 委托数量
     */
    private BigDecimal size;
    /**
     * 币对名称
     */
    private SwapMarketId instrumentId;
    /**
     * 订单类型
     * 1:开多
     * 2:开空
     * 3:平多
     * 4:平空
     */
    private OpenCloseLongShortType type;
    /**
     * 委托时间
     */
    private String timestamp;
    /**
     * 订单状态
     * 1：待生效
     * 2：已生效
     * 3：已撤销
     * 4：部分生效
     * 5：暂停生效
     * 6：委托失败
     * 【只有冰山和加权有4、5状态】
     */
    private AlgoOrderStatus status;




}