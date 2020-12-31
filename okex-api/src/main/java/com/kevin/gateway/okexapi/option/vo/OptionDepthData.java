package com.kevin.gateway.okexapi.option.vo;

import com.kevin.gateway.okexapi.option.OptionMarketId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 首次400档：
 * {
 *     "table": "option/depth",
 *     "action": "partial",
 *     "data": [{
 *         "instrument_id": "BTC-USD-190927-12500-C",
 *         "asks": [
 *             ["5621.7", "58", "0", "2"],
 *             ["5621.8", "125", "0", "5"],
 *             ["5621.9", "0", "0", "0"],
 *             ["5622", "84", "0", "2"],
 *             ["5623.5", "0", "0", "0"],
 *             ["5624.2", "4", "0", "1"],
 *             ...
 *             ["5625.1", "0", "0", "0"],
 *             ["5625.9", "0", "0", "0"],
 *             ["5629.3", "2", "0", "1"],
 *             ["5650", "187", "0", "8"],
 *             ["5789", "1", "0", "1"]
 *         ],
 *         "bids": [
 *             ["5621.3", "287", "0", "8"],
 *             ["5621.2", "41", "0", "1"],
 *             ["5621.1", "2", "0", "1"],
 *             ["5621", "26", "0", "2"],
 *             ["5620.8", "194", "0", "2"],
 *             ["5620", "2", "0", "1"],
 *             ...
 *             ["5618.8", "204", "0", "2"],
 *             ["5618.4", "0", "0", "0"],
 *             ["5617.2", "2", "0", "1"],
 *             ["5609.9", "0", "0", "0"],
 *             ["5433", "0", "0", "0"],
 *             ["5430", "0", "0", "0"]
 *         ],
 *         "timestamp": "2019-05-06T07:03:33.048Z",
 *         "checksum": -186865074
 *     }]
 *
 * 增量：
 * {
 *     "table": "option/depth",
 *     "action": "update",
 *     "data": [{
 *         "instrument_id": "BTC-USD-190927-12500-C",
 *         "asks": [],
 *         "bids": [
 *             ["5431", "0", "0", "0"]
 *         ],
 *         "timestamp": "2019-05-06T07:03:33.148Z",
 *         "checksum": -1200119424
 *     }]
 * }
 */
@Data
public class OptionDepthData {
    private OptionMarketId instrumentId;
    private List<OptionDepth> asks;
    private List<OptionDepth> bids;

    private LocalDateTime timestamp;
    private long checksum;

}
