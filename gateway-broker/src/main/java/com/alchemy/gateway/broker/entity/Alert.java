package com.alchemy.gateway.broker.entity;

import com.alchemy.gateway.broker.entity.type.AlertLevelEnum;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 报警表
 */
@Entity
@Table(name = "alert")
@Data
public class Alert {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Long id;

    /**
     * 报警级别
     */
    @Column(name = "level", nullable = false)
    private AlertLevelEnum level;

    /**
     * 报警文本
     */
    @Column(name = "text", nullable = false)
    private String text;

    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * 外键，指向 account.id
     */
    @Column(name = "account_id", nullable = false)
    private Long accountId;

    
}