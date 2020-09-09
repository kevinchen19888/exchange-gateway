package com.alchemy.gateway.broker.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账户资产表
 */
@Data
@Entity
@Table(name = "account_asset")
public class AccountAsset {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Long id;

    /**
     * 币种
     */
    @Column(name = "coin", nullable = false)
    private String coin;

    /**
     * 余额
     */
    @Column(name = "balance", nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    /**
     * 冻结资产
     */
    @Column(name = "frozen", nullable = false)
    private BigDecimal frozen = BigDecimal.ZERO;

    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;



    /**
     * 外键指向 account.id 字段
     */
    @Column(name = "account_id", nullable = false)
    private Long accountId;

    
}