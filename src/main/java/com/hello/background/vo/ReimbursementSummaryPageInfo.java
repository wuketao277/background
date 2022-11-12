package com.hello.background.vo;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

/**
 * 报销汇总分页信息
 *
 * @author wuketao
 * @date 2022/11/9
 * @Description
 */
@Data
public class ReimbursementSummaryPageInfo {
    private Page<ReimbursementSummaryVO> page;

    private BigDecimal sum = BigDecimal.ZERO;
}
