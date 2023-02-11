package com.hello.background.constant;

/**
 * 工资特殊项类型枚举
 *
 * @author wuketao
 * @date 2023/2/10
 * @Description
 */
public enum SalarySpecialItemTypeEnum {
    SALARY("SALARY", "工资"),
    COMMISSION("COMMISSION", "奖金");

    private String code;
    private String name;

    SalarySpecialItemTypeEnum(String _code, String _name) {
        this.code = _code;
        this.name = _name;
    }
}
