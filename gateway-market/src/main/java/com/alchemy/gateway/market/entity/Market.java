package com.alchemy.gateway.market.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "market")
public class Market {
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
     * 币对
     */
    private String coinPair;


    /**
     * 节点
     */
    private String nodeId;

    /**
     * 交易市场类型,如现货交易（spot）市场、永续合约(perpetual)交易市场等
     */
    private String marketType;

    /**
     * 价格精度
     */
    private int pricePrecision;
}
