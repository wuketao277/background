package com.hello.background.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;

/**
 * 自定义访问决策管理器，通过判断用户是否拥有对应角色来决定是否准入
 *
 * @author wuketao
 * @date 2019/11/1
 * @Description
 */
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {
    /**
     * 决策判断方法
     *
     * @param authentication
     * @param object
     * @param configAttributes
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        if (CollectionUtils.isEmpty(configAttributes)) {
            throw new AccessDeniedException("not allow");
        }
        // 对资源授权的角色与用户用户的角色进行交叉匹配，判断用户是否有权准入
        Iterator<ConfigAttribute> ite = configAttributes.iterator();
        while (ite.hasNext()) {
            ConfigAttribute ca = ite.next();
            String needRole = ca.getAttribute();
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (ga.getAuthority().equals(needRole)) {
                    //匹配到有对应角色,则允许通过
                    return;
                }
            }
        }
        //该url有配置权限,但是当然登录用户没有匹配到对应权限,则禁止访问
        throw new AccessDeniedException("not allow");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}