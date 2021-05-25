CREATE TABLE `cmc_symbols_quotation`
(
    `id`                 bigint(21) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `symbol`             varchar(64)     NOT NULL COMMENT '币种',
    `price`              decimal(36, 18) NOT NULL COMMENT '价格(默认usdt计价)',
    `percent_change_1h`  decimal(36, 18) NOT NULL COMMENT '1h币价变化百分比',
    `percent_change_24h` decimal(36, 18) NOT NULL COMMENT '24h币价变化百分比',
    `percent_change_7d`  decimal(36, 18) NOT NULL COMMENT '7天币价变化百分比',
    `market_cap`         decimal(36, 18) NOT NULL COMMENT '市值',
    `volume24h`          decimal(36, 18) NOT NULL COMMENT '24h交易量',
    `circulating_supply` decimal(36, 18) NOT NULL COMMENT '流通供应量',
    `max_supply`         decimal(36, 18) NOT NULL COMMENT '最大供应量',
    `total_supply`       decimal(36, 18) NOT NULL COMMENT '总供应量',
    `cmc_rank`           int(8) NOT NULL COMMENT 'cmc评级',
    `num_market_pairs`   int(8) NOT NULL COMMENT '上币的交易所数量',
    `date_added`         datetime        NOT NULL COMMENT '首次上系统时间',
    `last_updated`       datetime        NOT NULL COMMENT '最后更新时间',
    `create_time`        datetime        NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'cmc 币种行情信息表' ROW_FORMAT = Dynamic;


-- 交易所币种行情信息表
CREATE TABLE `cmc_exchange_symbols_quotation`
(
    `id`                 bigint(21) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `exchange`           varchar(32)     NOT NULL COMMENT '来源',
    `symbol`             varchar(64)     NOT NULL COMMENT '币种',
    `price`              decimal(36, 18) NOT NULL COMMENT '价格(默认usdt计价)',
    `percent_change_7d`  decimal(36, 18) NOT NULL COMMENT '7天币价变化百分比',
    `market_cap`         decimal(36, 18) NOT NULL COMMENT '市值',
    `volume24h`          decimal(36, 18) NOT NULL COMMENT '24h交易量',
    `circulating_supply` decimal(36, 18) NOT NULL COMMENT '流通供应量',
    `max_supply`         decimal(36, 18) NOT NULL COMMENT '最大供应量',
    `total_supply`       decimal(36, 18) NOT NULL COMMENT '总供应量',
    `cmc_rank`           int(8) NOT NULL COMMENT 'cmc评级',
    `num_market_pairs`   int(8) NOT NULL COMMENT '上币的交易所数量',
    `date_added`         datetime        NOT NULL COMMENT '首次上系统时间',
    `last_updated`       datetime        NOT NULL COMMENT '最后更新时间',
    `create_time`        datetime        NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'cmc 币种行情信息表' ROW_FORMAT = Dynamic;















