package com.kevin.gateway.okexapi.swap.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetSwapOrdersListResponse {

    @JsonProperty("orderStrategyVOS")
    private List<SwapOrdersBaseItem> orderStrategyVOS;
}
