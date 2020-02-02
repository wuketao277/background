package com.hello.background.constant;

/**
 * @author wuketao
 * @date 2020/2/2
 * @Description
 */
public enum CandidateForCaseStatusEnum {
    PREPARE("prepare", "准备"),
    FIRST_INTERVIEW("first interview", "一面"),
    SECOND_INTERVIEW("second interview", "二面"),
    THIRD_INTERVIEW("third interview", "三面"),
    FINALLY_INTERVIEW("finally interview", "终面");

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    private CandidateForCaseStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
