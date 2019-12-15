package com.hello.background.security;

import java.util.Map;

/**
 * 资源服务接口
 *
 * @author wuketao
 * @date 2019/11/2
 * @Description
 */
public interface ResourceService {
    /**
     * 获取资源-角色集合 映射表
     *
     * @return
     */
    Map<String, String[]> getResourceMap();

    /**
     * 更新资源映射表
     */
    void updateResourceMap();
}
