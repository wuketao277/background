package com.hello.background.constant;

/**
 * 是否枚举
 *
 * @author wuketao
 * @date 2022/10/11
 * @Description
 */
public enum YesOrNoEnum {
    YES("是"),
    NO("否");

    private String name;

    public String getName() {
        return this.name;
    }

    YesOrNoEnum(String _name) {
        this.name = _name;
    }
}
