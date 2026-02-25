package com.hello.background.vo;

import com.hello.background.constant.CompanyEnum;
import com.hello.background.constant.ContractCategoryEnum;
import com.hello.background.constant.ContractIndustryEnum;
import com.hello.background.constant.ContractTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 候选人
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientContractVO {
    /**
     * id
     */
    private Integer id;
    /**
     * 客户id
     */
    private Integer clientId;
    /**
     * 中文名
     */
    private String clientChineseName;
    /**
     * 中文名
     */
    private String clientEnglishName;
    /**
     * 合同类型
     */
    private ContractCategoryEnum category;
    /**
     * 生效日期
     */
    private Date effectiveDate;
    /**
     * 结束日期
     */
    private Date expireDate;
    /**
     * 行业
     */
    private ContractIndustryEnum industry;
    /**
     * 合同类别
     */
    private ContractTypeEnum type;
    /**
     * 费率
     */
    private String feeRate;
    /**
     * 保证期
     */
    private String guaranteePeriod;
    /**
     * 付款期
     */
    private String paymentPeriod;
    /**
     * BDid
     */
    private Integer bdId;
    /**
     * BD登录名
     */
    private String bdUserName;
    /**
     * BD真实姓名
     */
    private String bdRealName;
    /**
     * 地点
     */
    private String location;
    /**
     * 便签
     */
    private String note;
    /**
     * 备注
     */
    private String comments;
    /**
     * 禁止条款
     */
    private String forbid;
    /**
     * 合同收到日期
     */
    private Date receiveDate;
    /**
     * 是否含税
     */
    private Boolean containTax;
    /**
     * 签署公司
     */
    private CompanyEnum company;
    /**
     * 人选归属期
     */
    private String candidateOwnPeriod;
    /**
     * 合同顺序
     */
    private Integer contractOrder = 0;
}
