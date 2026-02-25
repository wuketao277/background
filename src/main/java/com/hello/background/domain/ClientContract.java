package com.hello.background.domain;

import com.hello.background.constant.CompanyEnum;
import com.hello.background.constant.ContractCategoryEnum;
import com.hello.background.constant.ContractIndustryEnum;
import com.hello.background.constant.ContractTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;

/**
 * 客户合同
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ClientContract {
    /**
     * id
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 客户id
     */
    @Column
    private Integer clientId;
    /**
     * 中文名
     */
    @Column(length = 200)
    private String clientChineseName;
    /**
     * 英文名
     */
    @Column(length = 200)
    private String clientEnglishName;
    /**
     * 合同类型
     */
    @Enumerated(value = EnumType.STRING)
    private ContractCategoryEnum category;
    /**
     * 生效日期
     */
    @Column
    private Date effectiveDate;
    /**
     * 结束日期
     */
    @Column
    private Date expireDate;
    /**
     * 行业
     */
    @Enumerated(value = EnumType.STRING)
    private ContractIndustryEnum industry;
    /**
     * 合同类别
     */
    @Enumerated(value = EnumType.STRING)
    private ContractTypeEnum type;
    /**
     * 费率
     */
    @Column(length = 200)
    private String feeRate;
    /**
     * 保证期
     */
    @Column(length = 200)
    private String guaranteePeriod;
    /**
     * 付款期
     */
    @Column(length = 200)
    private String paymentPeriod;
    /**
     * BDid
     */
    @Column
    private Integer bdId;
    /**
     * BD登录名
     */
    @Column(length = 50)
    private String bdUserName;
    /**
     * BD真实姓名
     */
    @Column(length = 50)
    private String bdRealName;
    /**
     * 地点
     */
    @Column(length = 50)
    private String location;
    /**
     * 便签
     */
    @Column(length = 400)
    private String note;
    /**
     * 备注
     */
    @Column(length = 400)
    private String comments;
    /**
     * 禁止条款
     */
    @Column(length = 400)
    private String forbid;
    /**
     * 合同收到日期
     */
    @Column
    private Date receiveDate;
    /**
     * 是否含税
     */
    @Column
    private Boolean containTax;
    /**
     * 签署公司
     */
    @Enumerated(value = EnumType.STRING)
    private CompanyEnum company;
    /**
     * 人选归属期
     */
    @Column(length = 200)
    private String candidateOwnPeriod;

    /**
     * 合同顺序
     */
    @Column(columnDefinition = "int default 0")
    private Integer contractOrder = 0;
}
