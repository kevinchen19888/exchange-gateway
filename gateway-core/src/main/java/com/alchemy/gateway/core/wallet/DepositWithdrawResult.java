package com.alchemy.gateway.core.wallet;

import lombok.Data;

import java.util.List;

/**
 * describe:
 * @author zoulingwei
 */
@Data
public class DepositWithdrawResult {
    private List<DepositWithdrawVo> list;
    private CursorVo cursorVo;
}
