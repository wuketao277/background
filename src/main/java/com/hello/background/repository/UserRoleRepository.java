package com.hello.background.repository;

import com.hello.background.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wuketao
 * @date 2019/12/6
 * @Description
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    /**
     * 通过用户名获取用户角色集合
     *
     * @param userName 用户名
     * @return 用户角色集合
     */
    List<UserRole> findByUserName(String userName);
}
