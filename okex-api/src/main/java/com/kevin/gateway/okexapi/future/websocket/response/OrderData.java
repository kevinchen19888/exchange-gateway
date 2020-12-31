package com.kevin.gateway.okexapi.future.websocket.response;
import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.type.LastAmendResult;
import com.kevin.gateway.okexapi.future.type.OpenCloseLongShortType;
import com.kevin.gateway.okexapi.future.type.OrderStateType;
import com.kevin.gateway.okexapi.future.type.OrderType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户交易数据
 */
@Data
public class OrderData {

    /**
     * 杠杆倍数
     */
    private int leverage;
    /**
     * 最新成交时间（如果没有，推1970-01-01T00:00:00.000Z）
     */
    private LocalDateTime lastFillTime;
    /**
     * 手续费
     */
    private BigDecimal fee;

    private BigDecimal slTriggerPrice;
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
     * 成交数量
     */
    private BigDecimal lastFillQty;
    /**
     * 相应修改操作的结果，“-1”： 失败，“0”： 成功，"1"：自动撤单（因为修改导致的订单自动撤销）1.如果API用户的订单cancel_on_fail设置为0或者是web/APP撤单，则修改失败后，last_amend_result返回 “-1” 2.如果API用户的订单cancel_on_fail设置为1，则修改失败后，last_amend_result返回“ 1”
     */
    private LastAmendResult lastAmendResult;
    /**
     * 委托价格
     */
    private BigDecimal price;
    private String eventCode;
    /**
     * 最新成交id（如果没有，推0），和trade频道推送的trade_id一致
     */
    private String lastFillId;
    /**
     * -1:撤单成功
     * 0:等待成交
     * 1:部分成交
     * 2:完全成交
     * 3:下单中
     * 4:撤单中
     */
    private OrderStateType state;
    /**
     * 合约面值
     */
    private BigDecimal contractVal;
    /**
     * 相应修改操作的request_id（如没有修改，推""）
     */
    private String lastRequestId;
    /**
     * 0：普通委托
     * 1：只做Maker（Post only）
     * 2：全部成交或立即取消（FOK）
     * 3：立即成交并取消剩余（IOC）
     * 4：市价委托
     */
    private OrderType orderType;
    /**
     * 订单状态变化时间
     */
    private LocalDateTime timestamp;
    private String tpTriggerPrice;
    private String filledQty;
    private String slPrice;
    private String clientOid;
    /**
     * 合约ID，如BTC-USDT-180213,BTC-USDT-191227
     */
    private FutureMarketId instrumentId;
    /**
     * 最新成交价格（如果没有，推0）
     */
    private BigDecimal lastFillPx;
    /**
     * 收益
     */
    private BigDecimal pnl;
    /**
     * 委托数量
     */
    private BigDecimal size;
    private String tpPrice;
    /**
     * 事件代码，（默认为0）
     */
    private String errorCode;
    /**
     * 事件信息，（默认为""）
     */
    private String eventMessage;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * -1:撤单成功
     * 0:等待成交
     * 1:部分成交
     * 2:完全成交
     * 3:下单中
     * 4:撤单中
     */
    private OrderStateType status;

}