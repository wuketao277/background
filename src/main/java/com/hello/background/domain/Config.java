package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 配置
 *
 * @author wuketao
 * @date 2022/11/04
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Config {
    /**
     * 主键id
     */
    @Id
    private Integer id;

    /**
     * 编码
     */
    @Column(length = 100)
    private String code;

    /**
     * 名称
     */
    @Column(length = 100)
    private String name;

    /**
     * 类别（大类）
     */
    @Column(length = 100)
    private String category;

    /**
     * 类型（小类）
     */
    @Column(length = 100)
    private String type;
}
