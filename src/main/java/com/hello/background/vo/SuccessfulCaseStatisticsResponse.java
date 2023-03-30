package com.hello.background.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 成功case统计对象
 *
 * @author wuketao
 * @date 2022/10/22
 * @Description
 */
@Data
public class SuccessfulCaseStatisticsResponse {
    /**
     * billing总和
     */
    private BigDecimal billingSum = BigDecimal.ZERO;
    /**
     * gp总和
     */
    private BigDecimal gpSum = BigDecimal.ZERO;
    /**
     * billing平均值
     */
    private BigDecimal billingAvg = BigDecimal.ZERO;
    /**
     * gp平均值
     */
    private BigDecimal gpAvg = BigDecimal.ZERO;
}
