package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotTimeInForce {

    //gtc - good till cancel (除非用户主动撤销否则一直有效)，
    // boc - book or cancel（即post only，或称book only，除非成功挂单否则自动撤销），
    // ioc - immediate or cancel（立即成交剩余部分自动撤销），
    // fok - fill or kill（立即全部成交否则全部自动撤销）

    GTC("gtc"),
    BOC("boc"),
    IOC("ioc"),
    FOK("fok");

    SpotTimeInForce(String type) {
        this.type = type;
    }

    private final String type;

    @JsonValue
    public String getType() {
        return type;
    }

    @JsonCreator
    public static SpotTimeInForce fromOf(String type) {
        for (SpotTimeInForce okexSpotPlaceType : SpotTimeInForce.values()) {
            if (okexSpotPlaceType.type.equals(type)) {
                return okexSpotPlaceType;
            }
        }
        throw new IllegalArgumentException("无效huobi订单有效期");
    }

}
