package com.hello.background.controller;

import com.hello.background.service.RoleService;
import com.hello.background.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuketao
 * @date 2019/12/7
 * @Description
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 保存角色
     *
     * @param vo 角色视图对象
     * @return
     */
    @RequestMapping("saveRole")
    public RoleVO saveRole(@RequestBody RoleVO vo) {
        return roleService.saveRole(vo);
    }

    /**
     * 查询角色，分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("queryRolePage")
    public Page<RoleVO> queryRolePage(String search, Integer currentPage, Integer pageSize) {
        return roleService.queryRoleVOPage(search, currentPage, pageSize);
    }

    /**
     * 通过id删除角色
     *
     * @param id 角色Id
     */
    @GetMapping("deleteRole")
    public Boolean deleteRole(Integer id) {
        if (id <= 0) {
            return false;
        }
        roleService.deleteById(id);
        return true;

    }
}
