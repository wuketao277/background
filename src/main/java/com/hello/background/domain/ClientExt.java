package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 客户扩展表
 *
 * @author wuketao
 * @date 2024/4/16
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ClientExt {
    /**
     * 主键id
     */
    @Id
    private Integer id;

    /**
     * 薪资架构
     */
    @Column(length = 4000)
    private String salaryStructure;

    /**
     * 推荐理由
     */
    @Column(length = 4000)
    private String recommendationReason;

    /**
     * 面试准备
     */
    @Column(length = 4000)
    private String interviewPrepare;

    /**
     * 公司卖点
     */
    @Column(length = 4000)
    private String sellingPoint;

    /**
     * 特别说明
     */
    @Column(length = 4000)
    private String remark;

    /**
     * 发票联系信息
     */
    @Column(length = 400)
    private String invoiceContact;

    /**
     * 发票备注
     */
    @Column(length = 400)
    private String invoiceRemark;
}
