package com.alchemy.gateway.broker.entity;

import com.alchemy.gateway.broker.entity.type.AssetCursorType;
import lombok.Data;

import javax.persistence.*;

/**
 * describe:游标
 *
 * @author zoulingwei
 */
@Entity
@Data
@Table(name = "asset_cursor")
public class AssetCursor {
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
     * 游标类型
     */
    @Column(name = "type", nullable = false)
    private AssetCursorType type;
    /**
     * 记录id
     */
    @Column(name = "record_id")
    private String recordId;
    /**
     * 记录时间时间戳
     */
    @Column(name = "time")
    private Long time;
}
