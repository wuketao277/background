package com.hello.background.security;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;

/**
 * 自定义调用安全过滤器
 *
 * @author wuketao
 * @date 2019/11/1
 * @Description
 */
@Service
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Resource(name = "JpaResourceServiceImpl")
    private ResourceService resourceService;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 安全验证
     *
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取调用请求的url
        FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequestUrl();
        // 每次都是从内存集合中获取最新资源集合数据
        for (Map.Entry<String, String[]> entry : resourceService.getResourceMap().entrySet()) {
            if (antPathMatcher.match(entry.getKey(), url)) {
                // 如果请求的url在安全控制集合范围内，就调用角色验证
                return SecurityConfig.createList(entry.getValue());
            }
        }
        // 没有匹配到,默认是不需要验证
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}