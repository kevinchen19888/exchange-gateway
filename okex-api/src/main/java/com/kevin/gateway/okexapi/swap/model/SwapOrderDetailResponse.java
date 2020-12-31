package com.kevin.gateway.okexapi.swap.model;

import com.kevin.gateway.okexapi.future.type.OpenCloseLongShortType;
import com.kevin.gateway.okexapi.future.type.OrderStateType;
import com.kevin.gateway.okexapi.future.type.OrderType;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 获取订单信息
 */
@Data
public class SwapOrderDetailResponse {

    /**
     * 合约ID，如BTC-USD-SWAP
     */
    private SwapMarketId instrumentId;

    /**
     * 由您设置的订单ID来识别您的订单
     */
    private String clientOid;

    /**
     * 委托数量
     */
    private BigDecimal size;

    /**
     * 委托时间
     */
    private LocalDateTime timestamp;

    /**
     * 成交数量
     */
    private BigDecimal filledQty;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 委托价格
     */
    private BigDecimal price;

    /**
     * 成交均价
     */
    private BigDecimal priceAvg;

    /**
     * 订单类型
     * 1:开多
     * 2:开空
     * 3:平多
     * 4:平空
     */
    private OpenCloseLongShortType type;

    /**
     * 合约面值
     */
    private BigDecimal contractVal;

    /**
     * 杠杆倍数，1-100的数值
     */
    private float leverage;

    /**
     * 0：普通委托
     * 1：只做Maker（Post only）
     * 2：全部成交或立即取消（FOK）
     * 3：立即成交并取消剩余（IOC）
     * 4：市价委托
     */
    private OrderType orderType;

    /**
     * 收益
     */
    private BigDecimal pnl;

    /**
     * 订单状态
     * -2：失败
     * -1：撤单成功
     * 0：等待成交
     * 1：部分成交
     * 2：完全成交
     * 3：下单中
     * 4：撤单中
     */
    private OrderStateType state;


    private String status;


    /**
     * 强平的真实触发价格，仅强平单会返回此字段
     */
    private BigDecimal triggerPrice;

}

