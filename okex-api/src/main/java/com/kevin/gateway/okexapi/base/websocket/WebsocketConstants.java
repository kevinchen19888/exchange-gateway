package com.kevin.gateway.okexapi.base.websocket;

public class WebsocketConstants {
    /**
     *  深度5档买盘或卖盘数据记录至少有3个数据
     */
    public static int DEPTH5_DATA_SIZE_MIN=3;
    /**
     *  深度400或档买盘或卖盘数据记录至少有3个数据
     */
    public static int DEPTH_DATA_SIZE_MIN=4;

    /**
     *  spot K线数据记录至少有6个数据
     */
    public static int SPOT_CANDLE_DATA_SIZE_MIN = 6;

    /**
     *  FUTURE K线数据记录至少有6个数据
     */
    public static int FUTURE_CANDLE_DATA_SIZE_MIN = 7;

    /**
     *  INDEX K线数据记录至少有6个数据
     */
    public static int INDEX_CANDLE_DATA_SIZE_MIN = 6;

    /**
     *  OPTION K线数据记录至少有7个数据
     */
    public static int OPTION_CANDLE_DATA_SIZE_MIN = 7;

    /**
     *  SWAP K线数据记录至少有7个数据
     */
    public static int SWAP_CANDLE_DATA_SIZE_MIN = 7;
}
