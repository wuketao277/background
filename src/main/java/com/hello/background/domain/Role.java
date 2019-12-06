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
 * 角色类
 *
 * @author wuketao
 * @date 2019/10/23
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
    /**
     * 角色id
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 角色名称
     */
    @Column(length = 200,nullable = false)
    private String roleName;
    /**
     * 角色描述
     */
    @Column(length = 200)
    private String roleDesc;
}
