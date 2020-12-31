package com.kevin.gateway.okexapi.spot.websocket.request;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.base.websocket.request.SubscribeOpBuilder;
import com.kevin.gateway.okexapi.spot.SpotMarketId;
import com.kevin.gateway.okexapi.spot.websocket.SpotChannel;

/**
 *  将到okex的websocket 请求信息封闭固定
 * */
public class WebsocketRequest {
    /**
     *  行情信息的订阅构建器
     */
    private final SubscribeOpBuilder quotationOpBuilder;

    /**
     *  交易信息的订阅构建器
     */
    private final SubscribeOpBuilder tradeOpBuilder;

    /**
     *  需登录才能访问的交易信息的订阅构建器
     */
    private final SubscribeOpBuilder loginTradeOpBuilder;
    public WebsocketRequest(){
        quotationOpBuilder = new SubscribeOpBuilder();
        tradeOpBuilder = new SubscribeOpBuilder();
        loginTradeOpBuilder = new SubscribeOpBuilder();
        createQuotationOpBuilder();
        createTradeOpBuilder();
        createLoginTradeOpBuilder();
    }

    /**
     *  下面的构建是不是通过配置信息来进行构建更好
     */

    /**
     *  需登录的websocket 交易频道
     */
    private void createLoginTradeOpBuilder(){
        loginTradeOpBuilder.addSubscriptionTopic(SpotChannel.ACCOUNT, Coin.of("ETH"))
                .addSubscriptionTopic(SpotChannel.ACCOUNT, Coin.of("BTC"))
                .addSubscriptionTopic(SpotChannel.ACCOUNT, Coin.of("ETHC"))

                .addSubscriptionTopic(SpotChannel.ORDER, SpotMarketId.of(CoinPair.of("ETH-USDT")))
                .addSubscriptionTopic(SpotChannel.ORDER, SpotMarketId.of(CoinPair.of("BTC-USDT")))
                .addSubscriptionTopic(SpotChannel.ORDER, SpotMarketId.of(CoinPair.of("ETH-BTC")));
    }

    /**
     *  不需登录的websocket 交易频道
     */
    private void createTradeOpBuilder(){
        tradeOpBuilder.addSubscriptionTopic(SpotChannel.MARGIN_ACCOUNT, SpotMarketId.of(CoinPair.of("ETH-USDT")))
                .addSubscriptionTopic(SpotChannel.MARGIN_ACCOUNT, SpotMarketId.of(CoinPair.of("BTC-USDT")))
                .addSubscriptionTopic(SpotChannel.MARGIN_ACCOUNT, SpotMarketId.of(CoinPair.of("ETH-BTC")))

                .addSubscriptionTopic(SpotChannel.ORDER_ALGO, SpotMarketId.of(CoinPair.of("ETH-USDT")))
                .addSubscriptionTopic(SpotChannel.ORDER_ALGO, SpotMarketId.of(CoinPair.of("BTC-USDT")))
                .addSubscriptionTopic(SpotChannel.ORDER_ALGO, SpotMarketId.of(CoinPair.of("ETH-BTC")));
    }

