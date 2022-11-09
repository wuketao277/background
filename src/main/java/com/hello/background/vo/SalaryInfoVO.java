package com.hello.background.vo;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

/**
 * @author wuketao
 * @date 2022/6/2
 * @Description
 */
@Data
public class SalaryInfoVO {
    /**
     * 薪资分页数据
     */
    private Page<SalaryVO> page;
    /**
     * 当月税前总薪资
     */
    private BigDecimal curMonthPreTaxSum = BigDecimal.ZERO;
    /**
     * 当月税后总薪资
     */
    private BigDecimal curMonthAfterTaxSum = BigDecimal.ZERO;
}
