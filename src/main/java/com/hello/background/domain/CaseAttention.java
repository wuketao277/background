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
 * 关注职位
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
public class CaseAttention {
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
     * 职位id
     */
    @Column
    private Integer caseId;

    /**
     * 职位名称
     */
    @Column(length = 200)
    private String caseTitle;

    /**
     * 关注人（登录名）
     */
    @Column(length = 50)
    private String userName;
}
