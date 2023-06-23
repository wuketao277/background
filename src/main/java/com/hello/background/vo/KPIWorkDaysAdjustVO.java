package com.hello.background.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * KPI工作天数调整
 *
 * @author wuketao
 * @date 2019/10/23
 * @Description
 */
@Data
public class KPIWorkDaysAdjustVO {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 被调整人登陆名
     */
    private String userName;
    /**
     * 调整日期
     */
    private Date adjustDate;
    /**
     * 调整天数
     */
    private BigDecimal adjustDays;
    /**
     * 备注
     */
    private String remark;
}