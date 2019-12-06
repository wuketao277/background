package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 用户信息表
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
public class User {

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
    private String username;
    /**
     * 真实姓名
     */
    @Column(length = 50, nullable = false)
    private String realname;
    /**
     * 密码
     */
    @Column(length = 50, nullable = false)
    private String password;
    /**
     * 创建日期
     */
    @Column(length = 50, nullable = false)
    private Date createDate;
    /**
     * 最后登录时间
     */
    @Column
    private Date lastLoginTime;
    /**
     * 是否可用
     */
    @Column
    private boolean enabled;
    /**
     * 是否过期
     */
    @Column
    private boolean accountNonExpired;
    /**
     * 是否锁定
     */
    @Column
    private boolean accountNonLocked;
    /**
     * 证书是否过期
     */
    @Column
    private boolean credentialsNonExpired;
}