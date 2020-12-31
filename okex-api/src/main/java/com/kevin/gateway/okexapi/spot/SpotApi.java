package com.kevin.gateway.okexapi.spot;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.spot.model.*;
import com.kevin.gateway.okexapi.spot.request.*;
import com.kevin.gateway.okexapi.spot.response.*;
import com.kevin.gateway.okexapi.spot.model.*;
import com.kevin.gateway.okexapi.spot.request.*;
import com.kevin.gateway.okexapi.spot.response.*;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public interface SpotApi {

    /**
     * 币币账户信息
     * 获取币币账户资产列表(仅展示拥有资金的币对)，查询各币种的余额、冻结和可用等信息。
     * <p>
     * 限速规则：20次/2s
     *
     * @param credentials 账户信息
     * @return 账户资产信息
     */
    SpotAccountInfoResponse[] searchAccountAssets(Credentials credentials);

    /**
     * 单一币种账户信息
     * 获取币币账户单个币种的余额、冻结和可用等信息。
     * <p>
     * 限速规则：20次/2s
     *
     * @param credentials 账户信息
     * @param coin        币种信息
     * @return 账户资产信息
     */
    SpotAccountInfoResponse searchAccountAsset(Credentials credentials, Coin coin);

    /**
     * 账单流水查询
     * 列出账户资产流水。账户资产流水是指导致账户余额增加或减少的行为。流水会分页，并且按时间倒序排序和存储，最新的排在最前面。
     * 请参阅分页部分以获取第一页之后的其他记录。 本接口能查询最近3个月的数据。
     * <p>
     * 限速规则：10次/2s
     *
     * @param coin          币种
     * @param credentials   账户信息
     * @param spotApiWindow 现货窗口信息
     * @param type          账单流水类型
     * @return 账单流水信息
     */
    SpotBillsDetailsResponse[] searchBillsDetails(Credentials credentials, Coin coin, @Nullable SpotApiWindow spotApiWindow, @Nullable SpotBillsType type);

    /**
     * 下单
     * 币币交易提供限价单和市价单和高级限价单下单模式。只有当您的账户有足够的资金才能下单。
     * <p>
     * 一旦下单，您的账户资金将在订单生命周期内被冻结。被冻结的资金以及数量取决于订单指定的类型和参数。
     * <p>
     * 限速规则：100次/2s （不同币对之间限速不累计）
     *
     * @param credentials 账户信息
     * @param item        订单信息
     * @return 订单结果
     */
    SpotPlaceOrderResponse addOrder(Credentials credentials, SpotPlaceOrderRequest item);

    /**
     * 批量下单
     * 下指定币对的多个订单（每次只能下最多4个币对且每个币对可批量下10个单）
     * <p>
     * 限速规则：50次/2s （不同币对之间限速累计）
     *
     * @param credentials 账户信息
     * @param items       订单信息
     * @return 订单结果
     */
    SpotPlaceOrderMapResponse addOrderList(Credentials credentials, List<SpotPlaceOrderRequest> items);

    /**
     * 撤销指定订单
     * 撤销之前下的未完成订单。
     * <p>
     * 限速规则：100次/2s （不同币对之间限速不累计）
     *
     * @param credentials 账户信息
     * @param orderId     订单id
     * @param request     币对信息
     * @return 撤销结果
     */
    SpotPlaceOrderResponse cancelOrder(Credentials credentials, @Nullable String orderId, @Nullable String clientOid, SpotCancelOrdersRequest request);

    /**
     * 批量撤销订单
     * 撤销指定的某一种或多种币对的未完成订单（每次只能下最多4个币对且每个币对可批量下10个单）。
     * <p>
     * 限速规则：50次/2s （不同币对之间限速累计）
     *
     * @param credentials 账户信息
     * @param requestVo   撤销订单信息
     * @return 撤销结果
     */
    SpotPlaceOrderMapResponse cancelOrderList(Credentials credentials, List<SpotCancelMultipleOrdersRequest> requestVo);

    /**
     * 修改订单
     * 修改之前下的未完成订单。
     * <p>
     * 限速规则：20次/s
     *
     * @param credentials 账户信息
     * @param coinPair    币对
     * @param request     修改订单信息
     * @return 结果
     */
    SpotModifyOrderResponse modifyOrder(Credentials credentials, CoinPair coinPair, SpotModifyOrderRequest request);

    /**
     * 批量修改订单
     * 修改之前下的未完成订单，每个标的指数最多可批量修改10个单。
     * <p>
     * 限速规则：20次/2s
     *
     * @param credentials 账户信息
     * @param request     修改订单信息
     * @return 结果
     */
    SpotModifyOrderMapResponse multipleModifyOrder(Credentials credentials, List<SpotModifyMultipleOrderRequest> request);

    /**
     * 获取订单列表
     * 列出您当前的订单信息（本接口能查询最近3个月订单信息）。这个请求支持分页，并且按委托时间倒序排序和存储，最新的排在最前面。
     * <p>
     * 限速规则：10次/2s
     *
     * @param credentials   账户信息
     * @param coinPair      币对
     * @param state         状态
     * @param spotApiWindow 窗口信息
     * @return 订单列表信息
     */
    SpotOrderInfoResponse[] searchOrderInfoList(Credentials credentials, CoinPair coinPair, SpotOrderInfoState state, @Nullable SpotApiWindow spotApiWindow);

    /**
     * 获取所有未成交订单
     * 列出您当前所有的订单信息。这个请求支持分页，并且按时间倒序排序和存储，最新的排在最前面。请参阅分页部分以获取第一页之后的其他纪录。
     * <p>
     * 限速规则：20次/2s
     *
     * @param credentials   账户信息
     * @param coinPair      币对
     * @param spotApiWindow 窗口信息
     * @return 订单列表信息
     */
    SpotOrderInfoResponse[] searchOrdersPendingList(Credentials credentials, CoinPair coinPair, @Nullable SpotApiWindow spotApiWindow);

    /**
     * 获取订单信息
     * 通过订单ID获取单个订单信息。可以获取近3个月订单信息。已撤销的未成交单只保留2个小时。
     * <p>
     * 限速规则：20次/2s
     *
     * @param credentials 账户信息
     * @param coinPair    币对
     * @param orderId     订单Id
     * @param clientOid   订单识别Id
     * @return 订单信息
     */
    SpotOrderInfoResponse searchOrderInfo(Credentials credentials, CoinPair coinPair, @Nullable String orderId, @Nullable String clientOid);

    /**
     * 获取成交明细
     * 获取最近的成交明细表。这个请求支持分页，并且按成交时间倒序排序和存储，最新的排在最前面。请参阅分页部分以获取第一页之后的其他记录。 本接口能查询最近3月的数据。
     * <p>
     * 限速规则：10次/2s
     *
     * @param credentials   账户信息
     * @param coinPair      币对
     * @param orderId       订单Id
     * @param spotApiWindow 窗口信息
     * @return 成交记录明细信息
     */
    SpotTransactionDetailsResponse[] searchTransactionDetails(Credentials credentials, CoinPair coinPair, String orderId, @Nullable SpotApiWindow spotApiWindow);

    /**
     * 委托策略下单
     * <p>
     * 提供止盈止损、跟踪委托、冰山委托和时间加权委托策略
     * <p>
     * 限速规则：40次/2s
     *
     * @param credentials 账户信息
     * @param request     委托策略下单信息
     * @return 订单结果
     */
    SpotPlaceAlgoOrderResponse addEntrustOrder(Credentials credentials, SpotPlaceAlgoOrderRequest request);

    /**
     * 委托策略撤单
     * 委托策略撤单
     * <p>
     * 根据指定的algo_id撤销某个币的未完成订单，每次最多可撤6（冰山/时间）/10（计划/跟踪）个。
     * <p>
     * 限速规则：20 次/2s
     *
     * @param credentials 账户信息
     * @param request     委托策略撤单信息
     * @return 订单结果
     */
    SpotCancelAlgoOrderResponse cancelEntrustOrder(Credentials credentials, SpotCancelAlgoOrderRequest request);

    /**
     * 获取当前账户费率
     * 获取您当前账户交易等级对应的手续费费率，母账户下的子账户的费率和母账户一致。每天凌晨0点更新一次
     * <p>
     * 限速规则：20次/2s
     * <p>
     * category和coinPair仅选填写一个参数
     *
     * @param credentials 账户信息
     * @param category    手续费档位
     * @param coinPair    币对
     * @return 当前账户费率
     */
    SpotAccountTradeFeeResponse searchTradeFee(Credentials credentials, @Nullable SpotAccountTradeFeeCategory category, @Nullable CoinPair coinPair);

    /**
     * 获取委托单列表
     * <p>
     * 列出您当前所有的订单信息。
     * <p>
     * 限速规则：20次/2s
     *
     * @param credentials 账户信息
     * @param coinPair    币对
     * @param orderType   委托单类型
     * @param status      状态
     * @param algoIds     委托单id
     * @param window      窗口信息
     * @return 委托单列表
     */
    SpotAlgoOrderInfoMapResponse searchAlgoOrderInfoList(Credentials credentials, CoinPair coinPair, SpotAlgoOrderType orderType,
                                                         @Nullable SpotAlgoOrderStatus status, @Nullable String algoIds, @Nullable SpotApiWindow window);

    /**
     * 公共-获取币对信息
     * 获取交易币对的列表，查询各币对的交易限制和价格步长等信息。
     * <p>
     * 限速规则：20次/2s
     *
     * @return 币对信息
     */
    SpotMarketInfoResponse[] getMarketInfoList();

    /**
     * 公共-获取深度数据
     * 获取币对的深度列表。这个请求不支持分页，一个请求返回整个深度列表。
     * <p>
     * 限速规则：20次/2s
     *
     * @param size     返回深度档位数量，最多返回200
     * @param depth    按价格合并深度，例如：0.1或0.001
     * @param coinPair 币对
     * @return 深度数据
     */
    SpotOrderBooksResponse orderBooks(int size, BigDecimal depth, CoinPair coinPair);

    /**
     * 公共-获取全部ticker信息
     * 获取平台全部币对的最新成交价、买一价、卖一价和24小时交易量的快照信息。
     * <p>
     * 限速规则：20次/2s
     *
     * @return 全部ticker信息
     */
    SpotTickerResponse[] tickers();

    /**
     * 公共-获取某个ticker信息
     * 获取币对的最新成交价、买一价、卖一价和24小时交易量的快照信息。
     * <p>
     * 限速规则：20次/2s
     *
     * @param coinPair 币对
     * @return 某个ticker信息
     */
    SpotTickerResponse tickerByCoinPair(CoinPair coinPair);

    /**
     * 公共-获取成交数据
     * 本接口能查询最近100条数据。
     *
     * @param coinPair 币对
     * @param limit    分页返回的结果集数量，最大为100，不填默认返回100条
     * @return 成交数据
     */
    SpotFilledOrdersResponse[] trades(CoinPair coinPair, @Nullable Integer limit);

    /**
     * 公共-获取成交数据
     * 本接口能查询最近100条数据。
     * <p>
     * 限速规则：20次/2s
     *
     * @param coinPair 币对
     * @param interval 周期
     * @param start    开始时间  这个应该是允许不给的
     * @param end      结束时间  这个应该是允许不给的
     * @return K线数据
     */
    List<SpotMarketDataResponse> candlesData(CoinPair coinPair, CandleInterval interval, @Nullable LocalDateTime start, @Nullable LocalDateTime end);

    default List<SpotMarketDataResponse> candlesData(CoinPair coinPair, CandleInterval interval, LocalDateTime start) {
        LocalDateTime end = LocalDateTime.now(ZoneId.of("UTC"));
        return candlesData(coinPair, interval, start, end);
    }

    /**
     * 获取历史K线数据,目前暂提供9大币种的历史k线：BTC-USDT、ETH-USDT、LTC-USDT、ETC-USDT、XRP-USDT、EOS-USDT、BCH-USDT、BSV-USDT、TRX-USDT。
     *
     * @param coinPair 币对
     * @param interval 周期
     * @param start    开始时间
     * @param end      结束时间
     * @return K线数据
     */
    List<SpotMarketDataResponse> historyCandlesData(CoinPair coinPair, CandleInterval interval, LocalDateTime start, LocalDateTime end);

    default List<SpotMarketDataResponse> historyCandlesData(CoinPair coinPair, CandleInterval interval, LocalDateTime start) {
        LocalDateTime end = LocalDateTime.now(ZoneId.of("UTC"));
        return historyCandlesData(coinPair, interval, start, end);
    }
}
