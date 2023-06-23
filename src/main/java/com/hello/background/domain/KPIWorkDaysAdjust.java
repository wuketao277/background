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
 * KPI工作天数调整
 *
 * @author wuketao
 * @date 2019/10/23
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class KPIWorkDaysAdjust {

    /**
     * 主键
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 调整天数
     */
    @Column
    private BigDecimal adjustDays;
    /**
     * 调整日期
     */
    @Column
    private Date adjustDate;
    /**
     * 被调整人登陆名
     */
    @Column(length = 50, nullable = false)
    private String userName;
    /**
     * 备注
     */
    @Column(length = 500)
    private String remark;
}