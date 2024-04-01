package com.hello.background.constant;

/**
 * 候选人不匹配原因枚举
 *
 * @author wuketao
 * @date 2022/11/30
 * @Description
 */
public enum CandidateNotMatchReasonEnum {
    NO("NO", "无"),
    LANGUAGE("LANGUAGE", "语言"),
    LOCATION("LOCATION", "地点"),
    PACKAGE("PACKAGE", "薪资"),
    ABILITY("ABILITY", "能力"),
    EDUCATION_BACKGROUND("EDUCATION_BACKGROUND", "学历"),
    AGE("AGE", "年龄"),
    LEVEL("LEVEL", "级别"),
    NOCONSIDER("NOCONSIDER", "不考虑"),
    OTHER("OTHER", "其他");

    private String code;
    private String name;

    CandidateNotMatchReasonEnum(String _code, String _name) {
        this.code = _code;
        this.name = _name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
