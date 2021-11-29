package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author wuketao
 * @date 2019/12/7
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SuccessfulPerm {
    /**
     * 用户ID
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
     * 审批状态 applied表示申请状态，approved表示审批通过，denied表示否决状态。
     */
    @Column(length = 20)
    private String approveStatus;
    /**
     * 客户名称
     */
    @Column(length = 100)
    private String clientName;
    /**
     * 职位id
     */
    @Column
    private Integer caseId;
    /**
     * 职位名称
     */
    @Column(length = 100)
    private String title;
    /**
     * 候选人id
     */
    @Column
    private Integer candidateId;
    /**
     * 候选人英文名字
     */
    @Column(length = 50)
    private String candidateEnglishName;
    /**
     * 候选人中文名字
     */
    @Column(length = 50)
    private String candidateChineseName;
    /**
     * CWid
     */
    @Column
    private Integer cwId;
    /**
     * CW登录名
     */
    @Column(length = 50)
    private String cwUserName;
    /**
     * CW真实姓名
     */
    @Column(length = 50)
    private String cwRealName;
    /**
     * CW提成比例
     */
    @Column
    private Integer cwCommissionPercent;
    /**
     * BDid
     */
    @Column
    private Integer bdId;
    /**
     * BD登录名
     */
    @Column(length = 50)
    private String bdUserName;
    /**
     * BD真实姓名
     */
    @Column(length = 50)
    private String bdRealName;
    /**
     * BD提成比例
     */
    @Column
    private Integer bdCommissionPercent;
    /**
     * 顾问id
     */
    @Column
    private Integer consultantId;
    /**
     * 顾问登录名
     */
    @Column(length = 50)
    private String consultantUserName;
    /**
     * 顾问真实姓名
     */
    @Column(length = 50)
    private String consultantRealName;
    /**
     * 顾问提成比例
     */
    @Column
    private Integer consultantCommissionPercent;
    /**
     * 顾问id2
     */
    @Column
    private Integer consultantId2;
    /**
     * 顾问登录名2
     */
    @Column(length = 50)
    private String consultantUserName2;
    /**
     * 顾问真实姓名2
     */
    @Column(length = 50)
    private String consultantRealName2;
    /**
     * 顾问提成比例2
     */
    @Column
    private Integer consultantCommissionPercent2;
    /**
     * 顾问id3
     */
    @Column
    private Integer consultantId3;
    /**
     * 顾问登录名3
     */
    @Column(length = 50)
    private String consultantUserName3;
    /**
     * 顾问真实姓名3
     */
    @Column(length = 50)
    private String consultantRealName3;
    /**
     * 顾问提成比例3
     */
    @Column
    private Integer consultantCommissionPercent3;
    /**
     * 顾问id4
     */
    @Column
    private Integer consultantId4;
    /**
     * 顾问登录名4
     */
    @Column(length = 50)
    private String consultantUserName4;
    /**
     * 顾问真实姓名4
     */
    @Column(length = 50)
    private String consultantRealName4;
    /**
     * 顾问提成比例4
     */
    @Column
    private Integer consultantCommissionPercent4;
    /**
     * 顾问id5
     */
    @Column
    private Integer consultantId5;
    /**
     * 顾问登录名5
     */
    @Column(length = 50)
    private String consultantUserName5;
    /**
     * 顾问真实姓名5
     */
    @Column(length = 50)
    private String consultantRealName5;
    /**
     * 顾问提成比例5
     */
    @Column
    private Integer consultantCommissionPercent5;
    /**
     * 地点
     */
    @Column(length = 50)
    private String location;
    /**
     *
     */
    @Column
    private Integer base;
    /**
     *
     */
    @Column
    private Integer gp;
    /**
     *
     */
    @Column
    private Integer billing;
    /**
     *
     */
    @Column
    private Date onBoardDate;
    /**
     *
     */
    @Column
    private Date offerDate;
    /**
     *
     */
    @Column
    private Date paymentDate;
    /**
     *
     */
    @Column
    private Date invoiceDate;
    /**
     *
     */
    @Column(length = 50)
    private String po;
    /**
     *
     */
    @Column(length = 50)
    private String invoiceNo;
    /**
     * 渠道
     */
    @Column(length = 50)
    private String channel;
    /**
     * 实际收款日期
     */
    @Column
    private Date actualPaymentDate;
    /**
     * 奖金发放日期
     */
    @Column
    private Date commissionDate;
    /**
     * 更新日期
     */
    @Column
    private Date updateTime;
    /**
     * 更新人
     */
    @Column(length = 20)
    private String updateUserName;
}
