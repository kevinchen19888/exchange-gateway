package com.kevin.gateway.huobiapi.spot.response.wallet;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 充币地址查询
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotDepositAddressResponse extends SpotBaseResponse {
    private List<SpotDepositAddress> data;

    @Data
    private static class SpotDepositAddress {
        private SpotCoin currency;    //	币种
        private String address;    //	充币地址
        private String addressTag;//	充币地址标签
        private SpotCoin chain;//链名称
    }
}
