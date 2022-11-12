package com.hello.background.vo;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

/**
 * 报销项分页信息
 *
 * @author wuketao
 * @date 2022/11/9
 * @Description
 */
@Data
public class ReimbursementItemPageInfo {
    private Page<ReimbursementItemVO> page;

    /**
     * 总报销金额
     */
    private BigDecimal totalReimbursementSum = BigDecimal.ZERO;
    /**
     * 需报销金额
     */
    private BigDecimal needReimbursementSum = BigDecimal.ZERO;
}
