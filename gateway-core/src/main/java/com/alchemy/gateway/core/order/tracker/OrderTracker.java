package com.alchemy.gateway.core.order.tracker;

import com.alchemy.gateway.core.common.Credentials;
import com.alchemy.gateway.core.order.OrderVo;
import com.alchemy.gateway.core.order.OrderStatusCallback;

import java.util.List;

/**
 * 订单跟踪器接口
 */
public interface OrderTracker {
    /**
     * 跟踪订单，如果订单有变化，则通知回调
     *
     * @param orderVo    待跟踪订单
     * @param callback 状态回调
     */
    TrackingOrder trackOrder(Credentials credentials, OrderVo orderVo, OrderStatusCallback callback);

    /**
     * 停止订单跟踪
     *
     * @param orderId 订单ID
     * @return true 如果以前跟踪了此订单对象，否则返回 false
     */
    boolean untrackOrder(String orderId);

    /**
     * 获取所有跟踪中的订单对象
     *
     * @return 跟踪中的订单对象
     */
    List<TrackingOrder> getTracingOrders();
}
