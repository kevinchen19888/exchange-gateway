package com.alchemy.gateway.core.order;

import java.util.List;

public interface OrderStatusCallback {

    /**
     * 状态改变时回调
     *
     * @param orderVo      变动后的订单内容
     */
    void stateChanged(OrderVo orderVo);

    /**
     * 新增成交记录
     * @param tradeVos 成交记录列表
     * @param mineOrderId 平台订单id
     */
    void insertTrade(List<TradeVo> tradeVos, Long mineOrderId);
}
