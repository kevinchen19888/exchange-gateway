package com.kevin.gateway.huobiapi.spot.request;

import com.kevin.gateway.huobiapi.spot.model.Sort;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 分页信息查询
 */
@Data(staticConstructor = "of")
public final class SpotApiWindow {
    @JsonProperty(value = "start-time")
    private final Long startTime;//远点时间 unix time in millisecond. 以transact-time为key进行检索. 查询窗口最大为1小时. 窗口平移范围为最近30天.

    @JsonProperty(value = "end-time")
    private final Long endTime;//近点时间unix time in millisecond. 以transact-time为key进行检索. 查询窗口最大为1小时. 窗口平移范围为最近30天.

    private final Sort sort;//检索方向

    private final Integer size;//最大条目数量

    @JsonProperty(value = "from-id")
    private final Long fromId;//起始编号（仅在下页查询时有效）

}
