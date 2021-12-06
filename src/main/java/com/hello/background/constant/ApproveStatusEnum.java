package com.hello.background.constant;

/**
 * @author wuketao
 * @date 2021/12/5
 * @Description
 */
public enum ApproveStatusEnum {

    APPLY("apply", "申请中"),
    APPROVED("approved", "审批通过"),
    DENIED("denied", "审批否决");

    private String code;
    private String name;

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    ApproveStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
