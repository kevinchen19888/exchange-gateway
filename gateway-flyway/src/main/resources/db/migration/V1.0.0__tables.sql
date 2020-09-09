-- ----------------------------
-- Table structure for market
-- ----------------------------
CREATE TABLE `market`  (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '市场id',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `market_type` varchar(20) NOT NULL COMMENT '市场类型',
  `coin_pair` varchar(20) NOT NULL COMMENT '币对名',
  `node_id` varchar(20) DEFAULT NULL COMMENT '分配的行情处理节点名',
  `price_precision` int NOT NULL COMMENT '价格精度',
  PRIMARY KEY (`id`)
) comment '交易所市场信息表';

-- ----------------------------
-- Table structure for kline_12h
-- ----------------------------
CREATE TABLE `kline_12h`  (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'K线id',
  `close_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '收盘价',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `high_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最高价',
  `instrument_id` varchar(20) NOT NULL COMMENT '币对名',
  `low_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最低价',
  `open_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '开盘价',
  `reserve` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `time_stamp` datetime NOT NULL COMMENT '开始时间',
  `volume` decimal(20, 8) DEFAULT 0.00000000 COMMENT '交易量',
  PRIMARY KEY (`id`)
) comment '12小时K线表';

-- ----------------------------
-- Table structure for kline_14d
-- ----------------------------
CREATE TABLE `kline_14d`  (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'K线id',
  `close_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '收盘价',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `high_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最高价',
  `instrument_id` varchar(20) NOT NULL COMMENT '币对名',
  `low_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最低价',
  `open_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '开盘价',
  `reserve` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `time_stamp` datetime NOT NULL COMMENT '开始时间',
  `volume` decimal(20, 8) DEFAULT 0.00000000 COMMENT '交易量',
  PRIMARY KEY (`id`)
) comment '14天K线表';

-- ----------------------------
-- Table structure for kline_15m
-- ----------------------------
CREATE TABLE `kline_15m`  (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'K线id',
  `close_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '收盘价',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `high_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最高价',
  `instrument_id` varchar(20) NOT NULL COMMENT '币对名',
  `low_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最低价',
  `open_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '开盘价',
  `reserve` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `time_stamp` datetime NOT NULL COMMENT '开始时间',
  `volume` decimal(20, 8) DEFAULT 0.00000000 COMMENT '交易量',
  PRIMARY KEY (`id`)
) comment '15分钟K线表';

-- ----------------------------
-- Table structure for kline_1d
-- ----------------------------
CREATE TABLE `kline_1d`  (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'K线id',
  `close_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '收盘价',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `high_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最高价',
  `instrument_id` varchar(20) NOT NULL COMMENT '币对名',
  `low_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最低价',
  `open_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '开盘价',
  `reserve` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `time_stamp` datetime NOT NULL COMMENT '开始时间',
  `volume` decimal(20, 8) DEFAULT 0.00000000 COMMENT '交易量',
  PRIMARY KEY (`id`)
) comment '1天K线表';

-- ----------------------------
-- Table structure for kline_1h
-- ----------------------------
CREATE TABLE `kline_1h`  (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'K线id',
  `close_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '收盘价',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `high_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最高价',
  `instrument_id` varchar(20) NOT NULL COMMENT '币对名',
  `low_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最低价',
  `open_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '开盘价',
  `reserve` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `time_stamp` datetime NOT NULL COMMENT '开始时间',
  `volume` decimal(20, 8) DEFAULT 0.00000000 COMMENT '交易量',
  PRIMARY KEY (`id`)
)comment '1小时K线表';

-- ----------------------------
-- Table structure for kline_1m
-- ----------------------------
CREATE TABLE `kline_1m`  (
 `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'K线id',
  `close_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '收盘价',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `high_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最高价',
  `instrument_id` varchar(20) NOT NULL COMMENT '币对名',
  `low_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最低价',
  `open_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '开盘价',
  `reserve` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `time_stamp` datetime NOT NULL COMMENT '开始时间',
  `volume` decimal(20, 8) DEFAULT 0.00000000 COMMENT '交易量',
  PRIMARY KEY (`id`)
) comment '1分钟K线表';

-- ----------------------------
-- Table structure for kline_1mon
-- ----------------------------
CREATE TABLE `kline_1mon`  (
 `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'K线id',
  `close_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '收盘价',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `high_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最高价',
  `instrument_id` varchar(20) NOT NULL COMMENT '币对名',
  `low_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最低价',
  `open_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '开盘价',
  `reserve` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `time_stamp` datetime NOT NULL COMMENT '开始时间',
  `volume` decimal(20, 8) DEFAULT 0.00000000 COMMENT '交易量',
  PRIMARY KEY (`id`)
) comment '1个月K线表';

-- ----------------------------
-- Table structure for kline_1y
-- ----------------------------
CREATE TABLE `kline_1y`  (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'K线id',
  `close_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '收盘价',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `high_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最高价',
  `instrument_id` varchar(20) NOT NULL COMMENT '币对名',
  `low_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最低价',
  `open_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '开盘价',
  `reserve` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `time_stamp` datetime NOT NULL COMMENT '开始时间',
  `volume` decimal(20, 8) DEFAULT 0.00000000 COMMENT '交易量',
  PRIMARY KEY (`id`)
) comment '1年K线表';

