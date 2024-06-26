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
    @Column(length = 100)
    private String chineseName;

    /**
     * 英文名
     */
    @Column(length = 100)
    private String englishName;

    /**
     * 地址
     */
    @Column(length = 2000)
    private String address;

    /**
     * 客户信息
     */
    @Column(length = 8000)
    private String information;

    /**
     * 推荐流程
     */
    @Column(length = 2000)
    private String recommendationProcess;

    /**
     * 查重要求
     */
    @Column(length = 2000)
    private String duplicateCheck;

    /**
     * 简历标准
     */
    @Column(length = 2000)
    private String resumeStandard;

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
}
