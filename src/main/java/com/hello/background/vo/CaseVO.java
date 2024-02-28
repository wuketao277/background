package com.hello.background.vo;

import com.hello.background.constant.CaseStatusEnum;
import com.hello.background.constant.JobTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 职位
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseVO {
    /**
     * 职位主键id
     */
    private Integer id;

    /**
     * 客户id
     */
    private Integer clientId;

    /**
     * 客户名称
     */
    private String clientChineseName;

    /**
     * HRid
     */
    private Integer hrId;

    /**
     * HR中文名
     */
    private String hrChineseName;

    /**
     * HR英文名
     */
    private String hrEnglishName;

    /**
     * 职位名称
     */
    private String title;

    /**
     * 职级
     */
    private String level;

    /**
     * 部门
     */
    private String department;

    /**
     * 汇报对象
     */
    private String lineManager;

    /**
     * 是否带人
     */
    private String subordinates;

    /**
     * 英语要求
     */
    private String english;

    /**
     * 年龄要求
     */
    private String age;

    /**
     * 工作经验
     */
    private String experience;

    /**
     * 学历要求
     */
    private String school;

    /**
     * 薪资范围
     */
    private String salaryScope;

    /**
     * 工作地点
     */
    private String location;

    /**
     * 面试流程
     */
    private String interviewProcess;

    /**
     * 目标公司
     */
    private String targetCompany;

    /**
     * HC情况
     */
    private String headCount;

    /**
     * pipeline
     */
    private String pipeline;

    /**
     * 描述
     */
    private String description;

    /**
     * top 3 skills
     */
    private String top3Skills;

    /**
     * 面试准备题
     */
    private String question;

    /**
     * 状态
     */
    private CaseStatusEnum status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人id
     */
    private String createUserId;

    /**
     * CW登录名
     */
    private String cwUserName;

    /**
     * 该职位对哪些人可见
     */
    private List<JobTypeEnum> show4JobType;

    /**
     * sourcingMap
     */
    private String sourcingMap;

    /**
     * 前任情况
     */
    private String predecessor;
}
