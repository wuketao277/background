package com.hello.background.domain;

import com.hello.background.constant.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 发票
 *
 * @author wuketao
 * @date 2022/09/25
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Invoice {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 开票日期
     */
    @Column
    private Date createDate;

    /**
     * 发票金额
     */
    @Column
    private BigDecimal sum;

    /**
     * 发票类型
     */
    @Enumerated
    private InvoiceType type;

    /**
     * 客户Id
     */
    @Column
    private Integer clientId;

    /**
     * 客户中文名
     */
    @Column(length = 200)
    private String clientChineseName;

    /**
     * 品名
     */
    @Column(length = 100)
    private String kind;

    /**
     * po
     */
    @Column(length = 300)
    private String po;

    /**
     * 联系信息
     */
    @Column(length = 400)
    private String contact;

    /**
     * AM ID
     */
    @Column
    private Integer amId;

    /**
     * AM 登录名
     */
    @Column(length = 50)
    private String amName;

    /**
     * AM 真实姓名
     */
    @Column(length = 50)
    private String amChineseName;

    /**
     * 候选人 id
     */
    @Column
    private Integer candidateId;

    /**
     * 候选人 中文名字
     */
    @Column(length = 50)
    private String candidateChineseName;

    /**
     * 备注
     */
    @Column(length = 400)
    private String remark;

    /**
     * 寄出日期
     */
    @Column
    private Date sendDate;

    /**
     * 收到日期
     */
    @Column
    private Date receiveDate;

    /**
     * 正常/作废
     */
    @Column
    private Boolean status;
}
