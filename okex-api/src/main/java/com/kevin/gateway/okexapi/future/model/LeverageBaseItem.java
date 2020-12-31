package com.kevin.gateway.okexapi.future.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "margin_mode")// name属性指定了json序列化&反序列化时多态的判断
@JsonSubTypes({
        @JsonSubTypes.Type(value = LeverageCrossedItem.class, name = "crossed"),
        @JsonSubTypes.Type(value = LeverageFixedItem.class, name = "fixed")

})
public class LeverageBaseItem {
}
