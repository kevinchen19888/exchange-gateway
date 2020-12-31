package com.kevin.gateway.okexapi.future.model;


import lombok.Data;

import java.util.List;

@Data
public class AllContractPositionResponse {

    /**
     * 返回结果
     */
    private boolean result;

    /**
     * 详细信息
     */
    private List<List<SingleContractPositionResponseBase>> holding;

}
