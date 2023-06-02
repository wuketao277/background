package com.hello.background.constant;

/**
 * 候选人特殊项
 *
 * @author wuketao
 * @date 2023/3/27
 * @Description
 */
public enum CandidateSpecialItemEnum {
    /**
     * 海外留学
     */
    OVERSEASTUDENT("OVERSEASTUDENT"),
    /**
     * 海外工作
     */
    OVERSEAASSIGNMENT("OVERSEAASSIGNMENT");

    /**
     * 描述
     */
    private String describe;

    CandidateSpecialItemEnum(String _describe) {
        this.describe = _describe;
    }
}
