package com.kevin.gateway.okexapi.option.rest;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.base.util.OkexFeeCategory;
import com.kevin.gateway.okexapi.base.util.OkexPagination;
import com.kevin.gateway.okexapi.option.OptionMarketId;
import com.kevin.gateway.okexapi.option.domain.*;
import com.kevin.gateway.okexapi.option.util.OptionOrderStatus;
import com.kevin.gateway.okexapi.option.domain.*;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Okex option(期权合约)API
 */
public interface OptionApi {
    /**
     * 获取单个标的指数持仓信息
     * 限速规则：20次/2s
     *
     * @param credentials  凭证
     * @param underlying   标的指数，如BTC-USD
     * @param instrumentId 合约ID，如BTC-USD-190927-12500-C
     * @return 单个标的指数持仓信息
     */
    OptionPositionResponse getPosition(Credentials credentials, CoinPair underlying, @Nullable OptionMarketId instrumentId);

    /**
     * 获取单个标的物账户信息
     * 限速规则：20次/2s
     *
     * @param credentials 凭证
     * @param underlying  标的指数，如BTC-USD
     * @return 单个标的物账户信息
     */
    OptionAccountsResponse getAccount(Credentials credentials, CoinPair underlying);

    /**
     * 下单
     * 限速规则：20次/s
     *
     * @param credentials 凭证
     * @param req         请求参数封装
     * @return 下单结果信息
     */
    OptionOrderResponse order(Credentials credentials, OptionOrderRequest req);

    /**
     * 批量下单
     * 限速规则：20次/2s
     *
     * @param credentials 凭证
     * @param req         请求参数封装
     * @return 批量下单结果信息
     */
    OptionBatchOrderResponse batchOrder(Credentials credentials, OptionBatchOrderRequest req);

    /**
     * 撤销之前下的未完成订单。
     * 限速规则：20次/s
     *
     * @param credentials 凭证
     * @param req         请求参数封装
     * @return 撤单结果信息
     */
    OptionOrderCancelResponse cancelOrder(Credentials credentials, OptionCancelOrderRequest req);

    /**
     * 批量撤销之前下的未完成订单，每个标的指数最多可批量撤10个单。
     * 限速规则：20次/2s
     *
     * @param credentials 凭证
     * @param req         请求参数封装
     * @return 撤单结果信息
     */
    OptionBatchOrderCancelResponse batchCancelOrder(Credentials credentials, OptionBatchCancelOrderRequest req);

    /**
     * 修改之前下的未完成订单。
     * 限速规则：20次/s
     *
     * @param credentials 凭证
     * @param req         请求参数封装
     * @return 修改订单请求结果信息
     */
    OptionAmendOrderResponse amendOrder(Credentials credentials, OptionAmendOrderRequest req);

    /**
     * 批量修改之前下的未完成订单，每个标的指数最多可批量修改10个单。
     * 限速规则：20次/2s
     *
     * @param credentials 凭证
     * @param req         请求参数封装
     * @return 批量修改订单请求结果信息
     */
    OptionBatchAmendOrderResponse batchAmendOrder(Credentials credentials, OptionBatchAmendOrderRequest req);

    /**
     * 查询单个订单状态。(已撤销的未成交单只保留2个小时)
     * 限速规则：40次/2s
     *
     * @param credentials 凭证
     * @param underlying  合约标的指数，如BTC-USD
     * @param orderId     order_id和client_oid必须且只能选一个填写。订单ID。
     * @param clientOid   order_id和client_oid必须且只能选一个填写。下单时由您设置的订单ID来识别您的订单,
     *                    类型为字母（大小写）+数字或者纯字母（大小写） 1-32位字符
     * @return 订单状态返回信息
     */
    OptionOrdersResponse getOrderStatus(Credentials credentials, CoinPair underlying, @Nullable String orderId, @Nullable String clientOid);

    /**
     * 获取当前所有的订单列表。本接口能查询7天内数据。
     * 限速规则：10次/2s
     *
     * @param credentials  凭证
     * @param underlying   合约标的指数，如BTC-USD
     * @param instrumentId 合约ID，如BTC-USD-190927-12500-C。如果提供的instrument_id的标的物与underlying参数不同，返回错误码。
     * @param pagination   分页对象
     * @param state        订单状态("-2":失败,"-1":撤单成功,"0":等待成交 ,"1":部分成交, "2":完全成交,"3":下单中,"4":撤单中,
     *                     "6": 未完成（等待成交+部分成交），"7":已完成（撤单成功+完全成交））
     * @return 订单的结果信息列表
     */
    OptionOrderListResponse getOrders(Credentials credentials, CoinPair underlying, @Nullable OptionMarketId instrumentId,
                                      @Nullable OkexPagination pagination, OptionOrderStatus state);

    /**
     * 获取最近的成交明细列表。(本接口能查询7天内数据)
     * 限速规则：10次/2s
     *
     * @param credentials
     * @param underlying   合约标的指数，如BTC-USD
     * @param orderId      订单ID
     * @param instrumentId 合约名称，如BTC-USD-190927-12500-C
     * @param pagination   分页对象
     * @return 成交明细结果列表
     */
    OptionFillDetailListResponse getOrdersFillDetails(Credentials credentials, CoinPair underlying,
                                                      @Nullable String orderId, @Nullable OptionMarketId instrumentId,
                                                      @Nullable OkexPagination pagination);

