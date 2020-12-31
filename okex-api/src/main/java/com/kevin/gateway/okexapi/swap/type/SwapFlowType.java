package com.kevin.gateway.okexapi.swap.type;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 1. 开多
 * 2. 开空
 * 3. 平多
 * 4. 平空
 * 5.转入
 * 6.转出
 * 7.清算未实现
 * 8.分摊
 * 9.剩余拨付
 * 10.强平多
 * 11.强平空
 * 14.资金费
 * 15.手动追加
 * 16.手动减少
 * 17.自动追加
 * 18.修改持仓模式
 * 19.强减多
 * 20.强减空
 * 21.用户调低杠杆追加保证金
 * 22.清算已实现
 */
public enum SwapFlowType {

    OPEN_LONG(1, "开多"),
    OPEN_SHORT(2, "开空"),
    CLOSE_LONG(3, "平多"),
    CLOSE_SHORT(4, "平空"),
    TRANSFER_INTO(5, "转入"),
    TRANSFER_OUT(6, "转出"),
    CLEARING_UNREAL(7, "清算未实现"),
    SHARE(8, "分摊"),
    REST(9, "剩余拨付"),
    FORCE_LONG(10, "强平多"),
    FORCE_SHORT(11, "强平空"),
    FEE(14, "资金费"),
    MANUAL_ADD(15, "手动追加"),
    MANUAL_SUB(16, "手动减少"),
    AUTO_ADD(17, "自动追加"),
    MODIFY_POSITION(18, "修改持仓模式"),
    FORCE_SUB_LONG(19, "强减多"),
    FORCE__SUB_SHORT(20, "强减空"),
    SET_LEVERAGE(21, "用户调低杠杆追加保证金"),
    CLEARING_REAL(22, "清算已实现");


    private final Integer value;

    private final String name;

    SwapFlowType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @JsonCreator
    public static SwapFlowType fromValue(String value) {
        for (SwapFlowType type : SwapFlowType.values()) {
            if (type.value.equals(Integer.valueOf(value))) {
                return type;
            }
        }
        throw new IllegalArgumentException("SwapFlowType 无效 ,value= " + value);
    }

    @JsonValue
    public Integer getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }


}
