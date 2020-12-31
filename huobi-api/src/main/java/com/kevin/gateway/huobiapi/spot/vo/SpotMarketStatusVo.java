package com.kevin.gateway.huobiapi.spot.vo;

import com.kevin.gateway.huobiapi.spot.model.SpotHaltReason;
import com.kevin.gateway.huobiapi.spot.model.SpotMarketStatus;
import lombok.Data;

/**
 * 获取当前市场状态
 */
@Data
public class SpotMarketStatusVo {
    /**
     * 市场状态（1=normal, 2=halted, 3=cancel-only）
     */
    private SpotMarketStatus marketStatus;
    /**
     * 市场暂停开始时间（unix time in millisecond），仅对marketStatus=halted或cancel-only有效
     */
    private long haltStartTime;
    /**
     * 市场暂停预计结束时间（unix time in millisecond），仅对marketStatus=halted或cancel-only有效；
     * 如在marketStatus=halted或cancel-only时未返回此字段，意味着市场暂停结束时间暂时无法预计
     */
    private long haltEndTime;
    /**
     * 市场暂停原因（2=emergency-maintenance, 3=scheduled-maintenance），仅对marketStatus=halted或cancel-only有效
     */
    private SpotHaltReason haltReason;
    /**
     * 市场暂停影响的交易对列表，以逗号分隔，如影响所有交易对返回"all"，仅对marketStatus=halted或cancel-only有效
     */
    private String affectedSymbols;
}
