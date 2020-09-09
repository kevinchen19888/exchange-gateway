package com.alchemy.gateway.core.order;

import com.alchemy.gateway.core.wallet.CursorVo;
import lombok.Data;

import java.util.List;

/**
 * describe:
 *
 * @author zoulingwei
 */
@Data
public class HistoryOrderResult {
    private List<OrderVo> list;
    private CursorVo cursorVo;
}
