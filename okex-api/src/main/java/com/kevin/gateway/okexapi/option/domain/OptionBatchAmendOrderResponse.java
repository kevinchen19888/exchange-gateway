package com.kevin.gateway.okexapi.option.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OptionBatchAmendOrderResponse extends OptionErrorResponse {
    private Boolean result;
    private List<OptionAmendOrderResponse> orderInfo;
}
