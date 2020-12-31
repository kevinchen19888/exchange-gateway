package com.kevin.gateway.okexapi.future.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "margin_mode")// name属性指定了json序列化&反序列化时多态的判断
@JsonSubTypes({
        @JsonSubTypes.Type(value = SetLeverageBodyResponseCrossed.class, name = "crossed"),
        @JsonSubTypes.Type(value = SetLeverageBodyResponseFixed.class, name = "fixed")

})

@Data
public class SetLeverageBodyResponseBase {


    /**
     * 全仓、逐仓参数 : 返回设定结果，成功或错误码
     */
    private boolean result;


}

