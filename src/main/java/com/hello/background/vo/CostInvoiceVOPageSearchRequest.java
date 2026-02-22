package com.hello.background.vo;

import com.hello.background.constant.InvoiceType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 成本发票分页查询
 *
 * @author wuketao
 * @date 2026/02/22
 * @Description
 */
@Data
public class CostInvoiceVOPageSearchRequest {
    /**
     * 顾问登录名
     */
    private String consultantUserName;
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
    private InvoiceType invoiceType;
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
