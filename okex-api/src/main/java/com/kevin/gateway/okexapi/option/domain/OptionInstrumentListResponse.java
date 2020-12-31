package com.kevin.gateway.okexapi.option.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OptionInstrumentListResponse extends OptionErrorResponse {
    private OptionInstrumentResponse[] instruments;
}
