package com.hello.background.service;

import com.google.common.base.Strings;
import com.hello.background.domain.Role;
import com.hello.background.repository.RoleRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author wuketao
 * @date 2019/12/7
 * @Description
 */
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 保存角色
     *
     * @param vo
     * @return
     */
    public RoleVO saveRole(RoleVO vo) {
        Role role = (Role) TransferUtil.transferTo(vo, Role.class);
        role = roleRepository.save(role);
        return (RoleVO) TransferUtil.transferTo(role, RoleVO.class);
    }

    /**
     * 查询角色分页数据
     *
     * @param search      搜索条件
     * @param currentPage 当前页索引
     * @param pageSize    页尺寸
     * @return
     */
    public Page<RoleVO> queryRoleVOPage(String search, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        Page<Role> rolePage = null;
        if (Strings.isNullOrEmpty(search)) {
            rolePage = roleRepository.findAll(pageable);
        } else {
            rolePage = roleRepository.findByRoleNameLikeOrRoleDescLike(search, search, pageable);
        }
        Page<RoleVO> map = rolePage.map(role -> (RoleVO) TransferUtil.transferTo(role, RoleVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber() + 1, map.getPageable().getPageSize()),
                map.getTotalElements());
        return map;
    }

    /**
     * 通过id删除角色
     *
     * @param id 角色id
     */
    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }
}
