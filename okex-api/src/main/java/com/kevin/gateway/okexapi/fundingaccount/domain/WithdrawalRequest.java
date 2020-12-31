package com.kevin.gateway.okexapi.fundingaccount.domain;

import com.kevin.gateway.core.Coin;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 提币 request
 */
@Data
@AllArgsConstructor
public class WithdrawalRequest {
    /**
     * 币种
     */
    private Coin currency;
    /**
     * 数量
     */
    private BigDecimal amount;
    /**
     * 认证过的数字货币地址、邮箱或手机号。某些数字货币地址格式为:地址+标签，例：ARDOR-7JF3-8F2E-QUWZ-CAN7F:123456
     */
    private String toAddress;
    /**
     * 交易密码
     */
    private String tradePwd;
    /**
     * 网络手续费≥0，提币到数字货币地址所需网络手续费可通过提币手续费接口查询
     */
    private BigDecimal fee;
    /**
     * 提币到
     * 3:OKEx; 4:数字货币地址; 68:币全CoinAll;89:OKEx Korea;
     * 90:4A交易平台;104:MY1EX;107:BFEX;108:99EX;
     * 113:ETHEX.COM以太坊交易所;116:雷爵爾交易所; 125:PICKCOIN;136:FT交易所;
     * 152:xFutures; 153:Float SV; 158:理想国;161:币爱交易所;
     * 163:BOOMEX; 172:币可富; 173:VVCOIN; 174:Huoblock;
     * 175:JIAMIX; 176:币海; 177:币尚; 178:七十三街交易所
     */
    private Destination destination;

}
