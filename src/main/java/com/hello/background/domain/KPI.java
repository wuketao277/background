package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author wuketao
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class KPI {
    /**
     * 主键
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 用户ID
     */
    @Column
    private Integer userId;
    /**
     * 真实姓名
     */
    @Column(length = 50, nullable = false)
    private String realName;
    /**
     * 登录名
     */
    @Column(length = 50, nullable = false)
    private String userName;
    /**
     * KPI考核月份
     */
    @Column(length = 50, nullable = false)
    private String month;
    @Column
    private BigDecimal finishRate;
    @Column
    private BigDecimal finishRateTICF;
    @Column
    private BigDecimal finishRateVIIOI;
    @Column
    private BigDecimal finishRateCVO;
    @Column
    private BigDecimal finishRateInterview;
    @Column
    private Integer ticf;
    @Column
    private Integer viioi;
    @Column
    private Integer cvo;
    @Column
    private Integer interview1st;
    @Column
    private Integer interview2nd;
    @Column
    private Integer interview3rd;
    @Column
    private Integer interview4th;
    @Column
    private Integer interview5th;
    @Column
    private Integer interview6th;
    @Column
    private Integer interviewFinal;
    @Column
    private Integer vi;
    @Column
    private Integer ioi;
    @Column
    private Integer ti;
    @Column
    private BigDecimal workDays;
    @Column
    private Integer offerSigned;
    @Column
    private Integer onBoard;
    @Column
    private Integer newCandidates;
    /**
     * 是否考核KPI
     */
    @Column
    private Boolean checkKPI;
}
