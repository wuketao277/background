package com.hello.background.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wuketao
 * @date 2021/7/3
 * @Description
 */
@Data
public class KPIPerson {
    @ExcelProperty(value = "用户ID", index = 1)
    private Integer userId;
    @ExcelProperty(value = "姓名", index = 2)
    private String realName;
    @ExcelProperty(value = "姓名", index = 3)
    private String userName;
    @ExcelProperty(value = "达成率", index = 4)
    private BigDecimal finishRate;
    @ExcelProperty(value = "工作天数", index = 5)
    private BigDecimal workDays;
    @ExcelProperty(value = "CVO", index = 6)
    private Integer cvo;
    @ExcelProperty(value = "1st", index = 7)
    private Integer interview1st;
    @ExcelProperty(value = "2nd", index = 8)
    private Integer interview2nd;
    @ExcelProperty(value = "3rd", index = 9)
    private Integer interview3rd;
    @ExcelProperty(value = "4th", index = 10)
    private Integer interview4th;
    @ExcelProperty(value = "5th", index = 11)
    private Integer interview5th;
    @ExcelProperty(value = "Final", index = 12)
    private Integer interviewFinal;
    @ExcelProperty(value = "VI+IOI", index = 13)
    private Integer viioi;
    @ExcelProperty(value = "VI", index = 14)
    private Integer vi;
    @ExcelProperty(value = "IOI", index = 15)
    private Integer ioi;
    @ExcelProperty(value = "TI+CF", index = 16)
    private Integer ticf;
    @ExcelProperty(value = "TI", index = 17)
    private Integer ti;
    @ExcelProperty("Offer Signed")
    private Integer offerSigned;
    @ExcelProperty("On Board")
    private Integer onBoard;

    public KPIPerson() {
        this.ti = 0;
        this.ticf = 0;
        this.vi = 0;
        this.ioi = 0;
        this.viioi = 0;
        this.cvo = 0;
        this.interview1st = 0;
        this.interview2nd = 0;
        this.interview3rd = 0;
        this.interview4th = 0;
        this.interview5th = 0;
        this.interviewFinal = 0;
        this.offerSigned = 0;
        this.onBoard = 0;
        this.finishRate = BigDecimal.ZERO;
        this.workDays = BigDecimal.ZERO;
    }
}
