package com.alchemy.gateway.quotation.entity;

import lombok.Data;
import lombok.Value;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@DynamicInsert
@DynamicUpdate
@MappedSuperclass
public class Kline {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 交易所名称
     */
    private String exchangeName;
    /**
     * 币对名称
     */
    private String instrumentId;
    /**
     * 开始时间
     */
    private LocalDateTime timeStamp;
    /**
     * 最高价
     */
    @Column(name = "high_price")
    private BigDecimal high;
    /**
     * 开盘价
     */
    @Column(name = "open_price")
    private BigDecimal open;
    /**
     * 最低价
     */
    @Column(name = "low_price")
    private BigDecimal low;
    /**
     * 收盘价
     */
    @Column(name = "close_price")
    private BigDecimal close;
    /**
     * 交易量
     */
    private BigDecimal volume;
    /**
     * 备用字段，各交易所特色字段存放；格式如下：amount：0.56481，count：0
     */
    private String reserve;

}
