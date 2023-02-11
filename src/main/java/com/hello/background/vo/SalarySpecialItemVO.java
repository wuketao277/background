package com.hello.background.vo;

import com.hello.background.constant.SalarySpecialItemTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 工资特殊项视图
 *
 * @author wuketao
 * @date 2019/12/7
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalarySpecialItemVO implements Serializable {
    /**
     * 用户ID
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
     * 特殊项所属月份
     */
    private String month;
    /**
     * 金额
     */
    private BigDecimal sum;
    /**
     * 描述
     */
    private String description;
    /**
     * 是否前置计算
     */
    private String isPre;
    /**
     * 特殊项类型
     */
    private SalarySpecialItemTypeEnum type;
    /**
     * 更新日期
     */
    private Date updateTime;
    /**
     * 更新人
     */
    private String updateUserName;
}
