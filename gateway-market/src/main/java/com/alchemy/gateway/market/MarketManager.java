package com.alchemy.gateway.market;

import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.common.MarketType;

import java.util.List;
import java.util.Set;

/**
 * 交易所市场管理器
 */
public interface MarketManager {

    /**
     * 装载所有的市场
     *
     * @param marketLoader 市场装载器
     */
    void load(MarketLoader marketLoader);

    /**
     * 获取所有的交易所市场
     *
     * @return 交易所市场列表
     */
    List<ExchangeMarket> getExchangeMarkets();

    /**
     * 获得所有交易所名称的列表
     *
     * @return 交易所名称的列表
     */
    Set<String> getExchangeNames();

    /**
     * 获得指定交易所的市场列表
     *
     * @param exchangeName 交易所名称，比如：binance, okex, ...
     * @return 市场列表
     */
    List<Market> getMarkets(String exchangeName);

    /**
     * 判断指定的市场是否存在
     *
     * @param exchangeName 交易所名称，比如：binance, okex, ...
     * @param market       市场
     * @return true 代表存在
     */
    boolean exists(String exchangeName, Market market);

    /**
     * 判断指定的市场是否存在
     *
     * @param exchangeName 交易所名称，比如：binance, okex, ...
     * @param coinPair     币对
     * @param marketType   市场类型
     * @return true 代表存在
     */
    default boolean exists(String exchangeName, CoinPair coinPair, MarketType marketType) {
        return exists(exchangeName, new Market(coinPair, marketType, 0));
    }

    Market getMarket(String exchangeName, CoinPair coinPair);

}
