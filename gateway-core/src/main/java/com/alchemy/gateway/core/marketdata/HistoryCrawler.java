package com.alchemy.gateway.core.marketdata;

import com.alchemy.gateway.core.ExchangeApi;
import com.alchemy.gateway.core.common.CandleInterval;
import com.alchemy.gateway.core.common.Market;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 历史数据爬取器
 */
@Slf4j
public class HistoryCrawler {

    private final ExchangeApi exchangeApi;
    private final Market market;
    private final long maxTickCount;
    private CandleInterval interval = null;
    private LocalDateTime startTime = null;
    private boolean forward = true;
    private boolean finished = true;

    public HistoryCrawler(@NonNull ExchangeApi exchangeApi, @NonNull Market market) {
        this(exchangeApi, market, Long.MAX_VALUE);
    }

    public HistoryCrawler(@NonNull ExchangeApi exchangeApi, @NonNull Market market, long maxTickCount) {
        this.exchangeApi = exchangeApi;
        this.market = market;
        // 每次爬取最多可以获取的蜡烛线数目是“传入参数”和“交易所支持的最大每次返回数”的最小值：
        //      min(exchange.maxTickCountPerRequest, maxTickCount)
        this.maxTickCount = Long.min(exchangeApi.getMarketDataApi().getMaxTickCountPerRequest(), maxTickCount);
    }

    /**
     * 交易所API
     *
     * @return 指定爬取的交易所API
     */
    public ExchangeApi getExchangeApi() {
        return this.exchangeApi;
    }

    /**
     * 市场
     *
     * @return 指定爬取的市场
     */
    public Market getMarket() {
        return market;
    }

    /**
     * 每次最大返回的K线数目
     *
     * @return 最大返回的K线数目
     */
    public long getMaxTickCount() {
        return maxTickCount;
    }

    /**
     * K线周期
     *
     * @return K线周期
     */
    public CandleInterval getInterval() {
        return interval;
    }

    /**
     * 开始时间
     * 注意：此时间会随着调用 next 而改变
     *
     * @return 返回下一次爬取的开始时间
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * 是否向前爬取
     *
     * @return true 向前爬取
     */
    public boolean isForward() {
        return forward;
    }

    /**
     * 开发爬取历史数据
     *
     * @param interval  指定周期（交易所必须支持此种周期，否则抛出异常）
     * @param startTime 开始时间
     * @param forward   false 向后爬取, true 向前爬取
     */
    public void start(@NonNull CandleInterval interval, @NonNull LocalDateTime startTime, boolean forward) {
        if (!getExchangeApi().getMarketDataApi().supportsCandleInterval(interval)) {
            throw new IllegalArgumentException("爬取数据时传入了交易所不支持的K线周期: " + interval);
        }

        this.interval = interval;
        this.startTime = startTime;
        this.forward = forward;
        this.finished = false;
    }

    /**
     * 判断是否已经爬取完成
     *
     * @return true 还有数据需要爬取
     */
    public boolean hasNext() {
        return !this.finished;
    }

    /**
     * 爬取并返回数据
     * 注意：爬取后 startTime 会做相应的调整，以便后续继续爬取
     *
     * @return 爬取后的数据
     */
    public List<CandleTick> next() {
        if (this.finished) {
            throw new IllegalStateException("请先调用 start 方法");
        }
        LocalDateTime st;
        LocalDateTime et;
        if (forward) {
            st = startTime.minusMinutes(maxTickCount * (interval.getMilliSeconds() / (60 * 1000)));
            et = startTime;
            startTime = st;
        } else {
            st = startTime;
            et = startTime.plusMinutes(maxTickCount * (interval.getMilliSeconds() / (60 * 1000)));
            startTime = et;
        }
        log.info("开始时间{},结束时间{}", st, et);
        List<CandleTick> candleTicks = exchangeApi.getMarketDataApi().getHistory(market, interval, st, et);
        if (candleTicks.size() <= 0 || et.equals(LocalDateTime.now())) {
            finished = true;
        }
        return candleTicks;
    }


}
