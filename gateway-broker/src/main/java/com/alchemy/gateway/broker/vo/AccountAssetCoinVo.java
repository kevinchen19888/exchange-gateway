package com.alchemy.gateway.broker.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 账户资产具体的币种信息
 *
 * @author zoulingwei
 */
@Builder
@Data
public class AccountAssetCoinVo{
    @JsonProperty("coin")
    private String coin;
    @JsonProperty("balance")
    private String balance;  // 余额
    @JsonProperty("frozen")
    private String frozen;  // 冻结
}
