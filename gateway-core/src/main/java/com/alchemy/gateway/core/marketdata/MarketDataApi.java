package com.alchemy.gateway.core.marketdata;

import com.alchemy.gateway.core.common.CandleInterval;
import com.alchemy.gateway.core.common.Market;
import reactor.util.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 市场数据接口
 */
public interface MarketDataApi {

    /**
     * 订阅指定市场数据
     *
     * @param market       市场
     * @param intervals    K线周期列表
     * @param trades       是否订阅最新成交
     * @param orderBook    是否订阅深度数据
     * @param tricks24h    是否订阅24小时ticker数据
     * @param dataReceiver 数据接收者
     * @return 订阅对象
     */
    DataSubscription subscribe(Market market, @Nullable List<CandleInterval> intervals, boolean trades, boolean orderBook,
                               boolean tricks24h, MarketDataReceiver dataReceiver);


    /**
     * 订阅指定市场的全部（订阅所有支持的K线周期、订阅深度数据、订阅最新成交）数据
     *
     * @param market       市场
     * @param dataReceiver 数据接收者
     * @return 订阅对象
     */
    default DataSubscription subscribe(Market market, MarketDataReceiver dataReceiver) {
        return subscribe(market, supportCandleIntervals(), true, true, true, dataReceiver);
    }

    /**
     * 支持的K线行情周期列表
     *
     * @return 行情周期的列表
     */
    List<CandleInterval> supportCandleIntervals();

    /**
     * 判断是否支持指定的K线周期
     *
     * @param candleInterval K线周期
     * @return true 支持
     */
    default boolean supportsCandleInterval(CandleInterval candleInterval) {
        return supportCandleIntervals().contains(candleInterval);
    }


    /**
     * 获取交易所每次请求可以返回的最多K线数目
     *
     * @return 每次请求可以返回的最多K线数目，需要查看交易所API文档
     */
    long getMaxTickCountPerRequest();

    /**
     * 获取指定市场的历史数据
     *
     * @param market   市场
     * @param interval K线周期
     * @param start    起始时间
     * @param end      终止时间
     * @return K线数据列表
     */
    List<CandleTick> getHistory(Market market, CandleInterval interval, LocalDateTime start, LocalDateTime end);

    /**
     * 获取指定市场的历史数据（指定时间以后直到当前时间之间的K线数据）
     *
     * @param market   市场
     * @param interval K线周期
     * @param start    起始时间
     * @return K线数据列表
     */
    default List<CandleTick> getHistory(Market market, CandleInterval interval, LocalDateTime start) {
        LocalDateTime end = LocalDateTime.now();    // TODO: 是否使用 UTC 时间？
        return getHistory(market, interval, start, end);
    }
}
