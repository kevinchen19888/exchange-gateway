package com.kevin.gateway.huobiapi.spot.response.subuser;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 子用户充币地址查询
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotSubUserDepositAddressResponse extends SpotBaseResponse {
    private List<SpotSubUserDepositAddress> data;

    @Data
    private static class SpotSubUserDepositAddress {
        private SpotCoin currency;    //	币种
        private String address;    //	充币地址
        private String addressTag;    //	充币地址标签
        private String chain; //	链名称
    }
}
