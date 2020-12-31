package com.kevin.gateway.okexapi.spot.request;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data(staticConstructor = "of")
public final class SpotApiWindow {
    /**
     * 请求此id之前（更旧的数据）的分页内容，传的值为对应接口的id等
     */
    @Nullable
    private final String after;
    /**
     * 请求此id之后（更新的数据）的分页内容，传的值为对应接口的id等
     */
    @Nullable
    private final String before;
    /**
     * 分页返回的结果集数量，最大为100，不填默认返回100条
     */
    @Nullable
    private final String limit;

}
