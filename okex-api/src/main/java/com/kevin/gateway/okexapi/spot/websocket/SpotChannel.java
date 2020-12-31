package com.kevin.gateway.okexapi.spot.websocket;

import com.kevin.gateway.okexapi.base.websocket.request.ChannelBusiness;
import com.kevin.gateway.okexapi.base.websocket.request.ChannelFilterType;
import com.kevin.gateway.okexapi.base.websocket.request.ChannelTrait;

public enum SpotChannel implements ChannelTrait {
    /**
     * 【现货】行情数据频道
     */
    TICKER(false, "ticker", ChannelFilterType.INSTRUMENT_ID),

    /**
     * 【现货】交易信息频道
     */
    TRADE(false, "trade", ChannelFilterType.INSTRUMENT_ID),

    /**
     * 【现货】400档深度频道
     */
    DEPTH(false, "depth", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【现货】400档增量数据频道
     */
    DEPTH_L2_TBT(false, "depth_l2_tbt", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【现货】1分钟K线数据频道
     */
    CANDLE60S(false, "candle60s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【现货】3分钟K线数据频道
     */
    CANDLE180S(false, "candle180s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【现货】5分钟K线数据频道
     */
    CANDLE300S(false, "candle300s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【现货】5分钟K线数据频道
     */
    CANDLE900S(false, "candle900s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【现货】30分钟K线数据频道
     */
    CANDLE1800S(false, "candle1800s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【现货】60分钟K线数据频道
     */
    CANDLE3600S(false, "candle3600s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【现货】2h K线数据频道
     */
    CANDLE7200S(false, "candle7200s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【现货】4h K线数据频道
     */
    CANDLE14400S(false, "candle14400s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【现货】6h K线数据频道
     */
    CANDLE21600S(false, "candle21600s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【现货】12h K线数据频道
     */
    CANDLE43200S(false, "candle43200s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【现货】1day K线数据频道
     */
    CANDLE86400S(false, "candle86400s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【现货】1week K线数据频道
     */
    CANDLE604800S(false, "candle604800s", ChannelFilterType.INSTRUMENT_ID),

    /**
     * 【现货】深度数据频道，每次返回前5档
     */
    DEPTH5(false, "depth5", ChannelFilterType.INSTRUMENT_ID),

    /**
     * 【现货】用户币币账户信息频道，需登录
     */
    ACCOUNT(true, "account", ChannelFilterType.COIN),

    /**
     * 【现货】用户杠杆账户信息频道
     */
    MARGIN_ACCOUNT(true, "margin_account", ChannelFilterType.INSTRUMENT_ID),

    /**
     * 【现货】用户委托策略频道
     */
    ORDER_ALGO(true, "order_algo", ChannelFilterType.INSTRUMENT_ID),

    /**
     * 【现货】用户交易数据频道，需登录
     */
    ORDER(true, "order", ChannelFilterType.INSTRUMENT_ID);

    private final boolean loginRequired;
    private final String channelName;
    private final ChannelFilterType channelFilterType;

    SpotChannel(boolean loginRequired, String channelName, ChannelFilterType channelFilterType) {
        this.loginRequired = loginRequired;
        this.channelName = channelName;
        this.channelFilterType = channelFilterType;
    }

    public static SpotChannel fromChannelName(String channelName) {
        for (SpotChannel sc : SpotChannel.values()) {
            if (sc.channelName.equals(channelName)) {
                return sc;
            }
        }
        throw new IllegalArgumentException("非法的现货频道名: " + channelName);
    }

    @Override
    public ChannelBusiness getChannelBusiness() {
        return ChannelBusiness.SPOT;
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
        return channelFilterType;
    }

    public boolean isTicker(){
        return this.channelName.equals("ticker")?true:false;
    }

    public boolean isTrade(){
        return this.channelName.equals("trade")?true:false;
    }

    public boolean isDepth(){
        return this.channelName.contains("depth")?true:false;
    }

    public boolean isCandle(){
        return this.channelName.contains("candle")?true:false;
    }
}
