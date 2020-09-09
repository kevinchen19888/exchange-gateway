create TABLE `account` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` varchar(255) NOT NULL COMMENT '平台投资者账号 id',
  `status` int NOT NULL COMMENT '账户状态, 1: 启用, 2: 禁用, 3: 资产异常, 4: 已删除',
  `create_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_id_UNIQUE` (`account_id`)
) COMMENT='账户表';


create TABLE `account_asset` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `coin` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '币种',
  `balance` decimal(19,8) NOT NULL DEFAULT '0.00000000' COMMENT '余额',
  `frozen` decimal(19,8) NOT NULL DEFAULT '0.00000000' COMMENT '冻结资产',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime NOT NULL COMMENT '更新时间',
--   `asset_updated_at` datetime NOT NULL COMMENT '资产校验更新时间',
  `account_id` bigint unsigned NOT NULL COMMENT '外键指向 account.id 字段',
  PRIMARY KEY (`id`)
) COMMENT='账户资产表';


create TABLE `alert` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `level` int NOT NULL COMMENT '报警级别',
  `text` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '报警文本',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `account_id` bigint unsigned NOT NULL COMMENT '外键，指向 account.id',
  PRIMARY KEY (`id`)
) COMMENT='报警表';


create TABLE `entrust_order` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mine_order_id` bigint NOT NULL COMMENT '实盘平台的订单 ID',
  `symbol` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '币对',
  `type` int NOT NULL COMMENT '订单类型,  0: 市价, 1: 限价, 2: 止损限价',
  `price` decimal(19,8) COMMENT '价格',
  `amount` decimal(19,8) NOT NULL COMMENT '数量或者金额: 市价单时为市价单币种的交易额, 限价单时为数量',
  `side` int NOT NULL COMMENT '交易方向, 0: 买, 1:卖',
  `state` int NOT NULL COMMENT '订单状态, 0: 提交中, 1: 提交失败(网络超时,key无效,余额不足), 2: 已创建订单, 3: 部分成交, 4: 完成, 5: 撤销中, 6: 已撤销',
  `stop_price` decimal(19,8) DEFAULT NULL COMMENT '止损价格（仅在止损订单有效，非止损订单时为 NULL）',
  `exchange_order_id` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '交易所内订单 id（交易所接口提交失败时为 NULL）',
  `finished_amount` decimal(19,8) NOT NULL DEFAULT '0.00000000' COMMENT '已完成金额',
  `finished_fee` decimal(19,8) NOT NULL DEFAULT '0.00000000' COMMENT '已完成手续费',
  `finished_volume` decimal(19,8) NOT NULL DEFAULT '0.00000000' COMMENT '已完成成交量',
  `rebate_coin` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '返佣费用币种（没有返佣时为 NULL）',
  `rebate` decimal(19,8) DEFAULT NULL COMMENT '返佣金额',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime NOT NULL COMMENT '更新时间',
  `finished_at` datetime NOT NULL COMMENT '完成时间',
  `account_id` bigint unsigned NOT NULL COMMENT '指向账户表的外键 account.id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mine_order_id_UNIQUE` (`mine_order_id`)
) COMMENT='订单表';


create TABLE `trade` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `exchange_order_id` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '交易所订单 ID',
  `exchange_name` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '交易所名称, 比如: binance, okex',
  `exchange_trade_id` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '交易所成交记录 ID',
  `filled_amount` decimal(19,8) NOT NULL COMMENT '成交额',
  `filled_fee` decimal(19,8) NOT NULL DEFAULT '0.00000000' COMMENT '本笔成交手续费',
  `filled_fee_coin` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手续费用币种（没有手续费时为 NULL）',
  `price` decimal(19,8) NOT NULL COMMENT '成交价格',
  `role` int NOT NULL COMMENT '成交角色（0: maker, 1: taker）',
  `fee_deduct_coin` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '抵扣费用币种（没有手续费时为 NULL）',
  `fee_deduct_amount` decimal(19,8) DEFAULT NULL COMMENT '抵扣金额（抵扣币种金额、抵扣点卡等）',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `order_id` bigint unsigned NOT NULL COMMENT '外键, 指向 order.id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique-exchange-trade-id` (`exchange_name`,`exchange_trade_id`) COMMENT '交易所内成交记录 ID 唯一'
) COMMENT='成交记录表';

create TABLE `asset_cursor` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` bigint NOT NULL COMMENT '外键, 指向 account.id',
  `type` int NOT NULL COMMENT '游标类型（1: DEPOSIT_WITHDRAW, 1: ASSET_TRANSFER）',
  `record_id` varchar(64) COLLATE utf8mb4_general_ci COMMENT '记录id',
  `time` bigint COMMENT '记录时间',
  PRIMARY KEY (`id`)
) COMMENT='游标表';

create TABLE `deposit_withdraw` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` bigint NOT NULL COMMENT '外键, 指向 account.id',
  `exchange_record_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '交易所充提记录id',
  `type` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '交易所充提记录类型',
  `coin` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '充提币种',
  `amount` decimal(19,8) NOT NULL COMMENT '充提数量',
  `fee` decimal(19,8) NOT NULL DEFAULT 0 COMMENT '充提手续费',
  `state` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '交易所充提记录状态',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) COMMENT='充提记录表';

create TABLE `asset_transfer` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` bigint NOT NULL COMMENT '外键, 指向 account.id',
  `exchange_record_id` varchar(32) NOT NULL COMMENT '交易所划转记录id',
  `type` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '交易所划转记录类型',
  `coin` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '划转币种',
  `amount` decimal(19,8) NOT NULL COMMENT '划转数量(转入为正,转出为负)',
  `from_id` varchar(32) COLLATE utf8mb4_general_ci COMMENT '转出目标',
  `to_id` varchar(32) COLLATE utf8mb4_general_ci COMMENT '转入目标',
  `created_at` datetime NOT NULL COMMENT '交易所划转记录创建时间',
  PRIMARY KEY (`id`)
) COMMENT='资产划转记录表';
