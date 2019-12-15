package com.hello.background.repository;

import com.hello.background.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuketao
 * @date 2019/12/6
 * @Description
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    /**
     * 通过角色名称、角色描述，查找分页数据
     *
     * @param name        角色名称
     * @param description 角色描述
     * @param pageable    分页信息
     * @return
     */
    Page<Role> findByRoleNameLikeOrRoleDescLike(String name, String description, Pageable pageable);

    /**
     * 通过角色名称查找角色
     *
     * @param roleName 通角色名称查找角色
     * @return 角色
     */
    Role findByRoleName(String roleName);
}
