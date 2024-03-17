package com.hello.background.constant;

/**
 * 教育经历
 *
 * @author wuketao
 * @date 2023/3/27
 * @Description
 */
public enum EducationEnum {
    /**
     * 博士
     */
    DOCTOR("DOCTOR"),
    /**
     * 硕士
     */
    MASTER("MASTER"),
    /**
     * 本科
     */
    BACHELOR("BACHELOR"),
    /**
     * 大专
     */
    JUNIORCOLLEGE("JUNIORCOLLEGE");

    /**
     * 描述
     */
    private String describe;

    EducationEnum(String _describe) {
        this.describe = _describe;
    }
}
