package com.hello.background.constant;

/**
 * 角色枚举
 *
 * @author wuketao
 * @date 2022/9/27
 * @Description
 */
public enum RoleEnum {
    RECRUITER("recruiter"),
    AM("Account Manager");

    /**
     * 描述
     */
    private String describe;

    RoleEnum(String _describe) {
        this.describe = _describe;
    }
}
