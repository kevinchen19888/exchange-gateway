package com.kevin.gateway.okexapi.base.util;

import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

@Getter
@ToString
public class OkexPagination {
    @Nullable
    private final Long after;
    @Nullable
    private final Long before;
    @Nullable
    private final Integer limit;

    /**
     * @param after  请求此id之前（更旧的数据）的分页内容，传的值为对应接口的order_id、ledger_id或trade_id等
     * @param before 请求此id之后（更新的数据）的分页内容，传的值为对应接口的order_id、ledger_id或trade_id等
     * @param limit  结果集数量(不能超过100)
     * @return 分页对象
     */
    public static OkexPagination of(@Nullable Long after, @Nullable Long before, @Nullable Integer limit) {
        if (limit != null) {
            Assert.isTrue(limit > 0 && limit < 100, "limit 结果集数量不能超过100");
        }
        return new OkexPagination(after, before, limit);
    }

    private OkexPagination(@Nullable Long after, @Nullable Long before, @Nullable Integer limit) {
        this.after = after;
        this.before = before;
        this.limit = limit;
    }

}
