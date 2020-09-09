package com.alchemy.gateway.core.order.tracker;

import com.alchemy.gateway.core.ExchangeManager;
import com.alchemy.gateway.core.common.AccountInfoProvider;
import com.alchemy.gateway.core.common.Credentials;
import com.alchemy.gateway.core.order.OrderState;
import com.alchemy.gateway.core.order.OrderVo;
import com.alchemy.gateway.core.order.TradesResult;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;

@Slf4j
public class ThreadedOrderTracker extends AbstractOrderTracker implements Runnable {

    private final Thread thread;
    private final long trackingInterval; // 检查订单状态的周期，单位：毫秒
    private final AccountInfoProvider accountInfoProvider;
    private ExchangeManager exchangeManager;

    public ThreadedOrderTracker(AccountInfoProvider accountInfoProvider, ExchangeManager exchangeManager) {
        this(accountInfoProvider, exchangeManager, 1000); // 1秒
    }

    public ThreadedOrderTracker(AccountInfoProvider accountInfoProvider, ExchangeManager exchangeManager, long trackingInterval) {
        this.accountInfoProvider = accountInfoProvider;
        this.thread = new Thread(this);
        this.trackingInterval = trackingInterval;
        this.exchangeManager = exchangeManager;
    }

    public void start() {
        if (thread.isAlive()) {
            throw new IllegalStateException("请先停止订单跟踪");
        }
        thread.start();
    }

    public void stop() throws InterruptedException {
        thread.interrupt();
        thread.join();
    }


    private OrderVo updateOrder(OrderVo orderVo, Credentials credentials) {
        return exchangeManager.getAPI(orderVo.getExchangeName()).getOrderApi().getOrder(credentials, String.valueOf(orderVo.getExchangeOrderId()), orderVo.getMarket().getCoinPair(), orderVo.getType());
    }

    private boolean orderEquals(OrderVo oldOrderVo, OrderVo newOrderVo) {
        if (oldOrderVo.getFinishedAmount().compareTo(newOrderVo.getFinishedAmount()) != 0
                || oldOrderVo.getOrderState() != newOrderVo.getOrderState()) {
            return false;
        }
        return true;
    }

    private TradesResult getTrades(OrderVo orderVo, Credentials credentials) {
        return exchangeManager.getAPI(orderVo.getExchangeName()).getOrderApi().getTrades(credentials, orderVo);
    }

    @Override
    public void run() {

        while (!Thread.interrupted()) {
            try {
                trackingOrders.forEach(trackingOrder->{
                    try {
                        log.info("查询是否成交:" + trackingOrder.toString());
                        OrderVo orderVo = trackingOrder.getOrderVo();
                        OrderVo updateOrderVo = updateOrder(orderVo, trackingOrder.getCredentials());
                        updateOrderVo.setMineOrderId(orderVo.getMineOrderId());//TODO:待优化,获取订单详情后没有平台订单id
                        if (!this.orderEquals(orderVo, updateOrderVo)) {
                            log.info("发现有成交的订单:" + trackingOrder.toString() + ";变更后订单:" + updateOrderVo);
                            TradesResult tradesResult = getTrades(updateOrderVo, trackingOrder.getCredentials());//获取成交记录

                            updateOrderVo.setRebate(tradesResult.getRebate());//修改返佣信息
                            updateOrderVo.setRebateCoin(tradesResult.getRebateCoin());

                            trackingOrder.getCallback().insertTrade(tradesResult.getTradeVos(), trackingOrder.getOrderVo().getMineOrderId());//插入成交记录

                            trackingOrder.getCallback().stateChanged(updateOrderVo);//回调

                            trackingOrder.getOrderVo().update(updateOrderVo);//修改内存中数据

                            if (trackingOrder.getOrderVo().getOrderState().getIntValue() < OrderState.CREATED.getIntValue()) {//完结状态
                                trackingOrders.remove(trackingOrder);
                            }
                        }
                    } catch (Exception e) {
                        log.error("是否有最新成交任务抛出异常:" + e.getMessage());
                    }
                });
                Thread.sleep(trackingInterval);   // 停止一段时间，避免过度消费 CPU
            } catch (InterruptedException e) {
                log.info("未成交订单定时任务异常");
            }

        }

    }
}
