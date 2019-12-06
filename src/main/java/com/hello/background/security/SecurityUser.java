package com.hello.background.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 用户信息表
 *
 * @author wuketao
 * @date 2019/10/23
 * @Description
 */
@Data
public class SecurityUser implements UserDetails {

    /**
     * 用户ID
     */
    private Integer id;
    /**
     * 登录名
     */
    private String username;
    /**
     * 真实姓名
     */
    private String realname;
    /**
     * 密码
     */
    private String password;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
    /**
     * 是否可用
     */
    private boolean enabled;
    /**
     * 是否过期
     */
    private boolean accountNonExpired;
    /**
     * 是否锁定
     */
    private boolean accountNonLocked;
    /**
     * 证书是否过期
     */
    private boolean credentialsNonExpired;
    /**
     * 用户所有权限
     */
    private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}