package com.kevin.gateway.okexapi.future.model;


import lombok.Data;

import java.util.List;

@Data
public class OrderListResponse {

    private boolean result;


    private List<OrderDetailResponse> orderInfo;
}