    private void createQuotationOpBuilder(){
        quotationOpBuilder.addSubscriptionTopic(SpotChannel.TICKER, SpotMarketId.of(CoinPair.of("ETH-USDT")))
                .addSubscriptionTopic(SpotChannel.TICKER, SpotMarketId.of(CoinPair.of("BTC-USDT")))
                .addSubscriptionTopic(SpotChannel.TICKER, SpotMarketId.of(CoinPair.of("ETH-BTC")))

                .addSubscriptionTopic(SpotChannel.TRADE, SpotMarketId.of(CoinPair.of("ETH-USDT")))
                .addSubscriptionTopic(SpotChannel.TRADE, SpotMarketId.of(CoinPair.of("BTC-USDT")))
                .addSubscriptionTopic(SpotChannel.TRADE, SpotMarketId.of(CoinPair.of("ETH-BTC")))

                .addSubscriptionTopic(SpotChannel.DEPTH, SpotMarketId.of(CoinPair.of("ETH-USDT")))
                .addSubscriptionTopic(SpotChannel.DEPTH, SpotMarketId.of(CoinPair.of("BTC-USDT")))
                .addSubscriptionTopic(SpotChannel.DEPTH, SpotMarketId.of(CoinPair.of("ETH-BTC")))

                .addSubscriptionTopic(SpotChannel.DEPTH5, SpotMarketId.of(CoinPair.of("ETH-USDT")))
                .addSubscriptionTopic(SpotChannel.DEPTH5, SpotMarketId.of(CoinPair.of("BTC-USDT")))
                .addSubscriptionTopic(SpotChannel.DEPTH5, SpotMarketId.of(CoinPair.of("ETH-BTC")))

                .addSubscriptionTopic(SpotChannel.DEPTH_L2_TBT, SpotMarketId.of(CoinPair.of("ETH-USDT")))
                .addSubscriptionTopic(SpotChannel.DEPTH_L2_TBT, SpotMarketId.of(CoinPair.of("BTC-USDT")))
                .addSubscriptionTopic(SpotChannel.DEPTH_L2_TBT, SpotMarketId.of(CoinPair.of("ETH-BTC")))

                .addSubscriptionTopic(SpotChannel.CANDLE60S, SpotMarketId.of(CoinPair.of("ETH-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE60S, SpotMarketId.of(CoinPair.of("BTC-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE60S, SpotMarketId.of(CoinPair.of("ETH-BTC")))

                .addSubscriptionTopic(SpotChannel.CANDLE180S, SpotMarketId.of(CoinPair.of("ETH-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE180S, SpotMarketId.of(CoinPair.of("BTC-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE180S, SpotMarketId.of(CoinPair.of("ETH-BTC")))

                .addSubscriptionTopic(SpotChannel.CANDLE300S, SpotMarketId.of(CoinPair.of("ETH-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE300S, SpotMarketId.of(CoinPair.of("BTC-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE300S, SpotMarketId.of(CoinPair.of("ETH-BTC")))

                .addSubscriptionTopic(SpotChannel.CANDLE900S, SpotMarketId.of(CoinPair.of("ETH-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE900S, SpotMarketId.of(CoinPair.of("BTC-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE900S, SpotMarketId.of(CoinPair.of("ETH-BTC")))

                .addSubscriptionTopic(SpotChannel.CANDLE1800S, SpotMarketId.of(CoinPair.of("ETH-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE1800S, SpotMarketId.of(CoinPair.of("BTC-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE1800S, SpotMarketId.of(CoinPair.of("ETH-BTC")))

                .addSubscriptionTopic(SpotChannel.CANDLE3600S, SpotMarketId.of(CoinPair.of("ETH-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE3600S, SpotMarketId.of(CoinPair.of("BTC-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE3600S, SpotMarketId.of(CoinPair.of("ETH-BTC")))

                .addSubscriptionTopic(SpotChannel.CANDLE7200S, SpotMarketId.of(CoinPair.of("ETH-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE7200S, SpotMarketId.of(CoinPair.of("BTC-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE7200S, SpotMarketId.of(CoinPair.of("ETH-BTC")))

                .addSubscriptionTopic(SpotChannel.CANDLE14400S, SpotMarketId.of(CoinPair.of("ETH-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE14400S, SpotMarketId.of(CoinPair.of("BTC-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE14400S, SpotMarketId.of(CoinPair.of("ETH-BTC")))

                .addSubscriptionTopic(SpotChannel.CANDLE21600S, SpotMarketId.of(CoinPair.of("ETH-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE21600S, SpotMarketId.of(CoinPair.of("BTC-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE21600S, SpotMarketId.of(CoinPair.of("ETH-BTC")))

                .addSubscriptionTopic(SpotChannel.CANDLE43200S, SpotMarketId.of(CoinPair.of("ETH-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE43200S, SpotMarketId.of(CoinPair.of("BTC-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE43200S, SpotMarketId.of(CoinPair.of("ETH-BTC")))

                .addSubscriptionTopic(SpotChannel.CANDLE86400S, SpotMarketId.of(CoinPair.of("ETH-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE86400S, SpotMarketId.of(CoinPair.of("BTC-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE86400S, SpotMarketId.of(CoinPair.of("ETH-BTC")))

                .addSubscriptionTopic(SpotChannel.CANDLE604800S, SpotMarketId.of(CoinPair.of("ETH-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE604800S, SpotMarketId.of(CoinPair.of("BTC-USDT")))
                .addSubscriptionTopic(SpotChannel.CANDLE604800S, SpotMarketId.of(CoinPair.of("ETH-BTC")));
    }

    public SubscribeOpBuilder getQuotationOpBuilder(){
        return quotationOpBuilder;
    }

    public SubscribeOpBuilder getTradeOpBuilder(){
        return tradeOpBuilder;
    }

    public SubscribeOpBuilder getLoginTradeOpBuilder(){
        return loginTradeOpBuilder;
    }
}
