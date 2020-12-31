package com.kevin.gateway.huobiapi.spot.response.wallet;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 提币地址查询
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotWithdrawAddressResponse extends SpotBaseResponse {
    private List<SpotWithdrawAddress> data;

    @Data
    private static class SpotWithdrawAddress {
        private SpotCoin currency;//	币种
        private String chain;//	链名称
        private String note;//	地址备注
        private String addressTag;//	地址标签，如有
        private String address;    //	地址
    }

    private long nextId;//下页起始编号（提币地址ID，仅在查询结果需要分页返回时，包含此字段，详细见备注）
}
