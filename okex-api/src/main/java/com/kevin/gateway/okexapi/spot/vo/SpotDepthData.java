package com.kevin.gateway.okexapi.spot.vo;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.spot.SpotMarketId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SpotDepthData {
    private SpotMarketId instrumentId;
    private List<SpotDepth> asks;
    private List<SpotDepth> bids;
    private LocalDateTime timestamp;
    private long checksum;
}
