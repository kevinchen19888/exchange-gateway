package com.kevin.gateway.okexapi.option.domain;

import com.kevin.gateway.core.CoinPair;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UnderlyingListResponse extends OptionErrorResponse {
    private CoinPair[] underlings;
}
