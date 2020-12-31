package com.kevin.gateway.okexapi.index.websocket;

import com.kevin.gateway.okexapi.base.websocket.request.ChannelBusiness;
import com.kevin.gateway.okexapi.base.websocket.request.ChannelFilterType;
import com.kevin.gateway.okexapi.base.websocket.request.ChannelTrait;

public enum IndexChannel implements ChannelTrait {
    /**
     * 【公共指数频道】获取公共指数行情
     */
    TICKER(false, "ticker", ChannelFilterType.INDEX_OR_COIN),
    /**
     * 【公共指数频道】1分钟K线数据频道
     */
    CANDLE60S(false, "candle60s", ChannelFilterType.INDEX_OR_COIN),

    /**
     * 【公共指数频道】3分钟K线数据频道
     */
    CANDLE180S(false, "candle180s", ChannelFilterType.INDEX_OR_COIN),

    /**
     * 【公共指数频道】5分钟K线数据频道
     */
    CANDLE300S(false, "candle300s", ChannelFilterType.INDEX_OR_COIN),

    /**
     * 【公共指数频道】15分钟K线数据频道
     */
    CANDLE900S(false, "candle900s", ChannelFilterType.INDEX_OR_COIN),

    /**
     * 【公共指数频道】30分钟K线数据频道
     */
    CANDLE1800S(false, "candle1800s", ChannelFilterType.INDEX_OR_COIN),

    /**
     * 【公共指数频道】1小时K线数据频道
     */
    CANDLE3600S(false, "candle3600s", ChannelFilterType.INDEX_OR_COIN),

    /**
     * 【公共指数频道】2小时K线数据频道
     */
    CANDLE7200S(false, "candle7200s", ChannelFilterType.INDEX_OR_COIN),

    /**
     * 【公共指数频道】4小时K线数据频道
     */
    CANDLE14400S(false, "candle14400s", ChannelFilterType.INDEX_OR_COIN),

    /**
     * 【公共指数频道】6小时K线数据频道
     */
    CANDLE21600S(false, "candle21600s", ChannelFilterType.INDEX_OR_COIN),

    /**
     * 【公共指数频道】12小时K线数据频道
     */
    CANDLE43200S(false, "candle43200s", ChannelFilterType.INDEX_OR_COIN),

    /**
     * 【公共指数频道】1day小时K线数据频道
     */
    CANDLE86400S(false, "candle86400s", ChannelFilterType.INDEX_OR_COIN),

    /**
     * 【公共指数频道】1 week K线数据频道
     */
    CANDLE604800S(false, "candle604800s", ChannelFilterType.INDEX_OR_COIN);

    private final boolean loginRequired;
    private final String channelName;
    private final ChannelFilterType channelFilterType;

    IndexChannel(boolean loginRequired, String channelName, ChannelFilterType channelFilterType) {
        this.loginRequired = loginRequired;
        this.channelName = channelName;
        this.channelFilterType = channelFilterType;
    }

    public static IndexChannel fromChannelName(String channelName) {
        for (IndexChannel ic : IndexChannel.values()) {
            if (ic.channelName.equals(channelName)) {
                return ic;
            }
        }
        throw new IllegalArgumentException("非法的公共指数频道名：" + channelName);
    }

    @Override
    public ChannelBusiness getChannelBusiness() {
        return ChannelBusiness.INDEX;
    }

    @Override
    public boolean isLoginRequired() {
        return this.loginRequired;
    }

    @Override
    public String getChannelName() {
        return this.channelName;
    }

    @Override
    public ChannelFilterType getChannelFilterType() {
        return this.channelFilterType;
    }

}
