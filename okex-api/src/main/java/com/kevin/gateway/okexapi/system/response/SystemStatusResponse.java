package com.kevin.gateway.okexapi.system.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SystemStatusResponse {

    /**
     * 系统维护说明的标题
     */
    private String title;

    /**
     * 系统维护的状态 0:等待中; 1:进行中; 2:已完成
     */
    private SystemStatus status;

    /**
     * 系统维护的开始时间,格式为ISO 8601
     * 标准格式 如:2020-04-03T16:30:00.000Z
     */
    private LocalDateTime startTime;

    /**
     * 系统维护的结束时间,格式为ISO 8601
     * 标准格式 如:2020-04-03T17:40:00.000Z
     */
    private LocalDateTime endTime;

    /**
     * 系统维护详情的超级链接,若无返回值，默认值为空
     * 如 href: “”
     */
    private String href;

    /**
     * 产品类型 0:WebSocket; 1:币币/杠杆; 2:交割; 3:永续; 4:期权
     */
    private String product_type;

}
