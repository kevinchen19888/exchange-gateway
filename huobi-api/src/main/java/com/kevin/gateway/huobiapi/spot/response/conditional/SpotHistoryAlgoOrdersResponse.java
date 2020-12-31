package com.kevin.gateway.huobiapi.spot.response.conditional;

import com.kevin.gateway.huobiapi.base.util.OrderSide;
import com.kevin.gateway.huobiapi.spot.SpotMarketId;
import com.kevin.gateway.huobiapi.spot.model.AlgoOrderState;
import com.kevin.gateway.huobiapi.spot.model.SpotOrderSource;
import com.kevin.gateway.huobiapi.spot.model.SpotTimeInForce;
import com.kevin.gateway.huobiapi.spot.model.SpotType;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 查询策略委托历史
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotHistoryAlgoOrdersResponse extends SpotBaseResponse {
    private List<SpotHistoryAlgoOrders> data;

    @Data
    public static class SpotHistoryAlgoOrders {
        private String accountId;//账户编号
        private SpotOrderSource source;//订单来源（api,web,ios,android,mac,windows,sys）
        private String clientOrderId;    //用户自编订单号
        private String orderId;    //订单编号（仅对orderStatus=triggered有效）
        private SpotMarketId symbol;//交易代码
        private BigDecimal orderPrice;    //	订单价格（对市价单无效）
        private BigDecimal orderSize;//	订单数量（对市价买单无效）
        private BigDecimal orderValue;//	订单金额（仅对市价买单有效）
        private OrderSide orderSide;//	订单方向
        private SpotTimeInForce timeInForce;    //订单有效期
        private SpotType orderType;    //	订单类型
        private BigDecimal stopPrice;    //	触发价
        private BigDecimal trailingRate;    //回调幅度（仅对追踪委托有效）
        private long orderOrigTime;    //订单创建时间
        private long lastActTime;    //	订单最近更新时间
        private long orderCreateTime;//订单触发时间（仅对orderStatus=triggered有效）
        private AlgoOrderState orderStatus; //订单状态（triggered,canceled,rejected）
        private Integer errCode;    //订单被拒状态码（仅对orderStatus=rejected有效）
        private String errMessage;    //订单被拒错误消息（仅对orderStatus=rejected有效）
    }
}
