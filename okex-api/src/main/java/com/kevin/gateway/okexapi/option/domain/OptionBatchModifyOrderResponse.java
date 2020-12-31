package com.kevin.gateway.okexapi.option.domain;

import lombok.Data;

import java.util.List;

@Data
public class OptionBatchModifyOrderResponse {
    private List<OptionAmendOrderResponse> orderInfo;
    private Boolean result;
}
