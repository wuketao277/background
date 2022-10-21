package com.hello.background.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 报销统计对象
 *
 * @author wuketao
 * @date 2022/10/22
 * @Description
 */
@Data
public class ReimbursementStatisticsResponse {
    /**
     * 总报销金额
     */
    private BigDecimal totalReimbursementSum = BigDecimal.ZERO;
    /**
     * 需报销金额
     */
    private BigDecimal needReimbursementSum = BigDecimal.ZERO;
}
