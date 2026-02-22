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
 * 成本发票
 *
 * @author wuketao
 * @date 2026/02/22
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CostInvoice {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 顾问id
     */
    @Column
    private Integer consultantId;
    /**
     * 顾问登录名
     */
    @Column(length = 50)
    private String consultantUserName;
    /**
     * 顾问真实姓名
     */
    @Column(length = 50)
    private String consultantRealName;
    /**
     * 开票日期
     */
    @Column
    private Date invoiceDate;
    /**
     * 提交日期
     */
    @Column
    private Date submitDate;
    /**
     * 发票号码
     */
    @Column(length = 100)
    private String invoiceNumber;
    /**
     * 价税合计
     */
    @Column
    private BigDecimal sum;
    /**
     * 发票类型
     */
    @Enumerated(value = EnumType.STRING)
    private InvoiceType type;
    /**
     * 品名
     */
    @Column(length = 100)
    private String kind;
    /**
     * 备注
     */
    @Column(length = 400)
    private String remark;
    /**
     * 审批状态 applied表示申请状态，approved表示审批通过，denied表示否决状态。
     */
    @Column(length = 20)
    private String approveStatus;
}
