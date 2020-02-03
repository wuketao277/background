package com.hello.background.controller;

import com.hello.background.service.UserService;
import com.hello.background.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wuketao
 * @date 2020/2/3
 * @Description
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 获取所有正常状态的用户
     *
     * @return
     */
    @GetMapping("findAllEnabled")
    public List<UserVO> findAllEnabled() {
        return userService.findByEnabled(true);
    }
}
