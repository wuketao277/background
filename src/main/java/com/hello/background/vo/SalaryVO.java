package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 工资
 * @author wuketao
 * @date 2019/12/7
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryVO {
    /**
     * 工资ID
     */
    private Integer id;
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
     * 所属月份
     */
    private String month;
    /**
     * 税前工资
     */
    private Integer sum;
    /**
     * 个税
     */
    private Integer personalTax;
    /**
     * 保险
     */
    private Integer insurance;
    /**
     * 公积金
     */
    private Integer gongjijin;
    /**
     * 税后工资
     */
    private Integer finalSum;
    /**
     * 历史负债
     */
    private Integer historyDebt;
    /**
     * 更新日期
     */
    private Date updateTime;
    /**
     * 更新人
     */
    private String updateUserName;
    /**
     * 描述
     */
    private String description;
}
