package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
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
     * Leaderid
     */
    @Column
    private Integer leaderId;
    /**
     * Leader登录名
     */
    @Column(length = 50)
    private String leaderUserName;
    /**
     * Leader真实姓名
     */
    @Column(length = 50)
    private String leaderRealName;
    /**
     * Leader提成比例
     */
    @Column
    private Integer leaderCommissionPercent;
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
    private BigDecimal consultantCommissionPercent;
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
    private BigDecimal consultantCommissionPercent2;
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
    private BigDecimal consultantCommissionPercent3;
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
    private BigDecimal consultantCommissionPercent4;
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
    private BigDecimal consultantCommissionPercent5;
    /**
     * 顾问id6
     */
    @Column
    private Integer consultantId6;
    /**
     * 顾问登录名6
     */
    @Column(length = 50)
    private String consultantUserName6;
    /**
     * 顾问真实姓名6
     */
    @Column(length = 50)
    private String consultantRealName6;
    /**
     * 顾问提成比例6
     */
    @Column
    private BigDecimal consultantCommissionPercent6;
    /**
     * 顾问id7
     */
    @Column
    private Integer consultantId7;
    /**
     * 顾问登录名7
     */
    @Column(length = 50)
    private String consultantUserName7;
    /**
     * 顾问真实姓名7
     */
    @Column(length = 50)
    private String consultantRealName7;
    /**
     * 顾问提成比例7
     */
    @Column
    private BigDecimal consultantCommissionPercent7;
    /**
     * 顾问id8
     */
    @Column
    private Integer consultantId8;
    /**
     * 顾问登录名8
     */
    @Column(length = 50)
    private String consultantUserName8;
    /**
     * 顾问真实姓名8
     */
    @Column(length = 50)
    private String consultantRealName8;
    /**
     * 顾问提成比例8
     */
    @Column
    private BigDecimal consultantCommissionPercent8;
    /**
     * 顾问id9
     */
    @Column
    private Integer consultantId9;
    /**
     * 顾问登录名9
     */
    @Column(length = 50)
    private String consultantUserName9;
    /**
     * 顾问真实姓名9
     */
    @Column(length = 50)
    private String consultantRealName9;
    /**
     * 顾问提成比例9
     */
    @Column
    private BigDecimal consultantCommissionPercent9;
    /**
     * 顾问id10
     */
    @Column
    private Integer consultantId10;
    /**
     * 顾问登录名10
     */
    @Column(length = 50)
    private String consultantUserName10;
    /**
     * 顾问真实姓名10
     */
    @Column(length = 50)
    private String consultantRealName10;
    /**
     * 顾问提成比例
     */
    @Column
    private BigDecimal consultantCommissionPercent10;
    /**
     * 顾问id11
     */
    @Column
    private Integer consultantId11;
    /**
     * 顾问登录名11
     */
    @Column(length = 50)
    private String consultantUserName11;
    /**
     * 顾问真实姓名11
     */
    @Column(length = 50)
    private String consultantRealName11;
    /**
     * 顾问提成比例11
     */
    @Column
    private BigDecimal consultantCommissionPercent11;
    /**
     * 顾问id12
     */
    @Column
    private Integer consultantId12;
    /**
     * 顾问登录名12
     */
    @Column(length = 50)
    private String consultantUserName12;
    /**
     * 顾问真实姓名12
     */
    @Column(length = 50)
    private String consultantRealName12;
    /**
     * 顾问提成比例12
     */
    @Column
    private BigDecimal consultantCommissionPercent12;
    /**
     * 顾问id13
     */
    @Column
    private Integer consultantId13;
    /**
     * 顾问登录名13
     */
    @Column(length = 50)
    private String consultantUserName13;
    /**
     * 顾问真实姓名13
     */
    @Column(length = 50)
    private String consultantRealName13;
    /**
     * 顾问提成比例13
     */
    @Column
    private BigDecimal consultantCommissionPercent13;
    /**
     * 顾问id14
     */
    @Column
    private Integer consultantId14;
    /**
     * 顾问登录名14
     */
    @Column(length = 50)
    private String consultantUserName14;
    /**
     * 顾问真实姓名14
     */
    @Column(length = 50)
    private String consultantRealName14;
    /**
     * 顾问提成比例14
     */
    @Column
    private BigDecimal consultantCommissionPercent14;
    /**
     * 顾问id15
     */
    @Column
    private Integer consultantId15;
    /**
     * 顾问登录名15
     */
    @Column(length = 50)
    private String consultantUserName15;
    /**
     * 顾问真实姓名15
     */
    @Column(length = 50)
    private String consultantRealName15;
    /**
     * 顾问提成比例15
     */
    @Column
    private BigDecimal consultantCommissionPercent15;
    /**
     * 顾问id16
     */
    @Column
    private Integer consultantId16;
    /**
     * 顾问登录名16
     */
    @Column(length = 50)
    private String consultantUserName16;
    /**
     * 顾问真实姓名16
     */
    @Column(length = 50)
    private String consultantRealName16;
    /**
     * 顾问提成比例16
     */
    @Column
    private BigDecimal consultantCommissionPercent16;
    /**
     * 顾问id17
     */
    @Column
    private Integer consultantId17;
    /**
     * 顾问登录名17
     */
    @Column(length = 50)
    private String consultantUserName17;
    /**
     * 顾问真实姓名17
     */
    @Column(length = 50)
    private String consultantRealName17;
    /**
     * 顾问提成比例17
     */
    @Column
    private BigDecimal consultantCommissionPercent17;
    /**
     * 顾问id18
     */
    @Column
    private Integer consultantId18;
    /**
     * 顾问登录名18
     */
    @Column(length = 50)
    private String consultantUserName18;
    /**
     * 顾问真实姓名18
     */
    @Column(length = 50)
    private String consultantRealName18;
    /**
     * 顾问提成比例18
     */
    @Column
    private BigDecimal consultantCommissionPercent18;
    /**
     * 顾问id19
     */
    @Column
    private Integer consultantId19;
    /**
     * 顾问登录名19
     */
    @Column(length = 50)
    private String consultantUserName19;
    /**
     * 顾问真实姓名9
     */
    @Column(length = 50)
    private String consultantRealName19;
    /**
     * 顾问提成比例19
     */
    @Column
    private BigDecimal consultantCommissionPercent19;
    /**
     * 顾问id20
     */
    @Column
    private Integer consultantId20;
    /**
     * 顾问登录名20
     */
    @Column(length = 50)
    private String consultantUserName20;
    /**
     * 顾问真实姓名20
     */
    @Column(length = 50)
    private String consultantRealName20;
    /**
     * 顾问提成比例20
     */
    @Column
    private BigDecimal consultantCommissionPercent20;
    /**
     * 地点
     */
    @Column(length = 50)
    private String location;
    /**
     *
     */
    @Column
    private BigDecimal base;
    /**
     *
     */
    @Column
    private BigDecimal gp;
    /**
     * 收费
     */
    @Column
    private BigDecimal billing;
    /**
     * 入职日期
     */
    @Column
    private Date onBoardDate;
    /**
     * 保证日期
     */
    @Column
    private Date guaranteeDate;
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
    /**
     * 说明
     */
    @Column(length = 400)
    private String comment;
    /**
     * 类型 perm 、contracting、consultation
     */
    @Column(length = 20)
    private String type;
}
