package com.hello.background.vo;

import lombok.Data;

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
    private Double curMonthPreTaxSum = 0d;
    /**
     * 当月税后总薪资
     */
    private Double curMonthAfterTaxSum = 0d;
}
