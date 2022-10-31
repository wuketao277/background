package com.hello.background.constant;

/**
 * 报销类别枚举
 *
 * @author wuketao
 * @date 2022/10/11
 * @Description
 */
public enum ReimbursementTypeEnum {
    Transportation("交通"),
    Travel("差旅"),
    Communication("通讯"),
    Office("办公"),
    Service("服务"),
    Recruit("招聘"),
    Other("其他");
    private String name;

    public String getName() {
        return this.name;
    }

    ReimbursementTypeEnum(String _name) {
        this.name = _name;
    }
}
