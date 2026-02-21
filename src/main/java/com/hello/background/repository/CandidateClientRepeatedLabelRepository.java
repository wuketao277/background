package com.hello.background.repository;

import com.hello.background.domain.CandidateClientRepeatedLabel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 候选人客户重复标签仓库
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Repository
public interface CandidateClientRepeatedLabelRepository extends PagingAndSortingRepository<CandidateClientRepeatedLabel, Integer>, JpaSpecificationExecutor<CandidateClientRepeatedLabel> {
    /**
     * 通过名称查找标签
     *
     * @param name
     * @return
     */
    List<CandidateClientRepeatedLabel> findByName(String name);

    /**
     * 通过名称删除标签
     *
     * @param name
     */
    void deleteByName(String name);
}
