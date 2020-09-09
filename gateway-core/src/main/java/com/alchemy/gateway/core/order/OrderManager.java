package com.alchemy.gateway.core.order;

import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.common.Credentials;
import com.alchemy.gateway.core.order.tracker.TrackingOrder;

import java.util.List;

/**
 * 订单管理器接口
 */
public interface OrderManager {
    /**
     * 下单
     *
     * @param credentials 账户凭证
     * @param request     下单请求参数
     * @param callback    下单后订单状态回调接口
     * @return 订单对象
     */
    OrderVo placeOrder(Credentials credentials, OrderRequest request, OrderStatusCallback callback);

    /**
     * 取消订单
     *
     * @param credentials 账户凭证
     * @param orderId     订单ID
     */
    void cancelOrder(Credentials credentials, String orderId, CoinPair coinPair, OrderType type);

    /**
     * 获取所有处理中的订单
     *
     * @return 订单列表
     */
    List<TrackingOrder> getTracingOrders();


    /**
     * 恢复订单（crash后恢复）
     *
     * @param credentials 账户凭证
     * @param orderId     订单ID
     * @param callback    下单后订单状态回调接口
     * @return 订单对象
     */
    OrderVo restoreOrder(Credentials credentials, String orderId, CoinPair coinPair, OrderStatusCallback callback, OrderType type);
}
