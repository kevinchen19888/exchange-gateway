package com.alchemy.gateway.core.wallet;

import lombok.Builder;
import lombok.Data;

/**
 * describe:游标
 *
 * @author zoulingwei
 */
@Builder
@Data
public class CursorVo {
    /**
     * 记录id
     */
    private String recordId;
    /**
     * 时间
     */
    private Long time;
}
