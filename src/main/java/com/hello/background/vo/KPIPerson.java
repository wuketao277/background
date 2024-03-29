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
    @ExcelProperty(value = "TI达成率", index = 5)
    private BigDecimal finishRateTICF;
    @ExcelProperty(value = "VI+IOI达成率", index = 6)
    private BigDecimal finishRateVIIOI;
    @ExcelProperty(value = "CVO达成率", index = 7)
    private BigDecimal finishRateCVO;
    @ExcelProperty(value = "面试达成率", index = 8)
    private BigDecimal finishRateInterview;
    @ExcelProperty(value = "TI+CF", index = 9)
    private Integer ticf;
    @ExcelProperty(value = "VI+IOI", index = 10)
    private Integer viioi;
    @ExcelProperty(value = "CVO", index = 11)
    private Integer cvo;
    @ExcelProperty(value = "1st", index = 12)
    private Integer interview1st;
    @ExcelProperty(value = "2nd", index = 13)
    private Integer interview2nd;
    @ExcelProperty(value = "3rd", index = 14)
    private Integer interview3rd;
    @ExcelProperty(value = "4th", index = 15)
    private Integer interview4th;
    @ExcelProperty(value = "5th", index = 16)
    private Integer interview5th;
    @ExcelProperty(value = "6th", index = 17)
    private Integer interview6th;
    @ExcelProperty(value = "Final", index = 18)
    private Integer interviewFinal;
    @ExcelProperty(value = "VI", index = 19)
    private Integer vi;
    @ExcelProperty(value = "IOI", index = 20)
    private Integer ioi;
    @ExcelProperty(value = "TI", index = 21)
    private Integer ti;
    @ExcelProperty(value = "工作天数", index = 22)
    private BigDecimal workDays;
    @ExcelProperty("Offer Signed")
    private Integer offerSigned;
    @ExcelProperty("On Board")
    private Integer onBoard;
    @ExcelProperty("New Candidate")
    private Integer newCandidates;

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
        this.interview6th = 0;
        this.interviewFinal = 0;
        this.offerSigned = 0;
        this.onBoard = 0;
        this.finishRate = BigDecimal.ZERO;
        this.workDays = BigDecimal.ZERO;
        this.newCandidates = 0;
    }
}
