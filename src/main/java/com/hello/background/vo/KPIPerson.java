package com.hello.background.vo;

import lombok.Data;

/**
 * @author wuketao
 * @date 2021/7/3
 * @Description
 */
@Data
public class KPIPerson {
    private Integer userId;
    private String userName;
    private String realName;
    private Integer ti;
    private Integer vi;
    private Integer ioi;
    private Integer viioi;
    private Integer cvo;
    private Integer interview1st;
    private Integer offerSigned;
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
