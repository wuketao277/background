package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户 视图对象
 *
 * @author wuketao
 * @date 2019/12/7
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO implements Serializable {
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
     * 角色集合
     */
    private List<RoleVO> roleVOList;
}
