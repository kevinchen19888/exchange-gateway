package com.kevin.gateway.okexapi.swap.common.model;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SwapDepthInfoResponse {

    /**
     * 卖盘 深度
     */
    private List<SwapDepthItemResponse> asks;

    /**
     * 买盘 深度
     */
    private List<SwapDepthItemResponse> bids;

    /**
     * 时间
     */
    private LocalDateTime time;
}
