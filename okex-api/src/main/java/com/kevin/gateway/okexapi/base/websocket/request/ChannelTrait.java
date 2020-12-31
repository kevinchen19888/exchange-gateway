package com.kevin.gateway.okexapi.base.websocket.request;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.core.Symbolic;
import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.websocket.FutureChannel;
import com.kevin.gateway.okexapi.index.IndexInstrumentId;
import com.kevin.gateway.okexapi.index.websocket.IndexChannel;
import com.kevin.gateway.okexapi.option.OptionMarketId;
import com.kevin.gateway.okexapi.option.websocket.OptionChannel;
import com.kevin.gateway.okexapi.spot.SpotMarketId;
import com.kevin.gateway.okexapi.spot.websocket.SpotChannel;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import com.kevin.gateway.okexapi.swap.websocket.SwapChannel;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 频道特性
 */
public interface ChannelTrait extends Symbolic {
    /**
     * 频道的业务线
     *
     * @return 业务线
     */
    ChannelBusiness getChannelBusiness();

    /**
     * 频道是否需要登录
     *
     * @return true 需要登录
     */
    boolean isLoginRequired();

    /**
     * 获取频道名称, 不包含业务名, 比如: account, candle60s... 等
     *
     * @return 频道名称
     */
    String getChannelName();

    /**
     * 获取频道过滤器(参数)类型
     *
     * @return 频道过滤器(参数)类型
     */
    ChannelFilterType getChannelFilterType();

    /**
     * 检查频道是否支持指定的标识符类型
     *
     * @return boolean true 代表支持
     */
    default boolean acceptFilterObject(Symbolic symbolic) {
        ChannelFilterType tp = getChannelFilterType();
        switch (getChannelFilterType()) {
            case COIN:
                return symbolic instanceof Coin;
            case INSTRUMENT_ID: {
                switch (getChannelBusiness()) {
                    case SPOT:
                        return symbolic instanceof SpotMarketId || symbolic instanceof Coin;
                    case SWAP:
                        return symbolic instanceof SwapMarketId || symbolic instanceof CoinPair;
                    case FUTURE:
                        return symbolic instanceof FutureMarketId;
                    case OPTION:
                        return symbolic instanceof OptionMarketId || symbolic instanceof CoinPair;
                    case INDEX:
                        return symbolic instanceof IndexInstrumentId;
                }
            }
            case COIN_OR_UNDERLYING:
                return symbolic instanceof Coin || symbolic instanceof CoinPair;
            case INDEX_OR_COIN:
                return symbolic instanceof Coin || symbolic instanceof IndexInstrumentId;
            case NONE:
                return symbolic == null;
        }

        return false;
    }

    /**
     * 获取频道全称, 带有业务名前缀, 比如: spot/account, future/candle60s, ... 等
     *
     * @return 频道全称
     */
    default String getSymbol() {
        return getChannelBusiness().getSymbol() + "/" + getChannelName();
    }

    Pattern CHANNEL_PATTERN = Pattern.compile("^([a-z]+)/([a-z0-9_]+)$");

    @JsonCreator
    static ChannelTrait fromSymbol(String symbol) {
        Matcher matcher = CHANNEL_PATTERN.matcher(symbol);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("非法业务频道格式: " + symbol);
        }
        String businessName = matcher.group(1);
        String channelName = matcher.group(2);
        ChannelBusiness channelBusiness = ChannelBusiness.fromSymbol(businessName);
        switch (channelBusiness) {
            case SPOT:
                return SpotChannel.fromChannelName(channelName);
            case SWAP:
                return SwapChannel.fromChannelName(channelName);
            case INDEX:
                return IndexChannel.fromChannelName(channelName);
            case FUTURE:
                return FutureChannel.fromChannelName(channelName);
            case OPTION:
                return OptionChannel.fromChannelName(channelName);
            default:
                throw new IllegalArgumentException("unreachable code!");
        }
    }
}
