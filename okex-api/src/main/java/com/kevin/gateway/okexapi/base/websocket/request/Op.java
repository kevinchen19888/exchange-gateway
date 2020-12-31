package com.kevin.gateway.okexapi.base.websocket.request;

public enum Op {
    SUBSCRIBE("subscribe"),
    UNSUBSCRIBE("unsubscribe"),
    LOGIN("login");

    private final String value;

    Op(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
