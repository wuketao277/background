package com.hello.background.constant;

/**
 * 候选人必要检查
 *
 * @author wuketao
 * @date 2023/3/27
 * @Description
 */
public enum CandidateDoubleCheckEnum {
    /**
     * 简历内容真实准确
     */
    RESUME("RESUME"),
    /**
     * 竞业协议检查
     */
    PROHIBITION("PROHIBITION"),
    /**
     * 候选人确认岗位信息
     */
    CANDIDATECONFIRM("CANDIDATECONFIRM");

    /**
     * 描述
     */
    private String describe;

    CandidateDoubleCheckEnum(String _describe) {
        this.describe = _describe;
    }
}
