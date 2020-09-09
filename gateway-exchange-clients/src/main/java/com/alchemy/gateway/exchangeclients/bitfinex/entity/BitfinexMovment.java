package com.alchemy.gateway.exchangeclients.bitfinex.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BitfinexMovment {

    @JsonProperty("MyArray")
    public List<List<Object>> myArray;

}
