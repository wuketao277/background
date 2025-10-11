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
 * 学校类型
 *
 * @author wuketao
 * @date 2025/10/10
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SchoolType {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 学校名称
     */
    @Column(length = 200)
    private String schoolName;

    /**
     * 是否为公办院校
     */
    @Column
    private Boolean isPublic;
}
