package com.alchemy.gateway.broker.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * describe:资产划转记录
 * @author zoulingwei
 */
@Entity
@Data
@Table(name = "asset_transfer")
public class AssetTransfer {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Long id;

    /**
     * 外键accountId
     */
    @Column(name = "account_id", nullable = false)
    private Long accountId;

    /**
     * 交易所划转记录id
     */
    @Column(name = "exchange_record_id", nullable = false)
    private String exchangeRecordId;

    /**
     * 交易所划转记录类型
     */
    @Column(name = "type", nullable = false)
    private String type;
    /**
     * 划转币种
     */
    @Column(name = "coin", nullable = false)
    private String coin;
    /**
     * 划转数量(转入为正,转出为负)
     */
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    /**
     * 转出目标
     */
    @Column(name = "from_id")
    private String fromId;
    /**
     * 转入目标
     */
    @Column(name = "to_id")
    private String toId;
    /**
     * 交易所划转记录创建时间
     */
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
