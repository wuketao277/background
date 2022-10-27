package com.hello.background.vo;

import com.hello.background.constant.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wuketao
 * @date 2021/11/28
 * @Description
 */
@Data
public class ReimbursementItemVO {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 报销人id
     */
    private Integer userId;
    /**
     * 报销人登录名
     */
    private String userName;
    /**
     * 报销人真实姓名
     */
    private String realName;
    /**
     * 审批状态
     */
    private ReimbursementApproveStatusEnum approveStatus;
    /**
     * 审批状态
     */
    private YesOrNoEnum needPay;
    /**
     * 日期
     */
    private String date;
    /**
     * 发生地点
     */
    private ReimbursementLocationEnum location;
    /**
     * 报销公司
     */
    private CompanyEnum company;
    /**
     * 报销公司名称
     */
    private String companyName;
    /**
     * 报销发放月份
     */
    private String paymentMonth;
    /**
     * 报销类型
     */
    private ReimbursementTypeEnum type;
    /**
     * 报销项目
     */
    private ReimbursementKindEnum kind;
    /**
     * 发票号
     */
    private String invoiceNo;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 数量
     */
    private BigDecimal count;
    /**
     * 报销金额
     */
    private BigDecimal sum;
    /**
     * 说明
     */
    private String description;
    /**
     * 更新日期
     */
    private Date updateTime;
    /**
     * 更新人
     */
    private String updateUserName;
}
