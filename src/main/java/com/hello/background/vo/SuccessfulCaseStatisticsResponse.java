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
    private BigDecimal billingSum = BigDecimal.ZERO;

    private BigDecimal gpSum = BigDecimal.ZERO;
}
