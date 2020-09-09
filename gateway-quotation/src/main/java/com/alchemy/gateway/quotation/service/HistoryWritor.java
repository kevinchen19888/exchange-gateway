package com.alchemy.gateway.quotation.service;

import com.alchemy.gateway.core.common.CandleInterval;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.marketdata.CandleTick;
import com.alchemy.gateway.quotation.entity.Kline;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 历史数据存储器
 */
public interface HistoryWritor {

    /**
     * 添加数据
     *
     * @param kline 行情数据信息
     * @return Kline
     */
    void save(Kline kline, String granularity);

    void saveAll(List<Kline> klineList, String granularity,String exchangeName);

    Kline findKlineLatest(String exchangeName, String instrumentId, String granularity);

    Kline findKlineOldest(String exchangeName, String instrumentId, String granularity);

    Kline findKlineLatest(String exchangeName, String instrumentId, String granularity, LocalDateTime time);

    /**
     * 保存历史K线数据
     *
     * @param candleTicks    历史K线数据
     * @param candleInterval 周期
     * @param exchangeName   交易所名称
     * @param market         市场币对
     */
    void saveKlineHistory(List<CandleTick> candleTicks, CandleInterval candleInterval, String exchangeName, Market market);

    /**
     * 保存历史K线数据
     *
     * @param candleTick     历史K线数据
     * @param candleInterval 周期
     * @param exchangeName   交易所名称
     * @param market         市场币对
     */
    void saveCandleTick(CandleTick candleTick, CandleInterval candleInterval, String exchangeName, Market market);

}
