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
    private Date actualAcceptDate;
    /**
     * 奖金发放日期
     */
    private Date bonusPaymentDate;
    /**
     * 更新日期
     */
    private Date updateTime;
    /**
     * 更新人
     */
    private String updateUserName;
}
