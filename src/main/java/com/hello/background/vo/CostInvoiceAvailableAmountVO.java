package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 成本发票可用金额
 *
 * @author wuketao
 * @date 2026/02/22
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostInvoiceAvailableAmountVO {
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
     * 发票金额
     */
    private BigDecimal sum;
}
