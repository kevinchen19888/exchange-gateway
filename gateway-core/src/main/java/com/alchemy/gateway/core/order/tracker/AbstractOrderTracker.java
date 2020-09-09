package com.alchemy.gateway.core.order.tracker;

import com.alchemy.gateway.core.common.Credentials;
import com.alchemy.gateway.core.order.OrderStatusCallback;
import com.alchemy.gateway.core.order.OrderVo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 订单跟踪器(抽象基类)
 * <p>
 * 不同交易所订单的跟踪方式不同，此处有两种跟踪器：基于 WebSocket 和基于线程的跟踪器
 */
public abstract class AbstractOrderTracker implements OrderTracker {
    protected final List<TrackingOrder> trackingOrders = new CopyOnWriteArrayList<>(); // 跟踪中的订单


    @Override
    public TrackingOrder trackOrder(Credentials credentials, OrderVo orderVo, OrderStatusCallback callback) {
        TrackingOrder result = TrackingOrder.of(orderVo, credentials, callback);
        this.trackingOrders.add(result);
        return result;
    }

    @Override
    public boolean untrackOrder(String orderId) {
        return trackingOrders.removeIf(trackingOrder -> trackingOrder.getOrderVo().getExchangeOrderId().equals(orderId));
    }

    @Override
    public List<TrackingOrder> getTracingOrders() {
        return trackingOrders;
    }
}
