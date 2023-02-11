package com.hello.background.domain;

import com.hello.background.constant.SalarySpecialItemTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 工资特殊项
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
public class SalarySpecialItem {
    /**
     * 用户ID
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
     * 特殊项所属月份
     */
    @Column(length = 10)
    private String month;
    /**
     * 金额
     */
    @Column
    private BigDecimal sum;
    /**
     * 是否前置计算 yes no
     */
    @Column(length = 20)
    private String isPre;
    /**
     * 特殊项类型
     */
    @Column
    @Enumerated(value = EnumType.STRING)
    private SalarySpecialItemTypeEnum type;
    /**
     * 描述
     */
    @Column(length = 2000)
    private String description;
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
