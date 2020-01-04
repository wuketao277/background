package com.hello.background.service;

import com.google.common.base.Strings;
import com.hello.background.domain.Resource;
import com.hello.background.domain.Role;
import com.hello.background.repository.ResourceRepository;
import com.hello.background.repository.RoleRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.ResourceVO;
import com.hello.background.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wuketao
 * @date 2019/12/9
 * @Description
 */
@Service
public class ResourceService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    /**
     * 保存资源
     *
     * @param vo
     * @return
     */
    public ResourceVO saveResource(ResourceVO vo) {
        Resource resource = fromVO(vo);
        resource = resourceRepository.save(resource);
        return fromDO(resource);
    }

    /**
     * 通过id删除资源
     *
     * @param id 资源id
     */
    public void deleteById(Integer id) {
        resourceRepository.deleteById(id);
    }

    /**
     * 从DO转换为VO
     * @param resource
     * @return
     */
    private ResourceVO fromDO(Resource resource) {
        ResourceVO vo = TransferUtil.transferTo(resource, ResourceVO.class);
        vo.setRoleVOList(new ArrayList<>());
        if (!Strings.isNullOrEmpty(resource.getRoles())) {
            List<String> roleNameList = Arrays.asList(resource.getRoles().split(","));
            roleNameList.forEach(roleName -> {
                Role role = roleRepository.findByRoleName(roleName);
                if (null != role) {
                    RoleVO roleVO = TransferUtil.transferTo(role, RoleVO.class);
                    vo.getRoleVOList().add(roleVO);
                }
            });
        }
        return vo;
    }

    /**
     * 从VO转换为DO
     *
     * @param vo
     * @return
     */
    private Resource fromVO(ResourceVO vo) {
        Resource resource = TransferUtil.transferTo(vo, Resource.class);
        resource.setRoles("");
        if (null != vo.getRoleVOList()) {
            vo.getRoleVOList().forEach(roleVO -> {
                resource.setRoles(resource.getRoles() + "," + roleVO.getRoleName());
            });
        }
        return resource;
    }


    /**
     * 查询资源分页数据
     *
     * @param search      搜索条件
     * @param currentPage 当前页索引
     * @param pageSize    页尺寸
     * @return
     */
    public Page<ResourceVO> queryResourceVOPage(String search, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        Page<Resource> resourcePage = null;
        if (Strings.isNullOrEmpty(search)) {
            resourcePage = resourceRepository.findAll(pageable);
        } else {
            resourcePage = resourceRepository.findByNameLikeOrRolesLikeOrUrlLike(search, search, search, pageable);
        }
        Page<ResourceVO> map = resourcePage.map(resource -> fromDO(resource));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber() + 1, map.getPageable().getPageSize()),
                map.getTotalElements());
        return map;
    }

}
