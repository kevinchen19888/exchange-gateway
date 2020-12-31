package com.kevin.gateway.okexapi.swap.model;

import com.kevin.gateway.okexapi.future.type.MarginMode;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 所有/单个合约持仓信息
 */

@Data
public class GetSwapContractPositionResponse {

    /**
     * 仓位模式：逐仓fixed   全仓 crossed
     */
    private MarginMode marginMode;

    /**
     * 创建时间
     */
    private LocalDateTime timestamp;

    /**
     * 永续合约列表
     */
    private List<SwapContractPositionResponse> holding;


}

