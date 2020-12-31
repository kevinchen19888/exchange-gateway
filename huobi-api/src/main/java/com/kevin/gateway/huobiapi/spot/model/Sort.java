package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Sort {
    ASC("asc"),
    DESC("desc");

    Sort(String sort) {
        this.sort = sort;
    }

    private final String sort;

    @JsonValue
    public String getSort() {
        return sort;
    }

    @JsonCreator
    public static Sort fromOf(String sort) {
        for (Sort type : Sort.values()) {
            if (type.sort.equals(sort)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币检索方向");
    }
}
