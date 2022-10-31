package com.hello.background.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wuketao
 * @date 2021/11/28
 * @Description
 */
@Data
public class ReimbursementSummaryVODownload {
    /**
     * 报销公司名称
     */
    @ExcelProperty(value = "报销公司名称", index = 0)
    private String companyName;
    /**
     * 报销人登录名
     */
    @ExcelProperty(value = "登录名", index = 1)
    private String userName;
    /**
     * 报销发放月份
     */
    @ExcelProperty(value = "报销月份", index = 2)
    private String paymentMonth;
    /**
     * 报销金额
     */
    @ExcelProperty(value = "报销金额", index = 3)
    private BigDecimal sum;
}
