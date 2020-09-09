package com.alchemy.gateway.core.wallet;

import lombok.Data;

import java.util.List;

/**
 * describe:
 * @author zoulingwei
 */
@Data
public class AssetTransferResult {
    private List<AssetTransferVo> list;
    private CursorVo cursorVo;
}
