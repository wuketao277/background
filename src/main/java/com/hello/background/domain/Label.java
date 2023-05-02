package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 标签
 *
 * @author wuketao
 * @date 2023/4/29
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Label {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 名称
     */
    @Column(length = 20)
    private String name;
}
