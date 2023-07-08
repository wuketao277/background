package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 候选人评论
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO {
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
     * 真实姓名
     */
    private String realname;

    /**
     * 阶段
     */
    private String phase;

    /**
     * 是否是终面
     */
    private Boolean isFinal;

    /**
     * 面试时间
     */
    private LocalDateTime interviewTime;

    /**
     * 内容
     */
    private String content;

    /**
     * 录入时间
     */
    private LocalDateTime inputTime;
}
