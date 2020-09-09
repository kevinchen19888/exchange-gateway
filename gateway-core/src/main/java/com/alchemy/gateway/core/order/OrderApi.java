package com.alchemy.gateway.core.order;

import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.common.Credentials;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.wallet.CursorVo;

import java.util.List;

/**
 * 对接交易所的 restful 接口
 */
public interface OrderApi {

    /**
     * 交易所下单
     *
     * @param credentials 账户凭据
     * @param request     订单请求
     * @return 订单对象
     */
    OrderVo placeOrder(Credentials credentials, OrderRequest request);

    /**
     * 获取订单详情
     *
     * @param credentials 账户凭据
     * @param orderId     订单 ID
     * @return 订单对象
     */
    OrderVo getOrder(Credentials credentials, String orderId, CoinPair coinPair, OrderType type);

    /**
     * 交易所取消订单
     *
     * @param credentials 账户凭据
     * @param orderId     订单请求
     */
    boolean cancelOrder(Credentials credentials, String orderId, CoinPair coinPair, OrderType type);

    /**
     * 获取订单所有成交记录
     *
     * @param credentials 账户凭据
     * @param orderVo     订单信息
     * @return TradesResult
     */
    TradesResult getTrades(Credentials credentials, OrderVo orderVo);


    /**
     * 查询交易所历史订单
     *
     * @param credentials 凭证
     * @param cursorVo    游标
     * @param market      市场
     * @return HistoryOrderResult
     */
    HistoryOrderResult getHistoryOrder(Credentials credentials, CursorVo cursorVo, Market market);

    /**
     * 校验下单是否合法
     * @param request 订单请求数据
     * @return boolean
     */
    boolean checkOrderDefined(OrderRequest request);

    /**
     * 初始化订单限制信息
     * @param markets 市场
     */
    void initOrderLimit(List<Market> markets);
}
