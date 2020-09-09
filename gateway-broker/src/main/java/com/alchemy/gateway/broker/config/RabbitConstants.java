package com.alchemy.gateway.broker.config;

public class RabbitConstants {

    public static final String EXCHANGE_MINE_TOPIC = "mine.topic";
    public static final String QUEUE_ACCOUNT_CREATE = "account.create";
    public static final String QUEUE_ACCOUNT_DELETE = "account.delete";
    public static final String QUEUE_ACCOUNT_ENABLE = "account.enable";
    public static final String QUEUE_ACCOUNT_DISABLE = "account.disable";
    public static final String QUEUE_ACCOUNT_SYNC_ASSET = "account.sync_asset";
    public static final String QUEUE_ACCOUNT_RESOLVE_ERROR = "account.resolve_error";

    public static final String QUEUE_ORDER_LIMIT = "order.limit";
    public static final String QUEUE_ORDER_MARKET = "order.market";
    public static final String QUEUE_ORDER_STOPLIMIT = "order.stoplimit";
    public static final String QUEUE_ORDER_CANCEL = "order.cancel";

    public static final String ROUTING_KEY_SEND_PLACE_ORDER = "order.ack";
    public static final String ROUTING_KEY_SEND_CANCEL_ORDER = "cancel.order.ack";
    public static final String ROUTING_KEY_SEND_ORDER_RECORD = "order.deal";
    public static final String ROUTING_KEY_SEND_ORDER_STATUS= "order.status";
    public static final String ROUTING_KEY_SEND_ACCOUNT_ASSET = "account.asset.";
    public static final String ROUTING_KEY_SEND_ACCOUNT_ERROR = "account.error.";
    public static final String ROUTING_KEY_SEND_ACCOUNT_DEPOSIT_WITHDRAW = "account.deposit.withdraw.";
    public static final String ROUTING_KEY_SEND_ACCOUNT_TRANSFER = "account.transfer.";
}