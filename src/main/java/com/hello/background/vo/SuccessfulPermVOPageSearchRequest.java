package com.hello.background.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wuketao
 * @date 2021/11/20
 * @Description
 */
@Data
public class SuccessfulPermVOPageSearchRequest {
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 顾问id
     */
    private Integer consultantId;
    /**
     * CWid
     */
    private Integer cwId;
    /**
     * HRid
     */
    private Integer hrId;
    /**
     * 收费
     */
    private BigDecimal billing;
    /**
     * BDid
     */
    private Integer bdId;
    /**
     * Leaderid
     */
    private Integer leaderId;
    /**
     * 渠道
     */
    private String channel;
    /**
     * 入职日期开始
     */
    private Date onBoardDateStart;
    /**
     * 入职日期结束
     */
    private Date onBoardDateEnd;
    /**
     * 预计付款日期开始
     */
    private Date paymentDateStart;
    /**
     * 预计付款日期结束
     */
    private Date paymentDateEnd;
    /**
     * 实际付款日期开始
     */
    private Date actualPaymentDateStart;
    /**
     * 实际付款日期结束
     */
    private Date actualPaymentDateEnd;
    /**
     * offer日期开始
     */
    private Date offerDateStart;
    /**
     * offer日期结束
     */
    private Date offerDateEnd;
    /**
     * 发票日期开始
     */
    private Date invoiceDateStart;
    /**
     * 发票日期结束
     */
    private Date invoiceDateEnd;
    /**
     * 发薪日期开始
     */
    private Date commissionDateStart;
    /**
     * 发薪日期结束
     */
    private Date commissionDateEnd;
    /**
     * 审批状态
     */
    private String approveStatus;
    /**
     * 候选人中文名
     */
    private String candidateChineseName;
    /**
     * 成功case类型
     */
    private String type;
    /**
     * 未付款
     */
    private Boolean nonPayment;
    /**
     * 到期未付款
     */
    private Boolean nonPaymentDue;
    /**
     * 一汽以外的到期未付款
     */
    private Boolean nonPaymentDueExcludeYiQi;
    /**
     * 未入职
     */
    private Boolean nonOnboard;
    /**
     * 还在保证期中
     */
    private Boolean guaranteePeriod;
    /**
     * 岗位名称
     */
    private String title;
}
