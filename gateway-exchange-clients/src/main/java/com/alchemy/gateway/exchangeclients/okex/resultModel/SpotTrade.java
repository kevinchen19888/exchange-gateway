package com.alchemy.gateway.exchangeclients.okex.resultModel;

import lombok.Data;

@Data
public class SpotTrade {
    private String timestamp;
    private String trade_id;
    private String price;
    private String size;
    private String side;
}
