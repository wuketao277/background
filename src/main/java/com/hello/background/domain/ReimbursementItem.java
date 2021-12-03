package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wuketao
 * @date 2021/11/28
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReimbursementItem {
    /**
     * 主键
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 报销人id
     */
    @Column
    private String userId;
    /**
     * 报销人登录名
     */
    @Column(length = 50)
    private String userName;
    /**
     * 报销人真实姓名
     */
    @Column(length = 50)
    private String realName;
    /**
     * 报销类型
     */
    @Column(length = 20)
    private String type;
    /**
     * 发生日期
     */
    @Column(length = 20)
    private String happenDate;
    /**
     * 报销金额
     */
    @Column
    private BigDecimal sum;
    /**
     * 说明
     */
    @Column(length = 2000)
    private String description;
    /**
     * 报销发放月份
     */
    @Column(length = 20)
    private String paymentMonth;
    /**
     * 审批状态
     */
    @Column(length = 20)
    private String approveStatus;
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
