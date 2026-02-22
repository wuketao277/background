package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 成本发票使用情况
 *
 * @author wuketao
 * @date 2026/02/22
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostInvoiceUsedVO {
    /**
     * 主键id
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
     * 使用日期
     */
    private Date usedDate;
    /**
     * 使用金额
     */
    private BigDecimal sum;
    /**
     * 备注
     */
    private String remark;
}
