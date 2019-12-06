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
 * 系统资源
 * @author wuketao
 * @date 2019/10/23
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Resource {
    /**
     * 资源id
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 资源中文名称
     */
    @Column(length = 100,nullable = false)
    private String name;
    /**
     * 角色集合
     */
    @Column(length = 2000)
    private String roles;
    /**
     * 请求url
     */
    @Column(length = 500,nullable = false)
    private String url;
}
