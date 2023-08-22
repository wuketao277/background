package com.hello.background.constant;

/**
 * 性别枚举
 *
 * @author wuketao
 * @date 2022/9/14
 * @Description
 */
public enum GenderEnum {
    MALE("MALE", "男"),
    FEMALE("FEMALE", "女");
    /**
     * 代码
     */
    private String code;
    /**
     * 描述
     */
    private String describe;

    GenderEnum(String _code, String _describe) {
        this.code = _code;
        this.describe = _describe;
    }

    public String getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }
}
