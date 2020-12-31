package com.kevin.gateway.okexapi.base.util;


/**
 * 深度数据档位枚举
 */
public enum DepthEntries {
    /**
     * 深度5档
     */
    DEPTH5(0),

    /**
     * 深度400档
     */
    DEPTH(1),

    /**
     * 深度增量400档，这个主要是体现在杠杆市场
     */
    DEPTH_L2_TBT(2);

    private final int value;

    DepthEntries(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}

