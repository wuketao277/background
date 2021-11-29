package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wuketao
 * @date 2019/12/7
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessfulPermVO implements Serializable {
    /**
     * 用户ID
     */
    private Integer id;
    /**
     * 客户id
     */
    private Integer clientId;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 审批状态 applied表示申请状态，approved表示审批通过，denied表示否决状态。
     */
    private String approveStatus;
    /**
     * 职位id
     */
    private Integer caseId;
    /**
     * 职位名称
     */
    private String title;
    /**
     * 候选人id
     */
    private Integer candidateId;
    /**
     * 候选人英文名字
     */
    private String candidateEnglishName;
    /**
     * 候选人中文名字
     */
    private String candidateChineseName;
    /**
     * CWid
     */
    private Integer cwId;
    /**
     * CW登录名
     */
    private String cwUserName;
    /**
     * CW真实姓名
     */
    private String cwRealName;
    /**
     * CW提成比例
     */
    private Integer cwCommissionPercent;
    /**
     * BDid
     */
    private Integer bdId;
    /**
     * BD登录名
     */
    private String bdUserName;
    /**
     * BD真实姓名
     */
    private String bdRealName;
    /**
     * BD提成比例
     */
    private Integer bdCommissionPercent;
    /**
     * 顾问id
     */
    private Integer consultantId;
    /**
     * 顾问登录名
     */
    private String consultantUserName;
    /**
     * 顾问真实姓名
     */
    private String consultantRealName;
    /**
     * 顾问提成比例
     */
    private Integer consultantCommissionPercent;
    /**
     * 顾问id2
     */
    private Integer consultantId2;
    /**
     * 顾问登录名2
     */
    private String consultantUserName2;
    /**
     * 顾问真实姓名2
     */
    private String consultantRealName2;
    /**
     * 顾问提成比例2
     */
    private Integer consultantCommissionPercent2;
    /**
     * 顾问id3
     */
    private Integer consultantId3;
    /**
     * 顾问登录名3
     */
    private String consultantUserName3;
    /**
     * 顾问真实姓名3
     */
    private String consultantRealName3;
    /**
     * 顾问提成比例3
     */
    private Integer consultantCommissionPercent3;
    /**
     * 顾问id4
     */
    private Integer consultantId4;
    /**
     * 顾问登录名4
     */
    private String consultantUserName4;
    /**
     * 顾问真实姓名4
     */
    private String consultantRealName4;
    /**
     * 顾问提成比例4
     */
    private Integer consultantCommissionPercent4;
    /**
     * 顾问id5
     */
    private Integer consultantId5;
    /**
     * 顾问登录名5
     */
    private String consultantUserName5;
    /**
     * 顾问真实姓名5
     */
    private String consultantRealName5;
    /**
     * 顾问提成比例5
     */
    private Integer consultantCommissionPercent5;
    /**
     * 地点
     */
    private String location;
    /**
     *
     */
    private Integer base;
    /**
     *
     */
    private Integer gp;
    /**
     *
     */
    private Integer billing;
    /**
     *
     */
    private Date onBoardDate;
    /**
     *
     */
    private Date offerDate;
    /**
     *
     */
    private Date paymentDate;
    /**
     *
     */
    private Date invoiceDate;
    /**
     *
     */
    private String po;
    /**
     *
     */
    private String invoiceNo;
    /**
     * 渠道
     */
    private String channel;
    /**
     * 实际收款日期
     */
    private Date actualPaymentDate;
    /**
     * 奖金发放日期
     */
    private Date commissionDate;
    /**
     * 更新日期
     */
    private Date updateTime;
    /**
     * 更新人
     */
    private String updateUserName;
}
