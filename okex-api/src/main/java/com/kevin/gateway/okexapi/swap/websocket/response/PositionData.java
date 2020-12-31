package com.kevin.gateway.okexapi.swap.websocket.response;

import com.kevin.gateway.okexapi.future.type.MarginMode;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户持仓信息
 */
@Data
public class PositionData {
    /**
     * 合约名称
     */
    private SwapMarketId instrumentId;
    /**
     * fixed:逐仓
     * crossed:全仓
     */
    private MarginMode marginMode;
    /**
     * 创建时间
     */
    private LocalDateTime timestamp;
    @JsonIgnore
    private Map<String,List<HoldingData>> holding = new HashMap<>();

    @JsonAnySetter
    public void addHolding(String key, List<HoldingData> value) {
        holding.put(key, value);
    }

}
