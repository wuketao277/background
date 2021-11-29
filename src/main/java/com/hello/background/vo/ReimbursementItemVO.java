package com.hello.background.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wuketao
 * @date 2021/11/28
 * @Description
 */
@Data
public class ReimbursementItemVO {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 报销人id
     */
    private String userId;
    /**
     * 报销人登录名
     */
    private String userName;
    /**
     * 报销人真实姓名
     */
    private String realName;
    /**
     * 报销类型
     */
    private String type;
    /**
     * 发生日期
     */
    private String happenDate;
    /**
     * 报销金额
     */
    private BigDecimal sum;
    /**
     * 说明
     */
    private String description;
    /**
     * 报销发放月份
     */
    private String paymentMonth;
    /**
     * 审批状态
     */
    private String approveStatus;
}
