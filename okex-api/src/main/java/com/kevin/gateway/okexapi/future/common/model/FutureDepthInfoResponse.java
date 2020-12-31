package com.kevin.gateway.okexapi.future.common.model;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FutureDepthInfoResponse {

    /**
     * 卖盘 深度
     */
    private List<FutureDepthItem> asks;

    /**
     * 买盘 深度
     */
    private List<FutureDepthItem> bids;

    /**
     * 时间
     */
    private LocalDateTime timestamp;
}
