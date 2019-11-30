package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 客户
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    /**
     * 新闻主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 客户中文名称
     * 最多200个字符，不允许为空
     */
    @Column(length = 200, nullable = false)
    private String customerChineseName;

    /**
     * 客户英文名称
     * 最多200个字符，不允许为空
     */
    @Column(length = 200, nullable = false)
    private String customerEnglishName;
}
