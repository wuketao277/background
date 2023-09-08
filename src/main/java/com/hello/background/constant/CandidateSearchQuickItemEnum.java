package com.hello.background.constant;

/**
 * 候选人搜索快捷项
 *
 * @author wuketao
 * @date 2023/3/27
 * @Description
 */
public enum CandidateSearchQuickItemEnum {
    /**
     * 电话不为空
     */
    HAVETELEPHONE("HAVETELEPHONE");

    /**
     * 描述
     */
    private String describe;

    CandidateSearchQuickItemEnum(String _describe) {
        this.describe = _describe;
    }
}
