package com.kevin.gateway.huobiapi.spot.request;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 提币
 */
@Data
public class SpotWithdrawRequest {
    private String address;    //	提币地址	仅支持在官网上相应币种地址列表 中的地址
    private BigDecimal amount;    //	提币数量
    private SpotCoin currency;    //	资产类型	btc, ltc, bch, eth, etc
    private BigDecimal fee;    //	转账手续费
    private String chain;    //	取值参考GET /v2/reference/currencies,例如提USDT至OMNI时须设置此参数为"usdt"，提USDT至TRX时须设置此参数为"trc20usdt"，其他币种提币无须设置此参数
    @JsonProperty(value = "addr-tag")
    private String addrTag;    //	虚拟币共享地址tag，适用于xrp，xem，bts，steem，eos，xmr	格式, "123"类的整数字符串
}
