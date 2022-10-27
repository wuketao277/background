package com.hello.background.vo;

import com.hello.background.constant.CompanyEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wuketao
 * @date 2021/11/28
 * @Description
 */
@Data
public class ReimbursementSummaryVO {
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
     * 报销公司
     */
    private CompanyEnum company;
    /**
     * 报销公司名称
     */
    private String companyName;
    /**
     * 报销金额
     */
    private BigDecimal sum;
    /**
     * 报销发放月份
     */
    private String paymentMonth;
}
