package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 候选人
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Candidate {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 中文名字
     */
    @Column(length = 50)
    private String chineseName;

    /**
     * 英文名字
     */
    @Column(length = 100)
    private String englishName;

    /**
     * 年龄
     */
    @Column
    private Integer age;

    /**
     * 电话
     */
    @Column(length = 20)
    private String phoneNo;

    /**
     * 邮箱
     */
    @Column(length = 200)
    private String email;

    /**
     * 公司名称
     */
    @Column(length = 200)
    private String companyName;

    /**
     * 部门
     */
    @Column(length = 200)
    private String department;

    /**
     * 职位名称
     */
    @Column(length = 200)
    private String title;

}