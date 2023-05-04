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
    @ExcelProperty("用户ID")
    private Integer userId;
    @ExcelProperty("登录名")
    private String userName;
    @ExcelProperty("姓名")
    private String realName;
    @ExcelProperty("完成比例")
    private BigDecimal finishRate;
    @ExcelProperty("工作天数")
    private BigDecimal workDays;
    @ExcelProperty("TI")
    private Integer ti;
    @ExcelProperty("TICF")
    private Integer ticf;
    @ExcelProperty("VI")
    private Integer vi;
    @ExcelProperty("IOI")
    private Integer ioi;
    @ExcelProperty("VI+IOI")
    private Integer viioi;
    @ExcelProperty("CVO")
    private Integer cvo;
    @ExcelProperty("1st Interview")
    private Integer interview1st;
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
        this.offerSigned = 0;
        this.onBoard = 0;
        this.finishRate = BigDecimal.ZERO;
        this.workDays = BigDecimal.ZERO;
    }
}
