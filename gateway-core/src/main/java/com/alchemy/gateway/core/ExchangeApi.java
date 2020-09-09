package com.alchemy.gateway.core;

import com.alchemy.gateway.core.common.CoinPairSymbolConverter;
import com.alchemy.gateway.core.common.OrderLimitManager;
import com.alchemy.gateway.core.common.RateLimiterManager;
import com.alchemy.gateway.core.info.ConnectionInfo;
import com.alchemy.gateway.core.info.Features;
import com.alchemy.gateway.core.marketdata.MarketDataApi;
import com.alchemy.gateway.core.order.OrderApi;
import com.alchemy.gateway.core.wallet.AccountApi;
import com.alchemy.gateway.core.wallet.WalletApi;

public interface ExchangeApi {
    /**
     * 获取交易所名称（全局唯一，比如：binance, okex...）
     *
     * @return 交易所名称
     */
    String getName();

    /**
     * 获取交易所连接信息
     *
     * @return 交易所连接信息
     */
    ConnectionInfo getConnectionInfo();

    /**
     * 获取交易所特色
     *
     * @return 特色对象
     */
    Features getFeatures();

    /**
     * 获取币对到交易所符号转换器
     *
     * @return 币对到交易所符号转换器
     */
    CoinPairSymbolConverter getCoinPairSymbolConverter();

    /**
     * 获取订单相关接口
     *
     * @return 订单接口
     */
    OrderApi getOrderApi();

    /**
     * 获取账户相关接口
     *
     * @return 账户接口
     */
    AccountApi getAccountApi();

    /**
     * 获取钱包相关接口
     *
     * @return 钱包接口
     */
    WalletApi getWalletApi();

    /**
     * 获取市场数据相关接口
     *
     * @return 市场数据接口
     */
    MarketDataApi getMarketDataApi();

    /**
     * 获取限频器管理器
     *
     * @return RateLimiterManager
     */
    RateLimiterManager getRateLimiterManager();


    OrderLimitManager getOrderLimitManager();

}
