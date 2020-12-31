package com.kevin.gateway.huobiapi.spot.vo;

import lombok.Data;

/**
 * 市场深度数据
 */
@Data
public class SpotDepthVo {
    private Object[] bids;    //	当前的最高买价 [price, size]
    private Object[] asks;    //	当前的最低卖价 [price, size]
    private long ts;//时间戳
    private long version;
}
