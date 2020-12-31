package com.kevin.gateway.okexapi.swap.websocket;

import com.kevin.gateway.okexapi.base.websocket.request.ChannelBusiness;
import com.kevin.gateway.okexapi.base.websocket.request.ChannelFilterType;
import com.kevin.gateway.okexapi.base.websocket.request.ChannelTrait;

public enum SwapChannel implements ChannelTrait {
    /**
     * 【永续合约】行情数据频道
     */
    TICKER(false, "ticker", ChannelFilterType.INSTRUMENT_ID),

    /**
     * 【永续合约】1分钟K线数据频道
     */
    CANDLE60S(false, "candle60s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【永续合约】3分钟K线数据频道
     */
    CANDLE180S(false, "candle180s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【永续合约】5分钟K线数据频道
     */
    CANDLE300S(false, "candle300s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【永续合约】15分钟K线数据频道
     */
    CANDLE900S(false, "candle900s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【永续合约】30分钟K线数据频道
     */
    CANDLE1800S(false, "candle1800s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【永续合约】1小时K线数据频道
     */
    CANDLE3600S(false, "candle3600s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【永续合约】2小时K线数据频道
     */
    CANDLE7200S(false, "candle7200s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【永续合约】4小时K线数据频道
     */
    CANDLE14400S(false, "candle14400s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【永续合约】6小时K线数据频道
     */
    CANDLE21600S(false, "candle21600s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【永续合约】12小时K线数据频道
     */
    CANDLE43200S(false, "candle43200s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【永续合约】1day K线数据频道
     */
    CANDLE86400S(false, "candle86400s", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【永续合约】1week K线数据频道
     */
    CANDLE604800S(false, "candle604800s", ChannelFilterType.INSTRUMENT_ID),

    /**
     * 【永续合约】交易信息频道
     */
    TRADE(false, "trade", ChannelFilterType.INSTRUMENT_ID),

    /**
     * 【永续合约】合约信息频道
     */
    INSTRUMENTS(false, "instruments", ChannelFilterType.INSTRUMENT_ID),

    /**
     * 【永续合约】资金费率频道
     */
    FUNDING_RATE(false, "funding_rate", ChannelFilterType.INSTRUMENT_ID),

    /**
     * 【永续合约】限价范围频道
     */
    PRICE_RANGE(false, "price_range", ChannelFilterType.INSTRUMENT_ID),

    /**
     * 【永续合约】深度数据频道，首次Example Response，后续增量
     */
    DEPTH(false, "depth", ChannelFilterType.INSTRUMENT_ID),

    /**
     * 【永续合约】深度数据频道，每次返回前5档
     */
    DEPTH5(false, "depth5", ChannelFilterType.INSTRUMENT_ID),
    /**
     * 【永续合约】公共-400档增量数据频道
     */
    DEPTH_L2_TBT(false, "depth_l2_tbt", ChannelFilterType.INSTRUMENT_ID),

    /**
     * 【永续合约】标记价格频道
     */
    MARK_PRICE(false, "mark_price", ChannelFilterType.INSTRUMENT_ID),

    /**
     * 【永续合约】用户账户信息频道
     */
    ACCOUNT(true, "account", ChannelFilterType.INSTRUMENT_ID),

    /**
     * 【永续合约】用户持仓信息频道
     */
    POSITION(true, "position", ChannelFilterType.INSTRUMENT_ID),

    /**
     * 【永续合约】用户委托策略频道
     */
    ORDER_ALGO(true, "order_algo", ChannelFilterType.INSTRUMENT_ID),

    /**
     * 【永续合约】用户交易数据频道
     */
    ORDER(true, "order", ChannelFilterType.INSTRUMENT_ID);

    private final boolean loginRequired;
    private final String channelName;
    private final ChannelFilterType channelFilterType;

    SwapChannel(boolean loginRequired, String channelName, ChannelFilterType channelFilterType) {
        this.loginRequired = loginRequired;
        this.channelName = channelName;
        this.channelFilterType = channelFilterType;
    }

    public static SwapChannel fromChannelName(String channelName) {
        for (SwapChannel wc : SwapChannel.values()) {
            if (wc.channelName.equals(channelName)) {
                return wc;
            }
        }
        throw new IllegalArgumentException("非法的永续合约频道名: " + channelName);
    }

    @Override
    public ChannelBusiness getChannelBusiness() {
        return ChannelBusiness.SWAP;
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
