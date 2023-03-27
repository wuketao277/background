package com.hello.background.constant;

/**
 * 候选人必要检查
 *
 * @author wuketao
 * @date 2023/3/27
 * @Description
 */
public enum CandidateDoubleCheckEnum {
    RESUME("RESUME");

    /**
     * 描述
     */
    private String describe;

    CandidateDoubleCheckEnum(String _describe) {
        this.describe = _describe;
    }
}
