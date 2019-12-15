package com.hello.background.repository;

import com.hello.background.domain.Resource;
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
public interface ResourceRepository extends JpaRepository<Resource,Integer> {
    /**
     * 模糊匹配资源名称，资源角色，资源Url，查询分页数据
     *
     * @param name     资源名称
     * @param role     资源角色
     * @param url      资源Url
     * @param pageable 分页信息
     * @return
     */
    Page<Resource> findByNameLikeOrRolesLikeOrUrlLike(String name, String role, String url, Pageable pageable);
}
