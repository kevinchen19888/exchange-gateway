package com.kevin.gateway.okexapi.future.model;


import com.kevin.gateway.okexapi.future.type.MarginMode;
import lombok.Data;

import java.util.List;

@Data
public class GetSingleContractPositionResponse {

    /**
     * 返回结果
     */
    private boolean result;

    /**
     * 详细信息
     */
    private List<SingleContractPositionResponseBase> holding;

    /**
     * 全仓crossed 或者逐仓fixed
     */
    private MarginMode marginMode;
}
