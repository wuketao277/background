package com.hello.background.controller;

import com.hello.background.service.UserService;
import com.hello.background.vo.UpdatePassword;
import com.hello.background.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
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
     * 保存
     *
     * @param vo
     * @return
     */
    @PostMapping("save")
    public UserVO save(@RequestBody UserVO vo, HttpSession session) {
        vo.setCreateDate(new Date());
        return userService.save(vo);
    }

    /**
     * 获取所有正常状态的用户
     *
     * @return
     */
    @GetMapping("findAllEnabled")
    public List<UserVO> findAllEnabled() {
        return userService.findByEnabled(true);
    }

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("queryPage")
    public Page<UserVO> queryPage(String search, Integer currentPage, Integer pageSize) {
        search = "%" + search + "%";
        return userService.queryPage(search, currentPage, pageSize);
    }

    /**
     * 查询
     *
     * @param search 搜索关键字
     * @return
     */
    @GetMapping("query")
    public List<UserVO> query(String search) {
        search = "%" + search + "%";
        return userService.query(search);
    }

    /**
     * 更新用户密码
     *
     * @param updatePassword
     * @param session
     */
    @PostMapping("updatePassword")
    public boolean updatePassword(@RequestBody UpdatePassword updatePassword, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
        return userService.updatePassword(updatePassword.getOldPassword(), updatePassword.getNewPassword(), user.getId());
    }
}
