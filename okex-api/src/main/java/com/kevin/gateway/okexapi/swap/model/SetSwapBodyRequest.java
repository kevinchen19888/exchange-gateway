package com.kevin.gateway.okexapi.swap.model;

import com.kevin.gateway.okexapi.swap.type.SwapSideType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SetSwapBodyRequest {
    /**
     * 杠杆倍数，填写1-100的数值
     */
    private int leverage;


    /**
     * 方向
     * 1:逐仓-多仓
     * 2:逐仓-空仓
     * 3:全仓
     */
    private SwapSideType side;

}

