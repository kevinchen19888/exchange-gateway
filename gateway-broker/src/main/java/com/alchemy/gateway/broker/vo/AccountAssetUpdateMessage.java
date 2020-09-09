package com.alchemy.gateway.broker.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class AccountAssetUpdateMessage {
    @JsonProperty("account_id")
    private String accountId;
    private List<AccountAssetCoinVo> assets;

}
