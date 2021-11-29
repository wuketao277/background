package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 工资
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
     * 所属月份
     */
    @Column(length = 10)
    private String month;
    /**
     * 税前工资
     */
    @Column
    private Integer sum;
    /**
     * 个税
     */
    @Column
    private Integer personalTax;
    /**
     * 保险
     */
    @Column
    private Integer insurance;
    /**
     * 公积金
     */
    @Column
    private Integer gongjijin;
    /**
     * 税后工资
     */
    @Column
    private Integer finalSum;
    /**
     * 历史负债
     */
    @Column
    private Integer historyDebt;
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
