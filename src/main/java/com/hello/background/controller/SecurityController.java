package com.hello.background.controller;

import com.hello.background.security.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuketao
 * @date 2019/12/7
 * @Description
 */
@RestController
@RequestMapping("security")
public class SecurityController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 更新资源映射表
     */
    @RequestMapping("updateResourceMap")
    public Boolean updateResourceMap(){
        resourceService.updateResourceMap();
        return true;
    }
}
