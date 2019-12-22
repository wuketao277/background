package com.hello.background.repository;

import com.hello.background.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuketao
 * @date 2019/12/6
 * @Description
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * 通过用户名查找用户
     *
     * @param userName 用户名
     * @return
     */
    User findByUsername(String userName);

    User findByUsernameAndPasswordAndEnabled(String userName, String password, boolean enabled);
}
