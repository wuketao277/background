package com.hello.background.vo;

import com.hello.background.constant.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 发票信息
 *
 * @author wuketao
 * @date 2022/9/25
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceVO {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 开票日期
     */
    private Date createDate;

    /**
     * 开票金额
     */
    private BigDecimal sum;

    /**
     * 发票类型
     */
    private InvoiceType type;

    /**
     * 客户Id
     */
    private Integer clientId;

    /**
     * 客户中文名
     */
    private String clientChineseName;

    /**
     * 品名
     */
    private String kind;

    /**
     * po
     */
    private String po;

    /**
     * 联系信息
     */
    private String contact;

    /**
     * AM ID
     */
    private Integer amId;

    /**
     * AM 登录名
     */
    private String amName;

    /**
     * AM 真实姓名
     */
    private String amChineseName;

    /**
     * 候选人 id
     */
    private Integer candidateId;

    /**
     * 候选人 中文名字
     */
    private String candidateChineseName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 寄出日期
     */
    private Date sendDate;

    /**
     * 收到日期
     */
    private Date receiveDate;

    /**
     * 正常/作废
     */
    private Boolean status;
}
