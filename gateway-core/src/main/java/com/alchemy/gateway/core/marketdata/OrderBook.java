package com.alchemy.gateway.core.marketdata;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 深度数据
 */
@Data
public class OrderBook {
    private LocalDateTime timeStamp;
    private List<OrderBookEntry> asks;
    private List<OrderBookEntry> bids;

    int level;  // 深度级别

}
