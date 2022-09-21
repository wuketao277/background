package com.hello.background.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wuketao
 * @date 2022/6/2
 * @Description
 */
@Data
public class SalaryInfoVO {
    /**
     * 当月税前总薪资
     */
    private BigDecimal curMonthPreTaxSum = BigDecimal.ZERO;
    /**
     * 当月税后总薪资
     */
    private BigDecimal curMonthAfterTaxSum = BigDecimal.ZERO;
}
