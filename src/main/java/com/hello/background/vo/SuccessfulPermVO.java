package com.hello.background.vo;

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
     * 部门
     */
    private String department;
    /**
     * 职位id
     */
    private Integer caseId;
    /**
     * 职位名称
     */
    private String title;
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
     * 性别
     */
    private String gender;
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
     * Leaderid
     */
    private Integer leaderId;
    /**
     * Leader登录名
     */
    private String leaderUserName;
    /**
     * Leader真实姓名
     */
    private String leaderRealName;
    /**
     * Leader提成比例
     */
    private Integer leaderCommissionPercent;
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
    private BigDecimal consultantCommissionPercent;
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
    private BigDecimal consultantCommissionPercent2;
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
    private BigDecimal consultantCommissionPercent3;
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
    private BigDecimal consultantCommissionPercent4;
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
    private BigDecimal consultantCommissionPercent5;
    /**
     * 顾问id6
     */
    private Integer consultantId6;
    /**
     * 顾问登录名6
     */
    private String consultantUserName6;
    /**
     * 顾问真实姓名6
     */
    private String consultantRealName6;
    /**
     * 顾问提成比例6
     */
    private BigDecimal consultantCommissionPercent6;
    /**
     * 顾问id7
     */
    private Integer consultantId7;
    /**
     * 顾问登录名7
     */
    private String consultantUserName7;
    /**
     * 顾问真实姓名7
     */
    private String consultantRealName7;
    /**
     * 顾问提成比例7
     */
    private BigDecimal consultantCommissionPercent7;
    /**
     * 顾问id8
     */
    private Integer consultantId8;
    /**
     * 顾问登录名8
     */
    private String consultantUserName8;
    /**
     * 顾问真实姓名8
     */
    private String consultantRealName8;
    /**
     * 顾问提成比例8
     */
    private BigDecimal consultantCommissionPercent8;
    /**
     * 顾问id9
     */
    private Integer consultantId9;
    /**
     * 顾问登录名9
     */
    private String consultantUserName9;
    /**
     * 顾问真实姓名9
     */
    private String consultantRealName9;
    /**
     * 顾问提成比例9
     */
    private BigDecimal consultantCommissionPercent9;
    /**
     * 顾问id10
     */
    private Integer consultantId10;
    /**
     * 顾问登录名10
     */
    private String consultantUserName10;
    /**
     * 顾问真实姓名10
     */
    private String consultantRealName10;
    /**
     * 顾问提成比例10
     */
    private BigDecimal consultantCommissionPercent10;
    /**
     * 顾问id11
     */
    private Integer consultantId11;
    /**
     * 顾问登录名11
     */
    private String consultantUserName11;
    /**
     * 顾问真实姓名11
     */
    private String consultantRealName11;
    /**
     * 顾问提成比例11
     */
    private BigDecimal consultantCommissionPercent11;
    /**
     * 顾问id12
     */
    private Integer consultantId12;
    /**
     * 顾问登录名12
     */
    private String consultantUserName12;
    /**
     * 顾问真实姓名12
     */
    private String consultantRealName12;
    /**
     * 顾问提成比例12
     */
    private BigDecimal consultantCommissionPercent12;
    /**
     * 顾问id13
     */
    private Integer consultantId13;
    /**
     * 顾问登录名13
     */
    private String consultantUserName13;
    /**
     * 顾问真实姓名13
     */
    private String consultantRealName13;
    /**
     * 顾问提成比例13
     */
    private BigDecimal consultantCommissionPercent13;
    /**
     * 顾问id14
     */
    private Integer consultantId14;
    /**
     * 顾问登录名14
     */
    private String consultantUserName14;
    /**
     * 顾问真实姓名14
     */
    private String consultantRealName14;
    /**
     * 顾问提成比例14
     */
    private BigDecimal consultantCommissionPercent14;
    /**
     * 顾问id15
     */
    private Integer consultantId15;
    /**
     * 顾问登录名15
     */
    private String consultantUserName15;
    /**
     * 顾问真实姓名15
     */
    private String consultantRealName15;
    /**
     * 顾问提成比例15
     */
    private BigDecimal consultantCommissionPercent15;
    /**
     * 顾问id16
     */
    private Integer consultantId16;
    /**
     * 顾问登录名16
     */
    private String consultantUserName16;
    /**
     * 顾问真实姓名16
     */
    private String consultantRealName16;
    /**
     * 顾问提成比例16
     */
    private BigDecimal consultantCommissionPercent16;
    /**
     * 顾问id17
     */
    private Integer consultantId17;
    /**
     * 顾问登录名17
     */
    private String consultantUserName17;
    /**
     * 顾问真实姓名17
     */
    private String consultantRealName17;
    /**
     * 顾问提成比例17
     */
    private BigDecimal consultantCommissionPercent17;
    /**
     * 顾问id18
     */
    private Integer consultantId18;
    /**
     * 顾问登录名18
     */
    private String consultantUserName18;
    /**
     * 顾问真实姓名18
     */
    private String consultantRealName18;
    /**
     * 顾问提成比例18
     */
    private BigDecimal consultantCommissionPercent18;
    /**
     * 顾问id19
     */
    private Integer consultantId19;
    /**
     * 顾问登录名19
     */
    private String consultantUserName19;
    /**
     * 顾问真实姓名19
     */
    private String consultantRealName19;
    /**
     * 顾问提成比例19
     */
    private BigDecimal consultantCommissionPercent19;
    /**
     * 顾问id20
     */
    private Integer consultantId20;
    /**
     * 顾问登录名20
     */
    private String consultantUserName20;
    /**
     * 顾问真实姓名20
     */
    private String consultantRealName20;
    /**
     * 顾问提成比例20
     */
    private BigDecimal consultantCommissionPercent20;
    /**
     * 地点
     */
    private String location;
    /**
     *
     */
    private BigDecimal base;
    /**
     *
     */
    private BigDecimal gp;
    /**
     *
     */
    private BigDecimal billing;
    /**
     * 入职日期
     */
    private Date onBoardDate;
    /**
     * 保证日期
     */
    private Date guaranteeDate;
    /**
     * 到面日期
     */
    private Date interviewDate;
    /**
     * offer日期
     */
    private Date offerDate;
    /**
     * 付款日期
     */
    private Date paymentDate;
    /**
     * 开票日期
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
    /**
     * 说明
     */
    private String comment;
    /**
     * 类型 perm 、contracting
     */
    private String type;
}
