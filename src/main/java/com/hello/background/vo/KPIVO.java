package com.hello.background.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wuketao
 * @Description
 */
@Data
public class KPIVO {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 登录名
     */
    private String userName;
    /**
     * KPI考核月份
     */
    private String month;
    private BigDecimal finishRate;
    private BigDecimal finishRateTICF;
    private BigDecimal finishRateVIIOI;
    private BigDecimal finishRateCVO;
    private BigDecimal finishRateInterview;
    private Integer ticf;
    private Integer viioi;
    private Integer cvo;
    private Integer interview1st;
    private Integer interview2nd;
    private Integer interview3rd;
    private Integer interview4th;
    private Integer interview5th;
    private Integer interview6th;
    private Integer interviewFinal;
    private Integer vi;
    private Integer ioi;
    private Integer ti;
    private BigDecimal workDays;
    private Integer offerSigned;
    private Integer onBoard;
    private Integer newCandidates;
    /**
     * 是否考核KPI
     */
    private Boolean checkKPI;
}
