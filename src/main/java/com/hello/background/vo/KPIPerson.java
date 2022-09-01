package com.hello.background.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

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
    @ExcelProperty("用户名")
    private String realName;
    @ExcelProperty("TI")
    private Integer ti;
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
        this.vi = 0;
        this.ioi = 0;
        this.viioi = 0;
        this.cvo = 0;
        this.interview1st = 0;
        this.offerSigned = 0;
        this.onBoard = 0;
    }
}
