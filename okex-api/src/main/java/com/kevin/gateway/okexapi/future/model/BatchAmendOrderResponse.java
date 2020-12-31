package com.kevin.gateway.okexapi.future.model;

import lombok.Data;

import java.util.List;


@Data
public class BatchAmendOrderResponse {

    private boolean result;

    private List<AmendOrderResponse> orderInfo;

}

