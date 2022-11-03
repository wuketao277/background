package com.hello.background.constant;

/**
 * 是否报销枚举
 *
 * @author wuketao
 * @date 2022/10/11
 * @Description
 */
public enum ReimbursementNeedPayEnum {
    YES("是"),
    NO("否"),
    BANK("银行");

    private String name;

    public String getName() {
        return this.name;
    }

    ReimbursementNeedPayEnum(String _name) {
        this.name = _name;
    }
}
