package com.hello.background.vo;

import com.hello.background.constant.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 成本发票
 *
 * @author wuketao
 * @date 2026/02/22
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostInvoiceVO {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 顾问id
     */
    private Integer consultantId;
    /**
     * 顾问登录名
     */
    private String consultantUserName;
    /**
     * 顾问真实姓名
     */
    private String consultantRealName;
    /**
     * 开票日期
     */
    private Date invoiceDate;
    /**
     * 提交日期
     */
    private Date submitDate;
    /**
     * 发票号码
     */
    private String invoiceNumber;
    /**
     * 发票金额
     */
    private BigDecimal sum;
    /**
     * 发票类型
     */
    private InvoiceType type;
    /**
     * 品名
     */
    private String kind;
    /**
     * 备注
     */
    private String remark;
    /**
     * 审批状态 applied表示申请状态，approved表示审批通过，denied表示否决状态。
     */
    private String approveStatus;
}
