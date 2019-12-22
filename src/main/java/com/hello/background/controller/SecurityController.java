package com.hello.background.controller;

import com.alibaba.fastjson.JSONObject;
import com.hello.background.domain.User;
import com.hello.background.repository.UserRepository;
import com.hello.background.security.ResourceService;
import com.hello.background.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("login")
    public JSONObject login(@RequestBody LoginUser vo, HttpSession session) {
        JSONObject jo = new JSONObject();
        User user = userRepository.findByUsernameAndPasswordAndEnabled(vo.getLoginName(), vo.getPassword(), true);
        if (null != user) {
            jo.put("status", true);
            jo.put("data", user);
            session.setAttribute("user", user);
        } else {
            jo.put("status", false);
        }
        return jo;
    }

    /**
     * 更新资源映射表
     */
    @RequestMapping("updateResourceMap")
    public Boolean updateResourceMap(){
        resourceService.updateResourceMap();
        return true;
    }
}
