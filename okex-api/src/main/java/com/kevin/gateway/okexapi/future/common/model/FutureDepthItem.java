package com.kevin.gateway.okexapi.future.common.model;


import com.kevin.gateway.okexapi.future.common.type.FutureDepthItemSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonDeserialize(using = FutureDepthItemSerializer.class)
public class FutureDepthItem {

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