-- ----------------------------
-- Table structure for kline_2h
-- ----------------------------
CREATE TABLE `kline_2h`  (
 `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'K线id',
  `close_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '收盘价',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `high_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最高价',
  `instrument_id` varchar(20) NOT NULL COMMENT '币对名',
  `low_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最低价',
  `open_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '开盘价',
  `reserve` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `time_stamp` datetime NOT NULL COMMENT '开始时间',
  `volume` decimal(20, 8) DEFAULT 0.00000000 COMMENT '交易量',
  PRIMARY KEY (`id`)
) comment '2小时K线表';

-- ----------------------------
-- Table structure for kline_30m
-- ----------------------------
CREATE TABLE `kline_3d`  (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'K线id',
  `close_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '收盘价',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `high_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最高价',
  `instrument_id` varchar(20) NOT NULL COMMENT '币对名',
  `low_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最低价',
  `open_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '开盘价',
  `reserve` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `time_stamp` datetime NOT NULL COMMENT '开始时间',
  `volume` decimal(20, 8) DEFAULT 0.00000000 COMMENT '交易量',
  PRIMARY KEY (`id`)
)comment '3天K线表';

-- ----------------------------
-- Table structure for kline_30m
-- ----------------------------
CREATE TABLE `kline_30m`  (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'K线id',
  `close_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '收盘价',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `high_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最高价',
  `instrument_id` varchar(20) NOT NULL COMMENT '币对名',
  `low_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最低价',
  `open_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '开盘价',
  `reserve` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `time_stamp` datetime NOT NULL COMMENT '开始时间',
  `volume` decimal(20, 8) DEFAULT 0.00000000 COMMENT '交易量',
  PRIMARY KEY (`id`)
)comment '30分钟K线表';

-- ----------------------------
-- Table structure for kline_3h
-- ----------------------------
CREATE TABLE `kline_3h`  (
 `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'K线id',
  `close_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '收盘价',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `high_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最高价',
  `instrument_id` varchar(20) NOT NULL COMMENT '币对名',
  `low_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最低价',
  `open_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '开盘价',
  `reserve` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `time_stamp` datetime NOT NULL COMMENT '开始时间',
  `volume` decimal(20, 8) DEFAULT 0.00000000 COMMENT '交易量',
  PRIMARY KEY (`id`)
)comment '3小时K线表';

-- ----------------------------
-- Table structure for kline_3m
-- ----------------------------
CREATE TABLE `kline_3m`  (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'K线id',
  `close_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '收盘价',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `high_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最高价',
  `instrument_id` varchar(20) NOT NULL COMMENT '币对名',
  `low_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最低价',
  `open_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '开盘价',
  `reserve` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `time_stamp` datetime NOT NULL COMMENT '开始时间',
  `volume` decimal(20, 8) DEFAULT 0.00000000 COMMENT '交易量',
  PRIMARY KEY (`id`)
) comment '3分钟K线表';

-- ----------------------------
-- Table structure for kline_3mon
-- ----------------------------
CREATE TABLE `kline_3mon`  (
 `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'K线id',
  `close_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '收盘价',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `high_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最高价',
  `instrument_id` varchar(20) NOT NULL COMMENT '币对名',
  `low_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最低价',
  `open_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '开盘价',
  `reserve` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `time_stamp` datetime NOT NULL COMMENT '开始时间',
  `volume` decimal(20, 8) DEFAULT 0.00000000 COMMENT '交易量',
  PRIMARY KEY (`id`)
) comment '3个月K线表';

-- ----------------------------
-- Table structure for kline_4h
-- ----------------------------
CREATE TABLE `kline_4h`  (
 `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'K线id',
  `close_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '收盘价',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `high_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最高价',
  `instrument_id` varchar(20) NOT NULL COMMENT '币对名',
  `low_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最低价',
  `open_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '开盘价',
  `reserve` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `time_stamp` datetime NOT NULL COMMENT '开始时间',
  `volume` decimal(20, 8) DEFAULT 0.00000000 COMMENT '交易量',
  PRIMARY KEY (`id`)
)comment '4小时K线表';

-- ----------------------------
-- Table structure for kline_5m
-- ----------------------------
CREATE TABLE `kline_5m`  (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'K线id',
  `close_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '收盘价',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `high_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最高价',
  `instrument_id` varchar(20) NOT NULL COMMENT '币对名',
  `low_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最低价',
  `open_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '开盘价',
  `reserve` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `time_stamp` datetime NOT NULL COMMENT '开始时间',
  `volume` decimal(20, 8) DEFAULT 0.00000000 COMMENT '交易量',
  PRIMARY KEY (`id`)
)comment '5分钟K线表';

