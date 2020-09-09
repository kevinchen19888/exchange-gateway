package com.alchemy.gateway.core.wallet;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * describe:资产划转
 *
 * @author zoulingwei
 */
@Data
public class AssetTransferVo {
//    /**
//     * 平台accountId
//     */
//    private Long accountId;

    /**
     * 交易所划转记录id
     */
    private String exchangeRecordId;

    /**
     * 交易所划转记录类型
     */
    private String type;
    /**
     * 划转币种
     */
    private String coin;
    /**
     * 划转数量(转入为正,转出为负)
     */
    private BigDecimal amount;
    /**
     * 转出目标
     */
    private String from;
    /**
     * 转入目标
     */
    private String to;
    /**
     * 交易所划转记录创建时间
     */
    private LocalDateTime createdAt;
}
