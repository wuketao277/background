package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * 候选人评论
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 候选人id
     */
    @Column
    private Integer candidateId;

    /**
     * 客户id
     */
    @Column
    private Integer clientId;

    /**
     * 客户公司名称
     */
    @Column(length = 200)
    private String clientName;

    /**
     * 职位id
     */
    @Column
    private Integer caseId;

    /**
     * 职位名称
     */
    @Column(length = 200)
    private String caseTitle;

    /**
     * 登录名
     */
    @Column(length = 50)
    private String username;

    /**
     * 本名
     */
    @Column(length = 50)
    private String realname;

    /**
     * 阶段
     */
    @Column(length = 20)
    private String phase;

    /**
     * 是否是终面
     */
    @Column
    private Boolean isFinal;

    /**
     * 面试时间
     */
    private LocalDateTime interviewTime;

    /**
     * 内容
     */
    @Column(length = 2000)
    private String content;

    /**
     * 录入时间
     */
    private LocalDateTime inputTime;
}
