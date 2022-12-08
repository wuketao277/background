package com.hello.background.domain;

import com.hello.background.constant.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author wuketao
 * @date 2021/11/28
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReimbursementItem {
    /**
     * 主键
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 报销人id
     */
    @Column
    private Integer userId;
    /**
     * 报销人登录名
     */
    @Column(length = 50)
    private String userName;
    /**
     * 报销人真实姓名
     */
    @Column(length = 50)
    private String realName;
    /**
     * 审批状态
     */
    @Column
    @Enumerated(value = EnumType.STRING)
    private ReimbursementApproveStatusEnum approveStatus;
    /**
     * 是否报销
     */
    @Column
    @Enumerated(value = EnumType.STRING)
    private ReimbursementNeedPayEnum needPay;
    /**
     * 日期
     */
    @Column(length = 20)
    private String date;
    /**
     * 发生地点
     */
    @Column
    @Enumerated(value = EnumType.STRING)
    private ReimbursementLocationEnum location;
    /**
     * 报销公司
     */
    @Column
    @Enumerated(value = EnumType.STRING)
    private CompanyEnum company;
    /**
     * 报销公司名称
     */
    @Column
    private String companyName;
    /**
     * 报销发放月份
     */
    @Column(length = 20)
    private String paymentMonth;
    /**
     * 报销类型
     */
    @Column
    @Enumerated(value = EnumType.STRING)
    private ReimbursementTypeEnum type;
    /**
     * 报销项目
     */
    @Column
    @Enumerated(value = EnumType.STRING)
    private ReimbursementKindEnum kind;
    /**
     * 发票号
     */
    @Column(length = 30)
    private String invoiceNo;
    /**
     * 单价
     */
    @Column
    private BigDecimal price;
    /**
     * 数量
     */
    @Column
    private BigDecimal count;
    /**
     * 报销金额
     */
    @Column
    private BigDecimal sum;
    /**
     * 说明
     */
    @Column(length = 2000)
    private String description;
    /**
     * 更新日期
     */
    @Column
    private Date updateTime;
    /**
     * 更新人
     */
    @Column(length = 20)
    private String updateUserName;
    /**
     * 创建日期
     */
    @Column
    private LocalDateTime createTime;
    /**
     * 创建人
     */
    @Column(length = 20)
    private String createUser;
}
