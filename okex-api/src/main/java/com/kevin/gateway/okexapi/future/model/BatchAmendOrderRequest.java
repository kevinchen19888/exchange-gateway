package com.kevin.gateway.okexapi.future.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;


/**
 * 批量修改订单
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchAmendOrderRequest {

    private List<AmendOrderRequest> amendData;

}

