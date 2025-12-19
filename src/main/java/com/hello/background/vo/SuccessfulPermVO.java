package com.hello.background.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
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
    @ExcelProperty(value = "id")
    private Integer id;
    /**
     * 客户id
     */
    @ExcelProperty(value = "clientId")
    private Integer clientId;
    /**
     * 客户名称
     */
    @ExcelProperty(value = "clientName")
    private String clientName;
    /**
     * 审批状态 applied表示申请状态，approved表示审批通过，denied表示否决状态。
     */
    @ExcelProperty(value = "approveStatus")
    private String approveStatus;
    /**
     * 部门
     */
    @ExcelProperty(value = "department")
    private String department;
    /**
     * 职位id
     */
    @ExcelProperty(value = "caseId")
    private Integer caseId;
    /**
     * 职位名称
     */
    @ExcelProperty(value = "title")
    private String title;
    /**
     * HRid
     */
    @ExcelProperty(value = "hrId")
    private Integer hrId;
    /**
     * HR中文名
     */
    @ExcelProperty(value = "hrChineseName")
    private String hrChineseName;
    /**
     * HR英文名
     */
    @ExcelProperty(value = "hrEnglishName")
    private String hrEnglishName;
    /**
     * 候选人id
     */
    @ExcelProperty(value = "candidateId")
    private Integer candidateId;
    /**
     * 候选人英文名字
     */
    @ExcelProperty(value = "candidateEnglishName")
    private String candidateEnglishName;
    /**
     * 候选人中文名字
     */
    @ExcelProperty(value = "candidateChineseName")
    private String candidateChineseName;
    /**
     * 性别
     */
    @ExcelProperty(value = "gender")
    private String gender;
    /**
     * CWid
     */
    @ExcelProperty(value = "cwId")
    private Integer cwId;
    /**
     * CW登录名
     */
    @ExcelProperty(value = "cwUserName")
    private String cwUserName;
    /**
     * CW真实姓名
     */
    @ExcelProperty(value = "cwRealName")
    private String cwRealName;
    /**
     * CW提成比例
     */
    @ExcelProperty(value = "cwCommissionPercent")
    private Integer cwCommissionPercent;
    /**
     * BDid
     */
    @ExcelProperty(value = "bdId")
    private Integer bdId;
    /**
     * BD登录名
     */
    @ExcelProperty(value = "bdUserName")
    private String bdUserName;
    /**
     * BD真实姓名
     */
    @ExcelProperty(value = "bdRealName")
    private String bdRealName;
    /**
     * BD提成比例
     */
    @ExcelProperty(value = "bdCommissionPercent")
    private Integer bdCommissionPercent;
    /**
     * Leaderid
     */
    @ExcelProperty(value = "leaderId")
    private Integer leaderId;
    /**
     * Leader登录名
     */
    @ExcelProperty(value = "leaderUserName")
    private String leaderUserName;
    /**
     * Leader真实姓名
     */
    @ExcelProperty(value = "leaderRealName")
    private String leaderRealName;
    /**
     * Leader提成比例
     */
    @ExcelProperty(value = "leaderCommissionPercent")
    private Integer leaderCommissionPercent;
    /**
     * 顾问id
     */
    @ExcelProperty(value = "consultantId")
    private Integer consultantId;
    /**
     * 顾问登录名
     */
    @ExcelProperty(value = "consultantUserName")
    private String consultantUserName;
    /**
     * 顾问真实姓名
     */
    @ExcelProperty(value = "consultantRealName")
    private String consultantRealName;
    /**
     * 顾问提成比例
     */
    @ExcelProperty(value = "consultantCommissionPercent")
    private BigDecimal consultantCommissionPercent;
    /**
     * 顾问id2
     */
    @ExcelProperty(value = "consultantId2")
    private Integer consultantId2;
    /**
     * 顾问登录名2
     */
    @ExcelProperty(value = "consultantUserName2")
    private String consultantUserName2;
    /**
     * 顾问真实姓名2
     */
    @ExcelProperty(value = "consultantRealName2")
    private String consultantRealName2;
    /**
     * 顾问提成比例2
     */
    @ExcelProperty(value = "consultantCommissionPercent2")
    private BigDecimal consultantCommissionPercent2;
    /**
     * 顾问id3
     */
    @ExcelProperty(value = "consultantId3")
    private Integer consultantId3;
    /**
     * 顾问登录名3
     */
    @ExcelProperty(value = "consultantUserName3")
    private String consultantUserName3;
    /**
     * 顾问真实姓名3
     */
    @ExcelProperty(value = "consultantRealName3")
    private String consultantRealName3;
    /**
     * 顾问提成比例3
     */
    @ExcelProperty(value = "consultantCommissionPercent3")
    private BigDecimal consultantCommissionPercent3;
    /**
     * 顾问id4
     */
    @ExcelProperty(value = "consultantId4")
    private Integer consultantId4;
    /**
     * 顾问登录名4
     */
    @ExcelProperty(value = "consultantUserName4")
    private String consultantUserName4;
    /**
     * 顾问真实姓名4
     */
    @ExcelProperty(value = "consultantRealName4")
    private String consultantRealName4;
    /**
     * 顾问提成比例4
     */
    @ExcelProperty(value = "consultantCommissionPercent4")
    private BigDecimal consultantCommissionPercent4;
    /**
     * 顾问id5
     */
    @ExcelProperty(value = "consultantId5")
    private Integer consultantId5;
    /**
     * 顾问登录名5
     */
    @ExcelProperty(value = "consultantUserName5")
    private String consultantUserName5;
    /**
     * 顾问真实姓名5
     */
    @ExcelProperty(value = "consultantRealName5")
    private String consultantRealName5;
    /**
     * 顾问提成比例5
     */
    @ExcelProperty(value = "consultantCommissionPercent5")
    private BigDecimal consultantCommissionPercent5;
    /**
     * 顾问id6
     */
    @ExcelProperty(value = "consultantId6")
    private Integer consultantId6;
    /**
     * 顾问登录名6
     */
    @ExcelProperty(value = "consultantUserName6")
    private String consultantUserName6;
    /**
     * 顾问真实姓名6
     */
    @ExcelProperty(value = "consultantRealName6")
    private String consultantRealName6;
    /**
     * 顾问提成比例6
     */
    @ExcelProperty(value = "consultantCommissionPercent6")
    private BigDecimal consultantCommissionPercent6;
    /**
     * 顾问id7
     */
    @ExcelProperty(value = "consultantId7")
    private Integer consultantId7;
    /**
     * 顾问登录名7
     */
    @ExcelProperty(value = "consultantUserName7")
    private String consultantUserName7;
    /**
     * 顾问真实姓名7
     */
    @ExcelProperty(value = "consultantRealName7")
    private String consultantRealName7;
    /**
     * 顾问提成比例7
     */
    @ExcelProperty(value = "consultantCommissionPercent7")
    private BigDecimal consultantCommissionPercent7;
    /**
     * 顾问id8
     */
    @ExcelProperty(value = "consultantId8")
    private Integer consultantId8;
    /**
     * 顾问登录名8
     */
    @ExcelProperty(value = "consultantUserName8")
    private String consultantUserName8;
    /**
     * 顾问真实姓名8
     */
    @ExcelProperty(value = "consultantRealName8")
    private String consultantRealName8;
    /**
     * 顾问提成比例8
     */
    @ExcelProperty(value = "consultantCommissionPercent8")
    private BigDecimal consultantCommissionPercent8;
    /**
     * 顾问id9
     */
    @ExcelProperty(value = "consultantId9")
    private Integer consultantId9;
    /**
     * 顾问登录名9
     */
    @ExcelProperty(value = "consultantUserName9")
    private String consultantUserName9;
    /**
     * 顾问真实姓名9
     */
    @ExcelProperty(value = "consultantRealName9")
    private String consultantRealName9;
    /**
     * 顾问提成比例9
     */
    @ExcelProperty(value = "consultantCommissionPercent9")
    private BigDecimal consultantCommissionPercent9;
    /**
     * 顾问id10
     */
    @ExcelProperty(value = "consultantId10")
    private Integer consultantId10;
    /**
     * 顾问登录名10
     */
    @ExcelProperty(value = "consultantUserName10")
    private String consultantUserName10;
    /**
     * 顾问真实姓名10
     */
    @ExcelProperty(value = "consultantRealName10")
    private String consultantRealName10;
    /**
     * 顾问提成比例10
     */
    @ExcelProperty(value = "consultantCommissionPercent10")
    private BigDecimal consultantCommissionPercent10;
    /**
     * 顾问id11
     */
    @ExcelProperty(value = "consultantId11")
    private Integer consultantId11;
    /**
     * 顾问登录名11
     */
    @ExcelProperty(value = "consultantUserName11")
    private String consultantUserName11;
    /**
     * 顾问真实姓名11
     */
    @ExcelProperty(value = "consultantRealName11")
    private String consultantRealName11;
    /**
     * 顾问提成比例11
     */
    @ExcelProperty(value = "consultantCommissionPercent11")
    private BigDecimal consultantCommissionPercent11;
    /**
     * 顾问id12
     */
    @ExcelProperty(value = "consultantId12")
    private Integer consultantId12;
    /**
     * 顾问登录名12
     */
    @ExcelProperty(value = "consultantUserName12")
    private String consultantUserName12;
    /**
     * 顾问真实姓名12
     */
    @ExcelProperty(value = "consultantRealName12")
    private String consultantRealName12;
    /**
     * 顾问提成比例12
     */
    @ExcelProperty(value = "consultantCommissionPercent12")
    private BigDecimal consultantCommissionPercent12;
    /**
     * 顾问id13
     */
    @ExcelProperty(value = "consultantId13")
    private Integer consultantId13;
    /**
     * 顾问登录名13
     */
    @ExcelProperty(value = "consultantUserName13")
    private String consultantUserName13;
    /**
     * 顾问真实姓名13
     */
    @ExcelProperty(value = "consultantRealName13")
    private String consultantRealName13;
    /**
     * 顾问提成比例13
     */
    @ExcelProperty(value = "consultantCommissionPercent13")
    private BigDecimal consultantCommissionPercent13;
    /**
     * 顾问id14
     */
    @ExcelProperty(value = "consultantId14")
    private Integer consultantId14;
    /**
     * 顾问登录名14
     */
    @ExcelProperty(value = "consultantUserName14")
    private String consultantUserName14;
    /**
     * 顾问真实姓名14
     */
    @ExcelProperty(value = "consultantRealName14")
    private String consultantRealName14;
    /**
     * 顾问提成比例14
     */
    @ExcelProperty(value = "consultantCommissionPercent14")
    private BigDecimal consultantCommissionPercent14;
    /**
     * 顾问id15
     */
    @ExcelProperty(value = "consultantId15")
    private Integer consultantId15;
    /**
     * 顾问登录名15
     */
    @ExcelProperty(value = "consultantUserName15")
    private String consultantUserName15;
    /**
     * 顾问真实姓名15
     */
    @ExcelProperty(value = "consultantRealName15")
    private String consultantRealName15;
    /**
     * 顾问提成比例15
     */
    @ExcelProperty(value = "consultantCommissionPercent15")
    private BigDecimal consultantCommissionPercent15;
    /**
     * 顾问id16
     */
    @ExcelProperty(value = "consultantId16")
    private Integer consultantId16;
    /**
     * 顾问登录名16
     */
    @ExcelProperty(value = "consultantUserName16")
    private String consultantUserName16;
    /**
     * 顾问真实姓名16
     */
    @ExcelProperty(value = "consultantRealName16")
    private String consultantRealName16;
    /**
     * 顾问提成比例16
     */
    @ExcelProperty(value = "consultantCommissionPercent16")
    private BigDecimal consultantCommissionPercent16;
    /**
     * 顾问id17
     */
    @ExcelProperty(value = "consultantId17")
    private Integer consultantId17;
    /**
     * 顾问登录名17
     */
    @ExcelProperty(value = "consultantUserName17")
    private String consultantUserName17;
    /**
     * 顾问真实姓名17
     */
    @ExcelProperty(value = "consultantRealName17")
    private String consultantRealName17;
    /**
     * 顾问提成比例17
     */
    @ExcelProperty(value = "consultantCommissionPercent17")
    private BigDecimal consultantCommissionPercent17;
    /**
     * 顾问id18
     */
    @ExcelProperty(value = "consultantId18")
    private Integer consultantId18;
    /**
     * 顾问登录名18
     */
    @ExcelProperty(value = "consultantUserName18")
    private String consultantUserName18;
    /**
     * 顾问真实姓名18
     */
    @ExcelProperty(value = "consultantRealName18")
    private String consultantRealName18;
    /**
     * 顾问提成比例18
     */
    @ExcelProperty(value = "consultantCommissionPercent18")
    private BigDecimal consultantCommissionPercent18;
    /**
     * 顾问id19
     */
    @ExcelProperty(value = "consultantId19")
    private Integer consultantId19;
    /**
     * 顾问登录名19
     */
    @ExcelProperty(value = "consultantUserName19")
    private String consultantUserName19;
    /**
     * 顾问真实姓名19
     */
    @ExcelProperty(value = "consultantRealName19")
    private String consultantRealName19;
    /**
     * 顾问提成比例19
     */
    @ExcelProperty(value = "consultantCommissionPercent19")
    private BigDecimal consultantCommissionPercent19;
    /**
     * 顾问id20
     */
    @ExcelProperty(value = "consultantId20")
    private Integer consultantId20;
    /**
     * 顾问登录名20
     */
    @ExcelProperty(value = "consultantUserName20")
    private String consultantUserName20;
    /**
     * 顾问真实姓名20
     */
    @ExcelProperty(value = "consultantRealName20")
    private String consultantRealName20;
    /**
     * 顾问提成比例20
     */
    @ExcelProperty(value = "consultantCommissionPercent20")
    private BigDecimal consultantCommissionPercent20;
    /**
     * 地点
     */
    @ExcelProperty(value = "location")
    private String location;
    /**
     *
     */
    @ExcelProperty(value = "base")
    private BigDecimal base;
    /**
     *
     */
    @ExcelProperty(value = "gp")
    private BigDecimal gp;
    /**
     *
     */
    @ExcelProperty(value = "billing")
    private BigDecimal billing;
    /**
     * 入职日期
     */
    @ExcelProperty(value = "onBoardDate")
    private Date onBoardDate;
    /**
     * 保证日期
     */
    @ExcelProperty(value = "guaranteeDate")
    private Date guaranteeDate;
    /**
     * 到面日期
     */
    @ExcelProperty(value = "interviewDate")
    private Date interviewDate;
    /**
     * offer日期
     */
    @ExcelProperty(value = "offerDate")
    private Date offerDate;
    /**
     * 付款日期
     */
    @ExcelProperty(value = "paymentDate")
    private Date paymentDate;
    /**
     * 开票日期
     */
    @ExcelProperty(value = "invoiceDate")
    private Date invoiceDate;
    /**
     *
     */
    @ExcelProperty(value = "po")
    private String po;
    /**
     *
     */
    @ExcelProperty(value = "invoiceNo")
    private String invoiceNo;
    /**
     * 渠道
     */
    @ExcelProperty(value = "channel")
    private String channel;
    /**
     * 实际收款日期
     */
    @ExcelProperty(value = "actualPaymentDate")
    private Date actualPaymentDate;
    /**
     * 奖金发放日期
     */
    @ExcelProperty(value = "commissionDate")
    private Date commissionDate;
    /**
     * 更新日期
     */
    @ExcelProperty(value = "updateTime")
    private Date updateTime;
    /**
     * 更新人
     */
    @ExcelProperty(value = "updateUserName")
    private String updateUserName;
    /**
     * 说明
     */
    @ExcelProperty(value = "comment")
    private String comment;
    /**
     * 类型 perm 、contracting
     */
    @ExcelProperty(value = "type")
    private String type;

    /**
     * 候选人年龄
     */
    @ExcelProperty(value = "candidateAge")
    private Integer candidateAge;
}
