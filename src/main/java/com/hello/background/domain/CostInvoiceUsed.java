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
 * 成本发票使用情况
 *
 * @author wuketao
 * @date 2026/02/22
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CostInvoiceUsed {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue
    private Integer id;
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
     * 使用日期
     */
    @Column
    private Date usedDate;
    /**
     * 使用金额
     */
    @Column
    private BigDecimal sum;
    /**
     * 备注
     */
    @Column(length = 400)
    private String remark;
}
