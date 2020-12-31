package com.kevin.gateway.okexapi.future.service;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.common.model.*;
import com.kevin.gateway.okexapi.future.common.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface FutureCommonApi {

    /**
     * (1)公共-获取合约信息
     * 获取可用合约的列表，查询各合约的交易限制和价格步长等信息。
     * <p>
     * 限速规则：20次/2s （根据ip限速）
     *
     * @return 合约信息列表
     */
    CommonFutureContractInfoResponse[] getContractInfo();


    /**
     * (2)公共-获取深度数据
     * 获取合约的深度列表。这个请求不支持分页，一个请求返回整个深度列表。
     * <p>
     * 限速规则：20次/2s （根据underlying，分别限速）
     *
     * @param instrumentId 合约id
     * @param size         返回深度数量，最大值可传200，即买卖深度共400条
     * @param depth        按价格合并深度，例如：0.1，0.001
     * @return 深度数据
     */
    FutureDepthInfoResponse getFutureDepth(FutureMarketId instrumentId, Integer size, BigDecimal depth);


    /**
     * (3) 公共-获取全部ticker信息
     * 获取平台全部合约的最新成交价、买一价、卖一价和24小时交易量的快照信息。
     * <p>
     * 限速规则：20次/2s （根据ip限速
     *
     * @return 全部ticker信息
     */
    FutureTickerInfoResponse[] getAllTicker();


    /**
     * （4）公共-获取某个ticker信息
     * 获取合约的最新成交价、买一价、卖一价和24小时交易量的快照信息。
     * <p>
     * 限速规则：20次/2s （根据underlying，分别限速）
     * HTTP请求
     * GET/api/futures/v3/instruments/<instrument_id>/ticker
     * <p>
     * 请求示例
     * GET/api/futures/v3/instruments/BTC-USD-180309/ticker
     *
     * @param instrumentId 合约id
     * @return 某个ticker信息
     */
    FutureTickerInfoResponse getTickerByInstrumentId(FutureMarketId instrumentId);


    /**
     * (5)公共-获取成交数据
     * 获取合约最新的300条成交列表。
     * <p>
     * 限速规则：20次/2s （根据underlying，分别限速）
     * HTTP请求
     * GET/api/futures/v3/instruments/<instrument_id>/trades
     * <p>
     * 请求示例
     * GET/api/futures/v3/instruments/BTC-USD-180309/trades?after=2517062044057601&limit=2
     *
     * @param instrumentId 合约id
     * @param after        请求此id之前（更旧的数据）的分页内容，传的值为对应接口的trade_id
     * @param before       请求此id之后（更旧的数据）的分页内容，传的值为对应接口的trade_id
     * @param limit        分页返回的结果集数量，最大为100，不填默认返回100条
     * @return 获取合约最新的300条成交列表
     */
    FutureNewDealResponse[] getFuturesNewDeal(FutureMarketId instrumentId, Long after, Long before, Integer limit);


    /**
     * (6)公共-获取K线数据
     * 获取合约的K线数据。K线数据按请求的粒度分组返回，K线数据最多可获取最近1440条。
     * <p>
     * 限速规则：20次/2s （根据underlying，分别限速）
     * HTTP请求
     * GET/api/futures/v3/instruments/<instrument_id>/candles
     * <p>
     * 请求示例
     * GET/api/futures/v3/instruments/EOS-USD-190628/candles?start=2019-03-18T02:31:00Z&end=2019-03-20T02:55:00Z&granularity=60
     * <p>
     * （查询EOS-USD-180309的2019年3月18日02点31分到2019年3月20日02点55分的1分钟K线数据）
     *
     * @param instrumentId 合约id
     * @param start        开始时间（ISO 8601标准，例如：2018-06-20T02:31:00Z）
     * @param end          结束时间（ISO 8601标准，例如：2018-06-20T02:31:00Z）
     * @param granularity  时间粒度，以秒为单位，默认值60。如[60/180/300/900/1800/3600/7200/14400/21600/43200/86400/604800]，详见下解释说明
     * @return 合约的K线数据
     */

    FutureKLineResponse[] getFuturesCandles(FutureMarketId instrumentId, LocalDateTime start, LocalDateTime end, CandleInterval granularity);



    /**
     * (7)公共-获取指数信息
     * 获取币种指数。此接口为公共接口，不需要身份验证。
     * <p>
     * 限速规则：20次/2s （根据underlying，分别限速）
     * HTTP请求
     * GET/api/futures/v3/instruments/<instrument_id>/index
     * <p>
     * 请求示例
     * GET/api/futures/v3/instruments/EOS-USD-190628/index
     *
     * @param instrumentId 合约id
     * @return 获取币种指数
     */
    FutureIndexResponse getFutureIndex(FutureMarketId instrumentId);


    /**
     * (8)公共-获取法币汇率
     * 获取法币汇率。此接口为公共接口，不需要身份验证。
     * <p>
     * 限速规则：20次/2s （根据ip限速）
     * HTTP请求
     * GET/api/futures/v3/rate
     * <p>
     * 请求示例
     * GET/api/futures/v3/rate
     *
     * @return 法币汇率
     */
    FiatRateResponse getFiatRate();


    /**
     * (9)公共-获取预估交割价
     * 获取合约预估交割价。交割预估价只有交割前一小时才有返回值。此接口为公共接口，不需要身份验证。
     * <p>
     * 限速规则：20次/2s （根据underlying，分别限速）
     * HTTP请求
     * GET/api/futures/v3/instruments/<instrument_id>/estimated_price
     * <p>
     * 请求示例
     * GET/api/futures/v3/instruments/BTC-USD-180309/estimated_price
     *
     * @param instrumentId 合约id
     * @return 合约预估交割价
     */
    EstimatedDeliveryPriceResponse getEstimatedDeliveryPrice(FutureMarketId instrumentId);


    /**
     * (10)公共-获取平台总持仓量
     * 获取合约整个平台的总持仓量。此接口为公共接口，不需要身份验证。
     * <p>
     * 限速规则：20次/2s （根据underlying，分别限速）
     * HTTP请求
     * GET /api/futures/v3/instruments/<instrument_id>/open_interest
     * <p>
     * 请求示例
     * GET/api/futures/v3/instruments/BTC-USD-180309/open_interest
     *
     * @param instrumentId 合约id
     * @return 合约整个平台的总持仓量
     */
    FuturePlatformHoldsResponse getPlatformHolds(FutureMarketId instrumentId);


    /**
     * (11)公共-获取当前限价
     * 获取合约当前交易的最高买价和最低卖价。此接口为公共接口，不需要身份验证。
     * <p>
     * 限速规则：20次/2s （根据underlying，分别限速）
     * HTTP请求
     * GET/api/futures/v3/instruments/<instrument_id>/price_limit
     * <p>
     * 请求示例
     * GET/api/futures/v3/instruments/BTC-USD-180309/price_limit
     *
     * @param instrumentId 合约id
     * @return 当前交易的最高买价和最低卖价
     */
    FuturePriceLimitResponse getFuturePriceLimit(FutureMarketId instrumentId);


    /**
     * (12)公共-获取标记价格
     * 获取合约标记价格。此接口为公共接口，不需要身份验证。
     * <p>
     * 限速规则：20次/2s （根据underlying，分别限速）
     * HTTP请求
     * GET/api/futures/v3/instruments/<instrument_id>/mark_price
     * <p>
     * 请求示例
     * GET/api/futures/v3/instruments/BTC-USD-180309/mark_price
     *
     * @param instrumentId 合约ID
     * @return 合约标记价格
     */
    FutureMarkPriceResponse getFutureMarkPrice(FutureMarketId instrumentId);


    /**
     * (13)公共-获取强平单
     * 获取合约强平单，此接口为公共接口，不需要身份验证。
     * <p>
     * 限速规则：20次/2s （根据underlying，分别限速）
     * HTTP请求
     * GET/api/futures/v3/instruments/<instrument_id>/liquidation
     * <p>
     * 请求示例
     * GET/api/futures/v3/instruments/BTC-USD-180309/liquidation?status=0&limit=50
     *
     * @param instrumentId 合约id
     * @param status       状态
     *                     0：最近7天未成交
     *                     1：最近7天已成交
     * @param from         请求此id之前（更旧的数据）的分页内容，例如 2
     * @param to           请求此id之后（更旧的数据）的分页内容，例如 2
     * @param limit        分页返回的结果集数量，默认为100，最大为100，按时间倒序排列，越晚的在前面
     * @return 获取合约强平单
     */
    FutureLiquidationOrderResponse[] getLiquidationOrders(FutureMarketId instrumentId, Integer status, Integer from, Integer to, Integer limit);


    /**
     * (14)公共-获取历史结算/交割记录
     * 当使用instrument_id查询时，即查询某个合约的历史结算/交割时，只支持使用2020年以后的合约来获取历史结算/交割记录。
     * <p>
     * 限速规则：1次/60s
     * HTTP请求
     * GET/api/futures/v3/settlement/history
     * <p>
     * 请求示例
     * GET/api/futures/v3/settlement/history
     *
     * @param instrumentId 合约ID，如BTC-USD-180213，instrument_id和underlying 必须且只能选一个填写
     * @param underlying   合约标的指数，如BTC-USD，instrument_id和underlying必须且只能选一个填写
     * @param start        开始时间（ISO 8601标准，例如：2020-03-08T08:00:00Z）
     * @param end          结束时间（ISO 8601标准，例如：2020-03-08T08:00:00Z）
     * @param limit        每一页100条
     * @return 获取历史结算/交割记录
     */

    SettlementHistoryResponse[] getSettlementHistory(FutureMarketId instrumentId, CoinPair underlying, LocalDateTime start, LocalDateTime end, Integer limit);


    /**
     * (15)公共-获取历史K线数据
     * 获取K线的历史数据，返回结果按时间倒序排列，最多返回300条。目前暂提供9个合约的历史k线：BTC、ETH、LTC、ETC、XRP、EOS、BCH、BSV、TRX。
     * <p>
     * 限速规则：5次/2s
     * HTTP请求
     * GET/api/futures/v3/instruments/<instrument_id>/history/candles
     * <p>
     * 签名请求示例
     * GET/api/futures/v3/instruments/BTC-USD-200731/history/candles?start=2020-07-25T02:31:00.000Z&end=2020-07-24T02:55:00.000Z&granularity=60
     *
     * @param instrumentId 合约id
     * @param start        开始时间（ISO 8601标准，例如：2018-06-20T02:31:00Z）
     * @param end          结束时间（ISO 8601标准，例如：2018-06-20T02:31:00Z）
     * @param granularity  时间粒度，以秒为单位，默认值60。如[60/180/300/900/1800/3600/7200/14400/21600/43200/86400/604800]，详见下解释说明
     * @param limit        返回的k线数量，默认300条，最多填写300
     * @return 合约的历史K线数据
     */
    HistoryKLineResponse[] getFuturesHistoryKlines(FutureMarketId instrumentId, LocalDateTime start, LocalDateTime end, CandleInterval granularity, Integer limit);
}
