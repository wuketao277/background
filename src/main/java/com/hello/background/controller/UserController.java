package com.hello.background.controller;

import com.hello.background.constant.RoleEnum;
import com.hello.background.service.UserService;
import com.hello.background.vo.SaveUserBasicInfoResponse;
import com.hello.background.vo.UpdatePassword;
import com.hello.background.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

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
     * 获取当前用户的角色列表
     *
     * @return
     */
    @GetMapping("getCurrentUserRoleList")
    public List<RoleEnum> getCurrentUserRoleList(HttpSession session) {
        // 获取当前用户
        UserVO user = (UserVO) session.getAttribute("user");
        return user.getRoles();
    }

    /**
     * 通过id查询用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("findById")
    public UserVO findById(Integer id) {
        return userService.findById(id);
    }

    /**
     * 通过姓名查询用户信息
     *
     * @param name
     * @return
     */
    @GetMapping("findByName")
    public UserVO findByName(String name) {
        return userService.findByName(name);
    }

    /**
     * 查询当前用户信息
     *
     * @param session
     * @return
     */
    @GetMapping("findSelf")
    public UserVO findSelf(HttpSession session) {
        // 获取当前用户
        UserVO user = (UserVO) session.getAttribute("user");
        if (null == user) {
            return null;
        }
        return userService.findById(user.getId());
    }

    /**
     * 保存用户基本信息
     *
     * @param vo
     * @return
     */
    @PostMapping("saveBaseInfo")
    public SaveUserBasicInfoResponse saveBaseInfo(@RequestBody UserVO vo) {
        return userService.saveBaseInfo(vo);
    }

    /**
     * 保存用户扩展信息
     *
     * @param vo
     * @return
     */
    @PostMapping("saveExtInfo")
    public UserVO saveExtInfo(@RequestBody UserVO vo, HttpSession session) {
        // 获取当前用户
        UserVO user = (UserVO) session.getAttribute("user");
        return userService.saveExtInfo(vo, user.getId());
    }


    /**
     * 获取所有用户
     *
     * @return
     */
    @GetMapping("findAll")
    public List<UserVO> findAll() {
        return userService.findAll();
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
     * 获取所有正常状态的全职员工
     *
     * @return
     */
    @GetMapping("findAllEnabledFullTime")
    public List<UserVO> findAllEnabledFullTime() {
        return userService.findAllEnabledFullTime();
    }


    /**
     * 获取所有正常状态的全职员工和实习生
     *
     * @return
     */
    @GetMapping("findAllEnabledFullTimeAndIntern")
    public List<UserVO> findAllEnabledFullTimeAndIntern() {
        return userService.findAllEnabledFullTimeAndIntern();
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
     * 查询
     *
     * @param search 搜索关键字
     * @return
     */
    @GetMapping("queryEnabled")
    public List<UserVO> queryEnabled(String search) {
        search = "%" + search + "%";
        return userService.queryEnabled(search);
    }

    /**
     * 更新用户密码
     *
     * @param updatePassword
     * @param session
     */
    @PostMapping("updatePassword")
    public boolean updatePassword(@RequestBody UpdatePassword updatePassword, HttpSession session) {
        // 获取当前用户
        UserVO user = (UserVO) session.getAttribute("user");
        return userService.updatePassword(updatePassword.getOldPassword(), updatePassword.getNewPassword(), user.getId());
    }

    /**
     * 角色检查
     *
     * @return
     */
    @GetMapping("roleCheck")
    public boolean roleCheck(String role, HttpSession session) {
        // 获取当前用户
        UserVO user = (UserVO) session.getAttribute("user");
        return user.getRoles().stream().map(r -> r.name()).collect(Collectors.toList()).contains(role);
    }
}
