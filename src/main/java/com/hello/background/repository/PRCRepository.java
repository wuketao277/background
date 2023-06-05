package com.hello.background.repository;

import com.hello.background.domain.PRC;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Repository
public interface PRCRepository extends PagingAndSortingRepository<PRC, Integer>, JpaSpecificationExecutor<PRC> {
    /**
     * 通过中文名称查找候选人
     *
     * @param chineseName
     * @return
     */
    List<PRC> findByChineseName(String chineseName);
}
