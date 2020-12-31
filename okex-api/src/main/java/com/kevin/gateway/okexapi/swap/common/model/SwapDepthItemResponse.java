package com.kevin.gateway.okexapi.swap.common.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonDeserialize(using = SwapDepthItemSerializer.class)
public class SwapDepthItemResponse {

    /**
     * 深度价格
     */
    private BigDecimal price;

    /**
     * 此价格的合约张数
     */
    private BigDecimal size;

    /**
     * 此价格的强平单个数
     */
    private int forceSize;


    /**
     * 此价格的订单个数
     */
    private int orderSize;

}
