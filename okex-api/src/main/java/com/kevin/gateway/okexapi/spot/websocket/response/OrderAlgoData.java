
package com.kevin.gateway.okexapi.spot.websocket.response;

import com.kevin.gateway.okexapi.base.util.OrderSide;
import com.kevin.gateway.okexapi.spot.SpotMarketId;
import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderMode;
import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderStatus;
import com.kevin.gateway.okexapi.spot.model.SpotCancelCode;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户委托策略
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,property = "order_type")
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

    private String orderId;
    /**
     * 1、币币 2、杠杆
     */
    private SpotAlgoOrderMode mode;
    /**
     * 委托数量
     */
    private BigDecimal size;
    /**
     * 币对名称
     */
    private SpotMarketId instrumentId;
    /**
     * buy或者sell
     */
    private OrderSide side;
    /**
     * 委托时间
     */
    private LocalDateTime timestamp;
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
    private SpotAlgoOrderStatus status;
    /**
     * 0:委托超时撤单 1:用户主动撤单 2:余额不足撤单
     */
    private SpotCancelCode cancelCode;

    private LocalDateTime createdAt;

}