package com.kevin.gateway.okexapi.option.domain;

import lombok.Data;

import java.util.List;

@Data
public class OptionBatchOrderResponse {
    private List<OptionOrderResponse> orderInfo;
    private Boolean result;
}
