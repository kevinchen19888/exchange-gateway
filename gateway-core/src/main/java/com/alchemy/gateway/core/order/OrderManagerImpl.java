package com.alchemy.gateway.core.order;

import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.common.Credentials;
import com.alchemy.gateway.core.order.tracker.OrderTracker;
import com.alchemy.gateway.core.order.tracker.TrackingOrder;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单管理器实现
 */
public class OrderManagerImpl implements OrderManager {

    private final OrderApi orderApi;
    private final OrderTracker orderTracker;

    public OrderManagerImpl(OrderApi orderApi, OrderTracker orderTracker) {
        this.orderApi = orderApi;
        this.orderTracker = orderTracker;
    }

    @Override
    public OrderVo placeOrder(Credentials credentials, OrderRequest request, OrderStatusCallback callback) {
        // 调用交易所 api 下单，如果下单成功，则将订单保存在跟踪订单的列表中
        OrderVo orderVo = orderApi.placeOrder(credentials, request);
        OrderVo order = orderApi.getOrder(credentials, orderVo.getExchangeOrderId(), request.getMarket().getCoinPair(), request.getType());//获取详情
        order.setOrderState(OrderState.CREATED.name().toLowerCase());
        order.setMineOrderId(request.getAlchemyId());
        order.setFinishedAmount(BigDecimal.ZERO);
        order.setFinishedFees(BigDecimal.ZERO);
        order.setFinishedVolume(BigDecimal.ZERO);
        order.setExchangeName(request.getExchangeName());
        orderTracker.trackOrder(credentials, order, callback);
        return order;
    }

    @Override
    public void cancelOrder(Credentials credentials, String orderId, CoinPair coinPair, OrderType type) {
        orderApi.cancelOrder(credentials, orderId, coinPair, type);
        orderTracker.untrackOrder(orderId);
    }

    @Override
    public List<TrackingOrder> getTracingOrders() {
        return orderTracker.getTracingOrders();
    }

    @Override
    public OrderVo restoreOrder(Credentials credentials, String orderId, CoinPair coinPair, OrderStatusCallback callback, OrderType type) {
        OrderVo orderVo = orderApi.getOrder(credentials, orderId, coinPair, type);
        if (!orderVo.getOrderState().isFinished()) {
            orderTracker.trackOrder(credentials, orderVo, callback);
        }
        return orderVo;
    }
}
