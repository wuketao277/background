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
 * 工资
 *
 * @author wuketao
 * @date 2019/12/7
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Salary {
    /**
     * 工资ID
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
     * 公司英文名称
     */
    @Column(length = 50)
    private String company;
    /**
     * 公司名称
     */
    @Column(length = 50)
    private String companyName;
    /**
     * 所属月份
     */
    @Column(length = 10)
    private String month;
    /**
     * 税前工资
     */
    @Column
    private BigDecimal sum;
    /**
     * 个税
     */
    @Column
    private BigDecimal personalTax;
    /**
     * 保险
     */
    @Column
    private BigDecimal insurance;
    /**
     * 公积金
     */
    @Column
    private BigDecimal gongjijin;
    /**
     * 工作日
     */
    @Column
    private BigDecimal workingDays;
    /**
     * 税后工资
     */
    @Column
    private BigDecimal finalSum;
    /**
     * 起提点
     */
    @Column
    private BigDecimal historyDebt;
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
     * 描述
     */
    @Column(length = 2000)
    private String description;
}
