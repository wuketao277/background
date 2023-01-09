package com.hello.background.domain;

import com.hello.background.constant.CaseStatusEnum;
import com.hello.background.constant.JobTypeEnum;
import com.hello.background.converter.JobTypeEnumListStringAttrConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 职位
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
public class ClientCase {
    /**
     * id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 客户id
     */
    @Column
    private Integer clientId;

    /**
     * 中文名
     */
    @Column(length = 200)
    private String clientChineseName;

    /**
     * HRid
     */
    @Column
    private Integer hrId;

    /**
     * HR中文名
     */
    @Column(length = 200)
    private String hrChineseName;

    /**
     * HR英文名
     */
    @Column(length = 200)
    private String hrEnglishName;

    /**
     * 职位名称
     */
    @Column(length = 200)
    private String title;

    /**
     * 职级
     */
    @Column(length = 50)
    private String level;

    /**
     * 部门
     */
    @Column(length = 50)
    private String department;

    /**
     * 汇报对象
     */
    @Column(length = 50)
    private String lineManager;

    /**
     * 是否带人
     */
    @Column(length = 50)
    private String subordinates;

    /**
     * 英语要求
     */
    @Column(length = 50)
    private String english;

    /**
     * 年龄要求
     */
    @Column(length = 50)
    private String age;

    /**
     * 工作经验
     */
    @Column(length = 50)
    private String experience;

    /**
     * 学历要求
     */
    @Column(length = 50)
    private String school;

    /**
     * 薪资范围
     */
    @Column(length = 50)
    private String salaryScope;

    /**
     * 工作地点
     */
    @Column(length = 200)
    private String location;

    /**
     * 面试流程
     */
    @Column(length = 200)
    private String interviewProcess;

    /**
     * HC情况
     */
    @Column
    private Integer headCount;

    /**
     * pipeline
     */
    @Column(length = 200)
    private String pipeline;

    /**
     * 描述
     */
    @Column(length = 2000)
    private String description;

    /**
     * 状态
     */
    @Enumerated
    private CaseStatusEnum status;

    /**
     * 创建时间
     */
    @Column
    private LocalDateTime createTime;

    /**
     * 创建人id
     */
    @Column(length = 50)
    private String createUserId;

    /**
     * CW登录名
     */
    @Column(length = 50)
    private String cwUserName;

    /**
     * 该职位对哪些人可见
     */
    @Column(length = 100)
    @Convert(converter = JobTypeEnumListStringAttrConverter.class)
    private List<JobTypeEnum> show4JobType;
}
