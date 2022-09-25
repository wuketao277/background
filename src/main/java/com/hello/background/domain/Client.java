package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * 客户
 *
 * @author wuketao
 * @date 2019/12/28
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 中文名
     */
    @Column(length = 200)
    private String chineseName;

    /**
     * 英文名
     */
    @Column(length = 200)
    private String englishName;

    /**
     * 客户备注
     */
    @Column(length = 2000)
    private String remark;

    /**
     * 创建时间
     */
    @Column
    private LocalDateTime createTime;

    /**
     * 创建人id
     */
    @Column(length = 50)
    private String createUserName;

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
