package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 面试计划视图对象
 *
 * @author wuketao
 * @date 2023/03/17
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewPlanVO {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 候选人id
     */
    private Integer candidateId;

    /**
     * 候选人姓名
     */
    private String candidateName;

    /**
     * 客户id
     */
    private Integer clientId;

    /**
     * 客户公司名称
     */
    private String clientName;

    /**
     * 职位id
     */
    private Integer caseId;

    /**
     * 职位名称
     */
    private String caseTitle;

    /**
     * 录入人名称
     */
    private String username;

    /**
     * cw
     */
    private String cw;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 阶段
     */
    private String phase;

    /**
     * 面试时间
     */
    private String interviewTime;

    /**
     * 内容
     */
    private String content;

    /**
     * 距离面试时间还有几天
     */
    private int distanceDays;
}