-- ----------------------------
-- Table structure for kline_6h
-- ----------------------------
CREATE TABLE `kline_6h`  (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'K线id',
  `close_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '收盘价',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `high_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最高价',
  `instrument_id` varchar(20) NOT NULL COMMENT '币对名',
  `low_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最低价',
  `open_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '开盘价',
  `reserve` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `time_stamp` datetime NOT NULL COMMENT '开始时间',
  `volume` decimal(20, 8) DEFAULT 0.00000000 COMMENT '交易量',
  PRIMARY KEY (`id`)
)comment '6小时K线表';

-- ----------------------------
-- Table structure for kline_6mon
-- ----------------------------
CREATE TABLE `kline_6mon`  (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'K线id',
  `close_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '收盘价',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `high_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最高价',
  `instrument_id` varchar(20) NOT NULL COMMENT '币对名',
  `low_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最低价',
  `open_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '开盘价',
  `reserve` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `time_stamp` datetime NOT NULL COMMENT '开始时间',
  `volume` decimal(20, 8) DEFAULT 0.00000000 COMMENT '交易量',
  PRIMARY KEY (`id`)
) comment '6个月K线表';

-- ----------------------------
-- Table structure for kline_7d
-- ----------------------------
CREATE TABLE `kline_7d`  (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'K线id',
  `close_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '收盘价',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `high_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最高价',
  `instrument_id` varchar(20) NOT NULL COMMENT '币对名',
  `low_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最低价',
  `open_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '开盘价',
  `reserve` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `time_stamp` datetime NOT NULL COMMENT '开始时间',
  `volume` decimal(20, 8) DEFAULT 0.00000000 COMMENT '交易量',
  PRIMARY KEY (`id`)
) comment '7天K线表';

-- ----------------------------
-- Table structure for kline_8h
-- ----------------------------
CREATE TABLE `kline_8h`  (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'K线id',
  `close_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '收盘价',
  `exchange_name` varchar(20) NOT NULL COMMENT '交易所名称',
  `high_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最高价',
  `instrument_id` varchar(20) NOT NULL COMMENT '币对名',
  `low_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '最低价',
  `open_price` decimal(20, 8) DEFAULT 0.00000000 COMMENT '开盘价',
  `reserve` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `time_stamp` datetime NOT NULL COMMENT '开始时间',
  `volume` decimal(20, 8) DEFAULT 0.00000000 COMMENT '交易量',
  PRIMARY KEY (`id`)
) comment '8小时K线表';


ALTER TABLE `kline_1m` ADD UNIQUE INDEX (`exchange_name`, `instrument_id`, `time_stamp`);
ALTER TABLE `kline_3m` ADD UNIQUE INDEX (`exchange_name`, `instrument_id`, `time_stamp`);
ALTER TABLE `kline_5m` ADD UNIQUE INDEX (`exchange_name`, `instrument_id`, `time_stamp`);
ALTER TABLE `kline_15m` ADD UNIQUE INDEX (`exchange_name`, `instrument_id`, `time_stamp`);
ALTER TABLE `kline_30m` ADD UNIQUE INDEX (`exchange_name`, `instrument_id`, `time_stamp`);
ALTER TABLE `kline_1h` ADD UNIQUE INDEX (`exchange_name`, `instrument_id`, `time_stamp`);
ALTER TABLE `kline_2h` ADD UNIQUE INDEX (`exchange_name`, `instrument_id`, `time_stamp`);
ALTER TABLE `kline_3h` ADD UNIQUE INDEX (`exchange_name`, `instrument_id`, `time_stamp`);
ALTER TABLE `kline_4h` ADD UNIQUE INDEX (`exchange_name`, `instrument_id`, `time_stamp`);
ALTER TABLE `kline_6h` ADD UNIQUE INDEX (`exchange_name`, `instrument_id`, `time_stamp`);
ALTER TABLE `kline_8h` ADD UNIQUE INDEX (`exchange_name`, `instrument_id`, `time_stamp`);
ALTER TABLE `kline_12h` ADD UNIQUE INDEX (`exchange_name`, `instrument_id`, `time_stamp`);
ALTER TABLE `kline_1d` ADD UNIQUE INDEX (`exchange_name`, `instrument_id`, `time_stamp`);
ALTER TABLE `kline_3d` ADD UNIQUE INDEX (`exchange_name`, `instrument_id`, `time_stamp`);
ALTER TABLE `kline_7d` ADD UNIQUE INDEX (`exchange_name`, `instrument_id`, `time_stamp`);
ALTER TABLE `kline_14d` ADD UNIQUE INDEX (`exchange_name`, `instrument_id`, `time_stamp`);
ALTER TABLE `kline_1mon` ADD UNIQUE INDEX (`exchange_name`, `instrument_id`, `time_stamp`);
ALTER TABLE `kline_3mon` ADD UNIQUE INDEX (`exchange_name`, `instrument_id`, `time_stamp`);
ALTER TABLE `kline_6mon` ADD UNIQUE INDEX (`exchange_name`, `instrument_id`, `time_stamp`);
ALTER TABLE `kline_1y` ADD UNIQUE INDEX (`exchange_name`, `instrument_id`, `time_stamp`);