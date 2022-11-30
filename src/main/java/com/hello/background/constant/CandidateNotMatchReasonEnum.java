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
    ABILITY("ABILITY", "能力");

    private String code;
    private String name;

    CandidateNotMatchReasonEnum(String _code, String _name) {
        this.code = _code;
        this.name = _name;
    }
}
