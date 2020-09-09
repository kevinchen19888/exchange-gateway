package com.alchemy.gateway.exchangeclients.okex.resultModel;

import lombok.Data;

@Data
public class SpotTicker {
    private String last;
    private String last_qty;
    private String open_24h;
    private String high_24h;
    private String low_24h;
    private String base_volume_24h;
    private String timestamp;
    private String quote_volume_24h;
    private String best_ask;
    private String best_bid;
    private String instrument_id;
    private String best_ask_size;
    private String best_bid_size;
}
