package com.hello.background.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 工资
 *
 * @author wuketao
 * @date 2019/12/7
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryVODownload {
    /**
     * 顾问登录名
     */
    @ExcelProperty(value = "Login Name", index = 0)
    private String consultantUserName;
    /**
     * 顾问真实姓名
     */
    @ExcelProperty(value = "User Name", index = 1)
    private String consultantRealName;
    /**
     * 所属月份
     */
    @ExcelProperty(value = "Month", index = 2)
    private String month;
    /**
     * 工作日
     */
    @ExcelProperty(value = "Working Days", index = 3)
    private BigDecimal workingDays;
    /**
     * 历史负债
     */
    @ExcelProperty(value = "History Debt", index = 4)
    private BigDecimal historyDebt;
    /**
     * 税前工资
     */
    @ExcelProperty(value = "Pretax Income", index = 5)
    private BigDecimal sum;
    /**
     * 税后工资
     */
    @ExcelProperty(value = "Net Pay", index = 6)
    private BigDecimal finalSum;
    /**
     * 个税
     */
    @ExcelProperty(value = "Individual Tax", index = 7)
    private BigDecimal personalTax;
    /**
     * 保险
     */
    @ExcelProperty(value = "Social Insurance", index = 8)
    private BigDecimal insurance;
    /**
     * 公积金
     */
    @ExcelProperty(value = "Housing Provident Fund", index = 9)
    private BigDecimal gongjijin;
}
