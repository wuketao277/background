package com.hello.background.repository;

import com.hello.background.domain.Label;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 标签仓库
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Repository
public interface LabelRepository extends PagingAndSortingRepository<Label, Integer>, JpaSpecificationExecutor<Label> {
    /**
     * 通过名称查找标签
     *
     * @param name
     * @return
     */
    List<Label> findByName(String name);

    /**
     * 通过名称删除标签
     *
     * @param name
     */
    void deleteByName(String name);
}
