package com.kevin.gateway.okexapi.future.websocket.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,property = "margin_mode")
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = AccountFixedData.class, name = "fixed"),
        @JsonSubTypes.Type(value = AccountCrossedData.class, name = "crossed")
})
public interface AccountData {

}
