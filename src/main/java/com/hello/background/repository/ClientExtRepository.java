package com.hello.background.repository;

import com.hello.background.domain.ClientExt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 客户扩展表仓库
 *
 * @author wuketao
 * @date 2024/4/17
 * @Description
 */
@Repository
public interface ClientExtRepository extends JpaRepository<ClientExt, Integer> {
    ClientExt queryById(Integer id);
}