    /**
     * 列出账户资产流水，账户资产流水是指导致账户余额增加或减少的行为。
     * (流水会分页，每页100条数据，并且按照时间倒序排序和存储，最新的排在最前面。 本接口能查询最近7天的数据)
     * 限速规则：5次/2s
     *
     * @param credentials 凭证
     * @param underlying  标的指数，如BTC-USD
     * @param pagination  分页对象
     * @return
     */
    OptionLedgerListResponse getLedgers(Credentials credentials, CoinPair underlying, @Nullable OkexPagination pagination);

    /**
     * 获取当前账户交易等级对应的手续费费率，母账户下的子账户的费率和母账户一致。每天凌晨0点更新一次
     * 限速规则：20次/2s
     *
     * @param credentials 凭证
     * @param category    手续费档位 1：第一档 ；category和underlying仅选填写一个参数
     * @param underlying  合约标的指数，e.g BTC-USD ； category和underlying仅选填写一个参数
     * @return 手续费结果信息
     */
    OptionFeeRateResponse getFeeRate(Credentials credentials, @Nullable OkexFeeCategory category, @Nullable CoinPair underlying);

    /**
     * 公共-获取标的指数
     * 获取期权交易已支持的标的指数列表。
     * 限速规则：20次/2s
     *
     * @return 标的指数列表
     */
    UnderlyingListResponse getUnderlying();

    /**
     * 公共-获取可用合约的列表。
     * 限速规则：20次/2s
     *
     * @param underlying   如BTC-USD
     * @param delivery     如果提供，只返回相应到期日的合约信息。格式为"YYMMDD"，如"190527"
     * @param instrumentId 如果提供，只返回相应合约信息
     * @return 可用合约列表
     */
    OptionInstrumentListResponse getInstruments(CoinPair underlying, @Nullable LocalDate delivery, @Nullable OptionMarketId instrumentId);

    /**
     * 公共-获取同一标的下所有期权合约详细定价。
     * 限速规则：20次/2s
     *
     * @param underlying 标的指数，如BTC-USD
     * @param delivery   合约到期日，如果提供，只返回相应到期日的合约信息。格式为"YYMMDD"，如"190527"
     * @return 获取的详细定价信息列表
     */
    OptionMarketDataListResponse getMarketDatas(CoinPair underlying, @Nullable LocalDate delivery);

    /**
     * 公共-获取单个期权合约详细定价
     *
     * @param underlying   标的指数，如BTC-USD
     * @param instrumentId 合约ID，如果提供，只返回相应合约信息
     * @return
     */
    OptionMarketDataResponse getMarketData(CoinPair underlying, OptionMarketId instrumentId);

    /**
     * 公共-获取期权合约的深度数据。
     * 限速规则：20次/2s
     *
     * @param instrumentId 合约ID，如BTC-USD-190927-12500-C
     * @param size         返回深度数量，最大值可传200
     * @return 深度数据结果信息
     */
    OptionOrderBookResponse getOrderBook(OptionMarketId instrumentId, @Nullable Integer size);

    /**
     * 公共-获取期权合约的成交记录。
     * 限速规则：20次/2s
     *
     * @param instrumentId 合约名称，如BTC-USD-190927-12500-C
     * @param pagination   分页参数对象
     * @return 期权合约的成交记录结果信息
     */
    OptionFilledOrderListResponse getFilledOrders(OptionMarketId instrumentId, @Nullable OkexPagination pagination);

    /**
     * 公共-获取某个期权合约的最新成交价、买一价、卖一价和对应的量。
     * 限速规则：20次/2s
     *
     * @param instrumentId 合约名称，如BTC-USD-190927-12500-C
     * @return ticker 结果信息
     */
    OptionTickerResponse getTicker(OptionMarketId instrumentId);

    /**
     * 公共-获取期权合约的K线数据，K线数据最多可获取最近1440条。
     * 限速规则：20次/2s
     *
     * @param instrumentId 合约名称，如BTC-USD-190927-12500-C
     * @param start        开始时间，ISO 8601格式的时间
     * @param end          结束时间，ISO 8601格式的时间
     * @param granularity  时间粒度，以秒为单位，默认值为60。如[60/180/300 900/1800/3600/7200/14400/21600/43200/86400/604800]
     * @return kline 数据
     */
    CandlestickListResponse getCandlesticks(OptionMarketId instrumentId, @Nullable LocalDateTime start,
                                            @Nullable LocalDateTime end, @Nullable CandleInterval granularity);

    /**
     * 公共-获取历史结算/行权记录
     * 限速规则：1次/10s
     *
     * @param instrumentId 标的指数 e.g BTC-USD ETH-USD
     * @param start        开始时间（ISO 8601标准，例如：2020-03-08T08:00:00Z）
     * @param limit        默认5条
     * @param end          结束时间（ISO 8601标准，例如：2020-03-10T08:00:00Z）
     * @return 历史结算/行权记录
     */
    SettlementAndExerciseHistoryListResponse getSettlementAndExerciseHistories(CoinPair instrumentId, @Nullable LocalDateTime start,
                                                                               @Nullable Integer limit, @Nullable LocalDateTime end);

}
