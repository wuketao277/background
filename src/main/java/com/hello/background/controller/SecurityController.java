package com.hello.background.controller;

import com.alibaba.fastjson.JSONObject;
import com.hello.background.domain.User;
import com.hello.background.repository.UserRepository;
import com.hello.background.repository.UserRoleRepository;
import com.hello.background.security.ResourceService;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.LoginUser;
import com.hello.background.vo.UserVO;
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

    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * 登录
     *
     * @param vo
     * @param session
     * @return
     */
    @RequestMapping("login")
    public JSONObject login(@RequestBody LoginUser vo, HttpSession session) {
        JSONObject jo = new JSONObject();
        User user = userRepository.findByUsernameAndPasswordAndEnabled(vo.getLoginName(), vo.getPassword(), true);
        if (null != user) {
            UserVO userVO = TransferUtil.transferTo(user, UserVO.class);
//            List<UserRole> userRoleList = userRoleRepository.findByUserName(userVO.getUsername());
//            List<String> roleNameList = userRoleList.stream().map(x -> x.getRoleName()).collect(Collectors.toList());
//            userVO.setRoleList(roleNameList);
            jo.put("status", true);
            jo.put("data", userVO);
            session.setAttribute("user", userVO);
        } else {
            jo.put("status", false);
        }
        return jo;
    }

    /**
     * 退出登录
     *
     * @param session
     */
    @RequestMapping("logout")
    public void logout(HttpSession session) {
        session.removeAttribute("user");
    }

    /**
     * 更新资源映射表
     */
    @RequestMapping("updateResourceMap")
    public Boolean updateResourceMap() {
        resourceService.updateResourceMap();
        return true;
    }

    /**
     * 检查是否登录
     *
     * @param session
     * @return
     */
    @RequestMapping("checkLogin")
    public Boolean checkLogin(HttpSession session) {
        Object objUser = session.getAttribute("user");
        if (null != objUser) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查版本
     *
     * @param version
     * @return
     */
    @RequestMapping("checkVersion")
    public boolean checkVersion(String version) {
        return "1.1.4".equals(version);
    }
}
