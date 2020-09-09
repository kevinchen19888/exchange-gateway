package com.alchemy.gateway.broker.entity;

import com.alchemy.gateway.broker.entity.type.AccountStatusEnum;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 账户表
 */
@Data
@Table(name = "account")
@Entity
public class Account {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Long id;

    /**
     * 平台投资者账号 id
     */
    @Column(name = "account_id", nullable = false)
    private String accountId;

    /**
     * 账户状态, 9: 启用, 1: 禁用, 2: 资产异常, 3: 已删除
     */
    @Column(name = "status", nullable = false)
    private AccountStatusEnum status;

    /**
     * 创建时间
     */
    @Column(name = "create_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

//    /**
//     * 资产校验更新时间
//     */
//    @Column(name = "asset_updated_at", nullable = false)
//    private LocalDateTime assetUpdatedAt;

    
}