package com.hello.background.domain;

import com.hello.background.constant.CompanyEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
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
public class ReimbursementSummary {
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
     * 报销金额
     */
    @Column
    private BigDecimal sum;
    /**
     * 报销发放月份
     */
    @Column(length = 20)
    private String paymentMonth;
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
}
