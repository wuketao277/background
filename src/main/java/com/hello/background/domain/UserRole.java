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
 * @author wuketao
 * @date 2019/12/6
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserRole {

    /**
     * 用户ID
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 登录名
     */
    @Column(length = 50, nullable = false)
    private String userName;
    /**
     * 角色名称
     */
    @Column(length = 200,nullable = false)
    private String roleName;
}
